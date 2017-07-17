package com.fubang.video.util;

import android.content.pm.ActivityInfo;


/**
 * Created by jerikc on 15/12/8.
 */
public class Config {
    public static final boolean DEBUG_MODE = false;
    public static final boolean FILTER_ENABLED = false;
    public static final int SCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    public static final String EXTRA_PUBLISH_URL_PREFIX = "URL:";
    public static final String EXTRA_PUBLISH_JSON_PREFIX = "JSON:";

    public static final String EXTRA_KEY_PUB_URL = "pub_url";

    public static final String HINT_ENCODING_ORIENTATION_CHANGED =
            "Encoding orientation had been changed. Stop streaming first and restart streaming will take effect";
}
