/*
  GNU Affero General Public License, version 3

  Copyright (c) 2014-2017 REvERSE, REsEarch gRoup of Software Engineering @ the University of Naples Federico II, http://reverse.dieti.unina.it/

  This program is free software: you can redistribute it and/or  modify
  it under the terms of the GNU Affero General Public License, version 3,
  as published by the Free Software Foundation.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.

  You should have received a copy of the GNU Affero General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

package it.unina.android.ripper.driver.device;

/*
  Android Virtual Device Instance

  @author Nicola Amatucci - REvERSE

 */

import it.unina.android.ripper.logger.ConsoleLogger;
import it.unina.android.ripper.tools.actions.Actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class AndroidVirtualDevice extends AbstractDevice {

    private int port = 5554;
    private String pid = "";

    public AndroidVirtualDevice(String name, int port) {
        super(name);
        this.name = name;
        this.port = port;
        this.needsSu = false;
        this.rooted = true;

        Actions.DEVICE = getName();
    }

    @Override
    public void start() {
        pid = "";
        System.out.println("Start AVD..." + port);
        Set<String> runningInstances = getEmulatorPid();
        Actions.startEmulatorNoSnapshotLoadSave(name, port);

//        Actions.waitForDeviceBoot();
        if (runningInstances.isEmpty()) {
            pid = getEmulatorPid().iterator().next();
            ConsoleLogger.debug("Found emulator PID " + pid);
        } else {
            Set<String> afterStart = getEmulatorPid();
            afterStart.removeAll(runningInstances);
            if (afterStart.isEmpty()) {
                ConsoleLogger.error("Stopping emulator, found multiple PID during startup");
                stop();
            } else {
                pid = afterStart.iterator().next();
                ConsoleLogger.debug("Found emulator PID " + pid);
            }
        }
    }

    @Override
    public void stop() {
        System.out.println("Shutdown AVD...");
        Actions.killEmulator();
        Actions.waitDeviceClosed(pid);
    }

    @Override
    public String getName() {
        return "emulator-" + port;
    }

    @Override
    public String getIpAddress() {
        return "localhost";
    }

    @Override
    public boolean isVirtualDevice() {
        return true;
    }

    private Set<String> getEmulatorPid() {
        Set<String> pids = new HashSet<>();
        ConsoleLogger.debug("Check PID");
        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        String[] commands = {"wmic", "process", "where", "caption=\"qemu-system-i386.exe\"", "get", "ProcessId"};
        try {
            proc = rt.exec(commands);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            String s = null;
            while ((s = stdInput.readLine()) != null) {
                if (s.trim().matches("-?\\d+")) {
                    ConsoleLogger.debug("Found PID: " + s);
                    pids.add(s.trim());
                }
            }

            while ((s = stdError.readLine()) != null) {
                ConsoleLogger.error(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pids;
    }
}
