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

package it.unina.android.ripper.tools.lib;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * AndroidTools
 * 
 * @author Nicola Amatucci - REvERSE
 *
 */
public class AndroidTools extends CommandLineTools{
	
	public static WrapProcess emulator(String... args) throws IOException {
		if (getOsFamily().equals("windows")) {
			return start("emulator.exe", args);
		} else if (getOsFamily().equals("unix")) {
			return start("emulator", args);
		} else {
			throw new IllegalStateException("Can't infer your OS family.  Please set the " + "os-family" + " system property to one of 'windows', 'unix'.");
		}
	}
	
	public static WrapProcess adb(String... args) throws IOException {
		if (getOsFamily().equals("windows")) {
			return start("adb.exe", args);
		} else if (getOsFamily().equals("unix")) {
			return start("adb", args);
		} else {
			throw new IllegalStateException("Can't infer your OS family.  Please set the " + "os-family" + " system property to one of 'windows', 'unix'.");
		}
	}
}
