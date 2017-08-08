package com.fubang.video.receive;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.fubang.video.AppConstant;
import com.fubang.video.util.DownloadAppUtils;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.io.File;

/**
 * Created by jacky on 2017/8/8.
 */
public class UpdataBroadcastReceiver extends BroadcastReceiver {

    @SuppressLint("NewApi")
    public void onReceive(Context context, Intent intent) {

        long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        long refernece = DownloadAppUtils.downloadUpdateApkId;
        if (refernece != myDwonloadID) {
            return;
        }

        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadFileUri = dManager.getUriForDownloadedFile(myDwonloadID);
        installAPK(context, downloadFileUri);
    }

    private void installAPK(Context context, Uri apk) {
        if (Build.VERSION.SDK_INT < 23) {
            KLog.e("<23");
            Intent intents = new Intent();
            intents.setAction("android.intent.action.VIEW");
            intents.addCategory("android.intent.category.DEFAULT");
            intents.setType("application/vnd.android.package-archive");
            intents.setData(apk);
            intents.setDataAndType(apk, "application/vnd.android.package-archive");
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intents);
        } else {
            KLog.e(">=23");
//            String url = AppConstant.DOWNLOAD_URL;
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(url));
//            context.startActivity(intent);
//            File file = new File(DownloadAppUtils.downloadUpdateApkFilePath);
//            if (file.exists()) {
//                openFile(file, context);
//            }
        }
    }

    private void openFile(File file, Context context) {
        Intent var2 = new Intent();
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        Uri uriForFile = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        var2.setDataAndType(uriForFile, context.getContentResolver().getType(uriForFile));

        try {
            context.startActivity(var2);
        } catch (Exception var5) {
            var5.printStackTrace();
            Toast.makeText(context, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
        }

    }

    private String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

}
