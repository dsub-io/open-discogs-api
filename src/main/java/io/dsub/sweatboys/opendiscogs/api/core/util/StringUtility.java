package io.dsub.sweatboys.opendiscogs.api.core.util;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtility {
    private static final StringUtility INSTANCE = new StringUtility();

    private StringUtility() {}

    public static String normalize(String in) {
        return INSTANCE.doNormalize(in);
    }

    private String doNormalize(String in) {
        if (in == null) {
            return null;
        }
        var v = in.trim();
        return v.isEmpty() ? null : v;
    }
    public static String getMostChildPath(String path) {
        return INSTANCE.doGetMostChildPath(path);
    }

    public String normalize(String in, String delimiter) {
        return Arrays.stream(in.split(delimiter))
                .map(String::trim)
                .collect(Collectors.joining(delimiter));
    }
    private String doGetMostChildPath(String path) {
        return Arrays.stream(path.split("\\."))
                .reduce((prev, curr) -> {
                    if (prev.isEmpty()) {
                        return curr;
                    }
                    return curr.isEmpty() ? prev : curr;
                })
                .orElse("");
    }
}
