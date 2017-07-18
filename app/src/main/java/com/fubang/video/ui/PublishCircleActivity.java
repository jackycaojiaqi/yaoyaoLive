package com.fubang.video.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.util.ToastUtil;
import com.socks.library.KLog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.path;

/**
 * Created by jacky on 2017/7/17.
 */
public class PublishCircleActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_action)
    ImageView ivAction;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout tagGroup;
    @BindView(R.id.iv_publish_add_video)
    ImageView ivPublishAddVideo;
    @BindView(R.id.et_publish_content)
    EditText etPublishContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_publish_circle);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvSubmit, "发布");
        tvSubmit.setTextColor(getResources().getColor(R.color.orange));
        final String[] date = new String[]{"#好身材#", "#美女#", "#好声音#", "#无聊，找人撩#", "#污污污#", "#午夜#", "#半夜春声#", "#小狐狸报道#", "#日久见人性#", "#no作no die#", "#大学生了没#"};
        final LayoutInflater mInflater = LayoutInflater.from(context);
        tagGroup.setAdapter(new TagAdapter<String>(date) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        tagGroup, false);
                tv.setText(s);
                return tv;
            }
        });

        tagGroup.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ToastUtil.show(context, date[position]);
                return true;
            }
        });
    }

    private final int GET_VIDEP_FILE = 0X12;

    @OnClick({R.id.tv_submit, R.id.iv_publish_add_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
                // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考访问控制章节
                OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("<StsToken.AccessKeyId>", "<StsToken.SecretKeyId>", "<StsToken.SecurityToken>");

                OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);

                // 构造上传请求
                PutObjectRequest put = new PutObjectRequest("<bucketName>", "<objectKey>", "<uploadFilePath>");
                // 异步上传时可以设置进度回调
                put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                        Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                    }
                });
                OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        Log.d("PutObject", "UploadSuccess");
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                            // 服务异常
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                });
//             task.cancel(); // 可以取消任务
//            task.waitUntilFinished(); // 可以等待直到任务完成
                break;
            case R.id.iv_publish_add_video:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                ((Activity) context).startActivityForResult(intent,
                        GET_VIDEP_FILE);
                break;
        }
    }

    private String path;

    /**
     * 视频回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case GET_VIDEP_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        Cursor cursor = getContentResolver().query(uri, null, null,
                                null, null);
                        cursor.moveToFirst();
                        String v_path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 图片文件路径
                        Long v_size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 图片大小
                        v_size = v_size / 1024 / 1024;
                        String v_name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 图片文件名
                        KLog.e("v_path=" + android.os.Environment
                                .getExternalStorageDirectory().getAbsolutePath() + "/" + v_path);
                        KLog.e("v_size=" + v_size);
                        KLog.e("v_name=" + v_name);
                        ivPublishAddVideo.setImageBitmap(createVideoThumbnail(v_path));
                    }
                }
                break;
        }

    }

    private Bitmap createVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            KLog.e("Assume this is a corrupt video file");
        } catch (RuntimeException ex) {
            KLog.e("Assume this is a corrupt video file");
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        return bitmap;
    }
}
