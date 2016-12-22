package me.mneri.ca.util;

import javax.swing.*;

public class IconFactory {
    private static final int CACHE_SIZE = 128;
    private static final String BASEDIR = "/me/mneri/ca/icon/";

    private static IconFactory sInstance;

    private LruCache<String, Icon> mCache = new LruCache<>(CACHE_SIZE);

    private IconFactory() { }

    public static IconFactory instance() {
        if (sInstance == null)
            sInstance = new IconFactory();

        return sInstance;
    }

    public Icon get(String name) {
        if (!mCache.containsKey(name))
            load(name);

        return mCache.get(name);
    }

    public void load(String name) {
        ImageIcon icon = new ImageIcon(getClass().getResource(BASEDIR + name));
        mCache.put(name, icon);
    }
}
