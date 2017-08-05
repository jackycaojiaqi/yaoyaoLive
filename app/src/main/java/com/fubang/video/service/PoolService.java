package com.fubang.video.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fubang.video.AppConstant;
import com.fubang.video.util.StringUtil;
import com.vmloft.develop.library.tools.utils.VMSPUtil;
import com.xlg.android.room.RoomMain;
import com.xlg.android.room.RoomPoolMain;

/**
 * Created by jacky on 2017/7/29.
 */
public class PoolService extends Service {
    public static final String TAG = "PoolService";
    public static RoomPoolMain roomMain = new RoomPoolMain();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String gender = intent.getStringExtra(AppConstant.GENDER);

        Log.d(TAG, "onStartCommand() executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.Start(Integer.parseInt((String) VMSPUtil.get(getApplicationContext(), AppConstant.USERID, "0")),
                        StringUtil.getMD5((String) VMSPUtil.get(getApplicationContext(), AppConstant.PASSWORD, "0")), AppConstant.BASE_CONNECT_IP, AppConstant.BASE_POOL_CONNECT_PORT);

                if (gender.equals("1")) {
                    roomMain.getRoom().getChannel().SendUserLinkRequest();//男性的话  开启 服务 直接默认发起配对请求
                }
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
