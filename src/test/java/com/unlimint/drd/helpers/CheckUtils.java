package com.unlimint.drd.helpers;

import java.io.File;

public class CheckUtils {

    public boolean isFileExists(File file) {
        return file.exists() && file.isFile();
    }

    public boolean isStringEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
