/**
 * GNU Affero General Public License, version 3
 * 
 * Copyright (c) 2014-2017 REvERSE, REsEarch gRoup of Software Engineering @ the University of Naples Federico II, http://reverse.dieti.unina.it/
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package it.unina.android.ripper.boundary;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import it.unina.android.ripper.driver.AndroidRipperStarter;
import it.unina.android.ripper.driver.exception.RipperUncaughtExceptionHandler;
import it.unina.android.ripper.logger.ConsoleLogger;
import it.unina.android.ripper.observer.RipperEventListener;
import it.unina.android.ripper.tools.lib.EmmaTools;
import it.unina.android.ripper.tools.lib.WrapProcess;

/**
 * Entry Point of Android Ripper
 * 
 * @author Nicola Amatucci - REvERSE
 * 
 */
public class AndroidRipper implements RipperEventListener {

	public AndroidRipperStarter ripper = null;

	private static String defaultConf = "default.properties";

	
	/**
	 * Entry Point
	 * 
	 * Args:
	 * - args[0] apk to test
	 * - args[1] configuration properties file
	 * 
	 * @param args
	 */
	public static void main(String[] args) {


        boolean noProblem = false;

		System.out.println("Android Ripper");

		AndroidRipper ripper = null;

		if (args.length < 1) {
			System.out.println("ERROR: You haven't specified needed parameters!");
		} else if (args.length == 1) {
			if (checkConfigurationFile(defaultConf) == false) {
				System.out.println("ERROR: Config file does not exist!");
			} else {
				ripper = new AndroidRipper(args[0], defaultConf);
			}
			noProblem = true;
		} else if (args.length == 2) {
			if (checkConfigurationFile(args[1]) == false) {
				System.out.println("ERROR: Config file does not exist!");
			} else {
				ripper = new AndroidRipper(args[0], defaultConf);
			}
			noProblem = true;
		}

		if (noProblem == false) {
			printUsageInstructions();
		} else {
			ripper.startRipping();
		}
	}

	public AndroidRipper(String apk, String configFile) {
		super();
		RipperUncaughtExceptionHandler uExH = new RipperUncaughtExceptionHandler();
		uExH.setExitOnException(true);
		ripper = new AndroidRipperStarter(apk, configFile, this, uExH);
	}

	public void startRipping() {
		ripper.startRipping();
	}

	/**
	 * Verify if the configuration file exists
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @return
	 */
	public static boolean checkConfigurationFile(String fileName) {
		if (new File(fileName).exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Print usage instructions
	 */
	public static void printUsageInstructions() {
		System.out.println();
		System.out.println("Usage: java -jar AndroidRipper.jar s|r|tc pathToApk [config.properties]");
		System.out.println();
		System.out.println("Parameter 1:");
		System.out.println("- path to apk to test");
		System.out.println();
		System.out.println("Parameter 2:");
		System.out.println("- configuration file name and path");
		System.out.println();
		System.out.println();
	}

	@Override
	public void ripperLog(String log) {
		ConsoleLogger.info(log);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.unina.android.ripper.observer.RipperEventListener#ripperStatusUpdate(
	 * java.lang.String)
	 */
	@Override
	public void ripperStatusUpdate(String status) {
		ConsoleLogger.info(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.unina.android.ripper.observer.RipperEventListener#ripperTaskEneded()
	 */
	@Override
	public void ripperTaskEneded() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unina.android.ripper.observer.RipperEventListener#ripperEneded()
	 */
	@Override
	public void ripperEneded() {
		ConsoleLogger.info("Ripper Ended!");
		System.exit(0);
	}

	@Override
	public void ripperPaused() {
		ConsoleLogger.info("Ripper Paused!");
		System.out.println("Press \"ENTER\" to continue...");
		new Thread() {
			@Override
			public void run() {
				Scanner scanner = new Scanner(System.in);
				scanner.nextLine();
				ripper.getDriver().resumeRipping();
			}
		}.start();
	}
}
