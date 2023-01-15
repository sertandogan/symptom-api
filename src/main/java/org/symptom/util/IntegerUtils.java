package org.symptom.util;

import org.apache.commons.lang3.StringUtils;

public class IntegerUtils {
    public static Integer convert(String data){
        if (StringUtils.isBlank(data)) return null;
        return Integer.valueOf(data);
    }
}
