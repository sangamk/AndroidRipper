package it.unina.android.ripper.tools.lib;

import it.unina.android.ripper.logger.ConsoleLogger;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;

public class CommandLineTools {

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);
    private static final String PATH_SEP = System.getProperty("path.separator");

    private static String OS_FAMILY = null;

    public static String getOsFamily() {

        if (OS_FAMILY == null) {
            String osFamily = System.getProperty("os-family");
            if (osFamily == null) {
                // boosted straight from ant:
                if (OS_NAME.indexOf("windows") > -1) {
                    osFamily = "windows";
                } else if (PATH_SEP.equals(":") && OS_NAME.indexOf("openvms") == -1 &&
                        (OS_NAME.indexOf("mac") == -1 || OS_NAME.endsWith("x"))) {
                    osFamily = "unix";
                } else {
                    throw new IllegalStateException("Can't infer your OS family.  Please set the " + "os-family" + " system property to one of 'windows', 'unix'.");
                }
            }

            OS_FAMILY = osFamily;
        }

        return OS_FAMILY;
    }

    protected static WrapProcess start(String binary, String ... args) throws IOException {
        return start(binary, asList(args));
    }

    protected static WrapProcess start(String binary, List<String> args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder();

        pb.command().add(binary);
        pb.command().addAll(args);
        pb.redirectErrorStream(true);
        ConsoleLogger.trace(pb.command().toString());

        return new WrapProcess(pb.start());
    }
}
