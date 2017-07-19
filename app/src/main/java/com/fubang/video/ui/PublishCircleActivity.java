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

import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.PublishUpLoadEntity;
import com.fubang.video.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private String uploadAuth = "";
    private String uploadAddress = "";
    private String videoId = "";
    private String accessKeyId = "LTAIlH4qOw6XnQVs";
    private String accessKeySecret = "jpGMmet5j9IV0gCSE4qkTxcqUVX1Di";

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
                if (StringUtil.isEmpty(path)) {
                    ToastUtil.show(context, "请选择视频文件");
                    return;
                }
                OkGo.<PublishUpLoadEntity>post(AppConstant.BASE_URL + AppConstant.URL_PUNLISH_UPLOAD)
                        .tag(this)
                        .params("type", 2)
                        .params("title", path)
                        .params("filename", name)
                        .params("filesize", size)
                        .params("desc", path)
                        .execute(new JsonCallBack<PublishUpLoadEntity>(PublishUpLoadEntity.class) {
                            @Override
                            public void onSuccess(Response<PublishUpLoadEntity> response) {
                                if (response.body().getStatus().equals("success")) {
                                    uploadAuth = response.body().getInfo().getUploadAuth();
                                    uploadAddress = response.body().getInfo().getUploadAddress();
                                    videoId = response.body().getInfo().getVideoId();
                                    oublishfile(path);
                                }
                            }

                            @Override
                            public void onError(Response<PublishUpLoadEntity> response) {
                                super.onError(response);
                            }
                        });

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

    private String path, name;
    private long size;

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
                        path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 图片文件路径
                        size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 图片大小
                        name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 图片文件名
                        KLog.e("v_path=" + path);
                        KLog.e("v_size=" + size);
                        KLog.e("v_name=" + name);
                        ivPublishAddVideo.setImageBitmap(createVideoThumbnail(path));
                    }
                }
                break;
        }
    }

    private VODUploadClient uploader;

    /**
     * 调用阿里云上传操作
     * @param name
     */
    private void oublishfile(String name) {
        uploader = new VODUploadClientImpl(context);
        VODUploadCallback callback = new VODUploadCallback() {
            /**
             * 文件开始上传时触发
             */
            void onUploadStarted() {
                KLog.e("onUploadStarted");
            }

            @Override
            public void onUploadSucceed(UploadFileInfo uploadFileInfo) {
                KLog.e("onUploadSucceed");
            }

            @Override
            public void onUploadFailed(UploadFileInfo uploadFileInfo, String s, String s1) {
                KLog.e("onUploadFailed");
            }
            @Override
            public void onUploadProgress(UploadFileInfo uploadFileInfo, long l, long l1) {
                KLog.e("onUploadProgress");
            }

            @Override
            public void onUploadTokenExpired() {
                KLog.e("onUploadTokenExpired");
            }

            @Override
            public void onUploadRetry(String s, String s1) {
                KLog.e("onUploadRetry");
            }

            @Override
            public void onUploadRetryResume() {
                KLog.e("onUploadRetryResume");
            }


            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                uploader.setUploadAuthAndAddress(uploadFileInfo, uploadAuth, uploadAddress);
            }
        };

        uploader.init(callback);
        uploader.addFile(path, getVodInfo(name));
        uploader.start();
    }

    private VodInfo getVodInfo(String name) {
        VodInfo vodInfo = new VodInfo();
        vodInfo.setTitle("标题" + name);
        vodInfo.setDesc("描述." + name);
        vodInfo.setCateId(1);
        vodInfo.setIsProcess(true);
        List<String> tags = new ArrayList<>();
        tags.add("标签" + name);
        vodInfo.setTags(tags);
        vodInfo.setIsShowWaterMark(false);
        vodInfo.setPriority(7);
        return vodInfo;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uploader.stop();
    }
}
