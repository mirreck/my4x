package org.my4x.tools.io;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;

public class FileUtils {

    public static InputStream inputStream(Object o, String path){
        return o.getClass().getResourceAsStream(path);
    }

    public static Stream<String[]> points(InputStream is) {
        try {
            List<String> strings = IOUtils.readLines(is, "UTF-8");
            return strings.stream().map(FileUtils::splitSpaces);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String[] splitSpaces(String line){
        return line.split(" ");
    }


}
