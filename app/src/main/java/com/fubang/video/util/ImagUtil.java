package com.fubang.video.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fubang.video.R;

import java.io.File;

/**
 * Created by jacky on 2017/7/20.
 */
public class ImagUtil {
    public static void set(Context context, String url, View view) {
        Glide.with(context).load(url).error(R.drawable.ic_load_error).centerCrop().into((ImageView) view);
    }
    public static void setnoerror(Context context, String url, View view) {
        Glide.with(context).load(url).centerCrop().into((ImageView) view);
    }
    public static void setwithbg(Context context, String url, View view) {
        Glide.with(context).load(url).centerCrop().error(R.drawable.ic_no_pic_rec).into((ImageView) view);
    }
    public static void set(Context context, Drawable url, View view) {
        Glide.with(context).load(url).error(R.drawable.ic_load_error).centerCrop().placeholder(R.drawable.ic_load_error).into((ImageView) view);
    }

    public static void set(Context context, File url, View view) {
        Glide.with(context).load(url).error(R.drawable.ic_load_error).centerCrop().placeholder(R.drawable.ic_load_error).into((ImageView) view);
    }
    public static void destroy(Context context){
        Glide.with(context).onDestroy();
    }
}
