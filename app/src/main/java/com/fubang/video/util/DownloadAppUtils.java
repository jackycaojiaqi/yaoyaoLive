package com.fubang.video.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import org.dync.giftlibrary.util.FileUtil;

import java.io.File;

/**
 * Created by jacky on 2017/8/8.
 */
public class DownloadAppUtils {
    /**
     * Created by Teprinciple on 2016/11/15.
     */
    private static final String TAG = DownloadAppUtils.class.getSimpleName();
    public static long downloadUpdateApkId = -1;//下载更新Apk 下载任务对应的Id
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径

    /**
     * 通过浏览器下载APK包
     *
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 下载更新apk包
     * 权限:1,<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
     *
     * @param context
     * @param url
     */
    public static void downloadForAutoInstall(Context context, String url, String fileName, String title) {
        //LogUtil.e("App 下载 url="+url+",fileName="+fileName+",title="+title);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Uri uri = Uri.parse(url);
            DownloadManager downloadManager = (DownloadManager) context
                    .getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            //在通知栏中显示
            request.setVisibleInDownloadsUi(true);
            request.setTitle(title);
            downloadUpdateApkFilePath = FileUtils.getFiles() + fileName + ".apk";
            // 若存在，则删除
            deleteFile(downloadUpdateApkFilePath);
            Uri fileUri = Uri.parse("file://" + downloadUpdateApkFilePath);
            request.setDestinationUri(fileUri);
            downloadUpdateApkId = downloadManager.enqueue(request);
//            installAPK(context,uri,downloadUpdateApkId);
        } catch (Exception e) {
            e.printStackTrace();
            downloadForWebView(context, url);
        }
    }


    private static boolean deleteFile(String fileStr) {
        File file = new File(fileStr);
        return file.delete();
    }

}
