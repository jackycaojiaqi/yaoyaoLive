package com.fubang.video.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

import com.fubang.video.APP;
import com.fubang.video.ui.MainActivity;
import com.socks.library.KLog;
import com.squareup.haha.perflib.Main;

import org.simple.eventbus.EventBus;

/**
 * Created by jacky on 2017/8/9.
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "xujun";
    public static final String TAG1 = "xxx";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见KLog
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            KLog.i(TAG1, "CONNECTIVITY_ACTION");

            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.isConnected()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        if (MainActivity.roomMain != null) {
                            
                            EventBus.getDefault().post("network_change", "reconncet");
                        }
                        KLog.e(TAG, "当前WiFi连接可用 ");
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                        if (MainActivity.roomMain != null) {
                            EventBus.getDefault().post("network_change", "reconncet");
                        }
                        KLog.e(TAG, "当前移动网络连接可用 ");
                    }
                } else {
                    if (MainActivity.roomMain != null) {
                        EventBus.getDefault().post("network_change", "disconncet");
                    }
                    KLog.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                }


                KLog.e(TAG1, "info.getTypeName()" + activeNetwork.getTypeName());
                KLog.e(TAG1, "getSubtypeName()" + activeNetwork.getSubtypeName());
                KLog.e(TAG1, "getState()" + activeNetwork.getState());
                KLog.e(TAG1, "getDetailedState()"
                        + activeNetwork.getDetailedState().name());
                KLog.e(TAG1, "getDetailedState()" + activeNetwork.getExtraInfo());
                KLog.e(TAG1, "getType()" + activeNetwork.getType());
            } else {   // not connected to the internet
                KLog.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
            }


        }
    }


}
