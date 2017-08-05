package com.fubang.video.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fubang.video.APP;
import com.fubang.video.AppConstant;
import com.fubang.video.db.UserDao;
import com.fubang.video.util.StringUtil;
import com.hyphenate.easeui.domain.EaseUser;
import com.vmloft.develop.library.tools.utils.VMSPUtil;
import com.xlg.android.room.RoomMain;

import java.util.Map;

/**
 * Created by jacky on 2017/7/29.
 */
public class VideoService extends Service {
    public static final String TAG = "VideoService";
    public static RoomMain roomMain = new RoomMain();
    public static boolean is_video_call = false;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.Start(Integer.parseInt((String) VMSPUtil.get(getApplicationContext(), AppConstant.USERID, "0")),
                        StringUtil.getMD5((String) VMSPUtil.get(getApplicationContext(), AppConstant.PASSWORD, "0")), AppConstant.BASE_CONNECT_IP, AppConstant.BASE_CONNECT_PORT);
//                roomMain.Start(13, StringUtil.encodeMD5("123456"), 12, "42.121.57.170", 11444);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
