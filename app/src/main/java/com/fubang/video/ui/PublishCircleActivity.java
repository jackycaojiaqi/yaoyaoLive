package com.fubang.video.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.fubang.video.entity.UploadPhotoEntity;
import com.fubang.video.entity.VideoInfoEntity;
import com.fubang.video.util.DialogFactory;
import com.fubang.video.util.FileUtils;
import com.fubang.video.util.GetPathFromUri4kitkat;
import com.fubang.video.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    @BindView(R.id.tv_publish_content_num)
    TextView tvPublishContentNum;

    private String uploadAuth = "";
    private String uploadAddress = "";
    private String videoId = "";
    private String accessKeyId = "LTAIlH4qOw6XnQVs";
    private String accessKeySecret = "jpGMmet5j9IV0gCSE4qkTxcqUVX1Di";
    private String ctopic;
    private String photo_name;
    private String cvideo_mp4;
    private String cvideo_m3u8;

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
        setText(tvTitle, "发布");
        setText(tvSubmit, "发送");
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
                ctopic = date[position];
                return true;
            }
        });
        etPublishContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvPublishContentNum.setText(s.length() + "/100");
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                DialogFactory.showRequestDialog(context);
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
                                    publishfile(path);
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
                        if (Build.VERSION.SDK_INT >= 19) {//适配高低版本的获取视频信息
                            Uri uri = data.getData();
                            path = GetPathFromUri4kitkat.getPath(context, uri);
                            Cursor cursor = getContentResolver().query(uri, null, null,
                                    null, null);
                            cursor.moveToFirst();
                            size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 图片大小
                            name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 图片文件名
                            KLog.e("v_path=" + path);
                            KLog.e("v_size=" + size);
                            KLog.e("v_name=" + name);
                            ivPublishAddVideo.setImageBitmap(createVideoThumbnail(path));
                        } else {
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
                }
                break;
        }
    }

    private VODUploadClient uploader;

    /**
     * 调用阿里云上传操作
     *
     * @param name
     */
    private void publishfile(String name) {
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

                //先上传视频图片再调用发布接口
                upload_pic();
            }

            @Override
            public void onUploadFailed(UploadFileInfo uploadFileInfo, String s, String s1) {
                KLog.e("onUploadFailed");
                DialogFactory.hideRequestDialog();
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

    private void upload_pic() {
        //上传图片
        try {
            OkGo.<UploadPhotoEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPLOAD_PHOTO)
                    .tag(this)
                    .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                    .params("sub_name", "pengyouquan")
                    .params("file", new File(FileUtils.saveImg(createVideoThumbnail(path))))
                    .execute(new JsonCallBack<UploadPhotoEntity>(UploadPhotoEntity.class) {
                        @Override
                        public void onSuccess(Response<UploadPhotoEntity> response) {
                            if (response.body().getStatus().equals("success")) {
                                photo_name = response.body().getInfo().getFilename();
                                send_life_to_server();
                            } else {
                                ToastUtil.show(context, "上传头像失败");
                            }
                        }

                        @Override
                        public void onError(Response<UploadPhotoEntity> response) {
                            super.onError(response);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void send_life_to_server() {
        OkGo.<VideoInfoEntity>post(AppConstant.BASE_URL + AppConstant.URL_PLAY_VIDEO_INFO)
                .tag(this)
                .params("VideoId", videoId)
                .execute(new JsonCallBack<VideoInfoEntity>(VideoInfoEntity.class) {
                    @Override
                    public void onSuccess(Response<VideoInfoEntity> response) {
                        if (response.body().getPlayInfoList() != null) {
                            if (response.body().getPlayInfoList().getPlayInfo().size() > 0) {
                                for (int i = 0; i < response.body().getPlayInfoList().getPlayInfo().size(); i++) {
                                    if (response.body().getPlayInfoList().getPlayInfo().get(i).getFormat().equals("mp4")) {
                                        cvideo_mp4 = response.body().getPlayInfoList().getPlayInfo().get(i).getPlayURL();
                                    } else if (response.body().getPlayInfoList().getPlayInfo().get(i).getFormat().equals("m3u8")) {
                                        cvideo_m3u8 = response.body().getPlayInfoList().getPlayInfo().get(i).getPlayURL();
                                    }
                                }
                            }

                            if (com.fubang.video.util.StringUtil.isEmptyandnull(cvideo_mp4) && !com.fubang.video.util.StringUtil.isEmptyandnull(cvideo_m3u8)) {
                                cvideo_mp4 = cvideo_m3u8;
                            }
                            if (!com.fubang.video.util.StringUtil.isEmptyandnull(cvideo_mp4) && com.fubang.video.util.StringUtil.isEmptyandnull(cvideo_m3u8)) {
                                cvideo_m3u8 = cvideo_mp4;
                            }
                            KLog.e(cvideo_mp4 + "  " + cvideo_m3u8);
                            OkGo.<PublishUpLoadEntity>post(AppConstant.BASE_URL + AppConstant.URL_LIFE_ADD)
                                    .tag(this)
                                    .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                                    .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                                    .params("cvideo", videoId)
                                    .params("cvideophoto", photo_name)
                                    .params("ccontent", etPublishContent.getText().toString().trim())
                                    .params("ctopic", ctopic)
                                    .params("cvideo_mp4", cvideo_mp4)
                                    .params("cvideo_m3u8", cvideo_m3u8)
                                    .execute(new JsonCallBack<PublishUpLoadEntity>(PublishUpLoadEntity.class) {
                                        @Override
                                        public void onSuccess(Response<PublishUpLoadEntity> response) {
                                            if (response.body().getStatus().equals("success")) {
                                                DialogFactory.hideRequestDialog();
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onError(Response<PublishUpLoadEntity> response) {
                                            super.onError(response);
                                        }
                                    });
                        } else {
                            send_life_to_server();
                        }
                    }

                    @Override
                    public void onError(Response<VideoInfoEntity> response) {
                        super.onError(response);

                    }
                });
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
        Bitmap bitmap2 = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            int options = 100;
            while (baos.toByteArray().length / 1024 > 32) {
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 10;
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            bitmap2 = BitmapFactory.decodeStream(isBm, null, null);
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
        return bitmap2;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploader != null)
            uploader.stop();
    }

}
