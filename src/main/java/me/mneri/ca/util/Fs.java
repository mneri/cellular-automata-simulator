package me.mneri.ca.util;

import java.io.File;

public class Fs {
    public static String path(String... pieces) {
        return String.join(File.separator, (CharSequence[]) pieces);
    }
}
