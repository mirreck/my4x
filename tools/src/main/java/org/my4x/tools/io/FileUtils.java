package org.my4x.tools.io;

import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;

public class FileUtils {

    public static Stream<String[]> points(String path) {
        try {
            InputStream is = FileUtils.class.getResourceAsStream(path);
            List<String> strings = IOUtils.readLines(is, "UTF-8");
            return strings.stream().map(FileUtils::splitSpaces);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String[] splitSpaces(String line){
        return line.split(" ");
    }

    public static <T> Stream<T> loadObjects(String path, Function<String[],T> mapper){
        return points(path).map(mapper);
    }

}
