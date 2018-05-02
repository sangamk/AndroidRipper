package it.unina.android.ripper.tools.lib;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmmaTools extends CommandLineTools{

    public static WrapProcess report(String... args) throws IOException {

        List<String> emmaReportOptions = Arrays.asList("/c", "java", "-cp",  "\"C:/Master/Thesis/AndroidRipperTool/tools/emma.jar\"", "emma", "report", "-r", "txt,html,xml");
        List<String> ecFilesOptions = Arrays.asList(args);

        List<String> emmaOptions = Stream.concat(emmaReportOptions.stream(), ecFilesOptions.stream())
                .collect(Collectors.toList());

        if (getOsFamily().equals("windows")) {
            return start("cmd", emmaOptions);
        } else if (getOsFamily().equals("unix")) {
            throw new NotImplementedException();
        } else {
            throw new IllegalStateException("Can't infer your OS family.  Please set the " + "os-family" + " system property to one of 'windows', 'unix'.");
        }
    }
}
