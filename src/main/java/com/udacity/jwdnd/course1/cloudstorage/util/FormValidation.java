package com.udacity.jwdnd.course1.cloudstorage.util;

import java.util.List;

public class FormValidation {
    public static boolean isValid(List<String> values) {
        for (var value : values
        ) {
            return !value.isEmpty() && !value.isBlank();
        }
        return true;
    }
}
