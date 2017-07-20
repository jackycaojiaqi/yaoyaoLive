package com.fubang.video.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.BaseInfoEntity;
import com.fubang.video.entity.PublishUpLoadEntity;
import com.fubang.video.entity.UploadPhotoEntity;
import com.fubang.video.util.DialogFactory;
import com.fubang.video.util.FileUtils;
import com.fubang.video.util.GetPathFromUri4kitkat;
import com.fubang.video.util.GlideImageLoader;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/18.
 */
public class UserinfoEditActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_editinfo_pic1)
    ImageView ivEditinfoPic1;
    @BindView(R.id.iv_editinfo_pic2)
    ImageView ivEditinfoPic2;
    @BindView(R.id.iv_editinfo_pic3)
    ImageView ivEditinfoPic3;
    @BindView(R.id.iv_editinfo_pic4)
    ImageView ivEditinfoPic4;
    @BindView(R.id.iv_editinfo_pic5)
    ImageView ivEditinfoPic5;
    @BindView(R.id.iv_editinfo_video)
    ImageView ivEditinfoVideo;
    @BindView(R.id.iv_editinfo_video_play)
    ImageView ivEditinfoVideoPlay;
    @BindView(R.id.tv_editinfo_sign)
    TextView tvEditinfoSign;
    @BindView(R.id.rll_editinfo_sign)
    RelativeLayout rllEditinfoSign;
    @BindView(R.id.tv_editinfo_name)
    TextView tvEditinfoName;
    @BindView(R.id.rll_editinfo_name)
    RelativeLayout rllEditinfoName;
    @BindView(R.id.tv_editinfo_gender)
    TextView tvEditinfoGender;
    @BindView(R.id.rll_editinfo_gender)
    RelativeLayout rllEditinfoGender;
    @BindView(R.id.tv_editinfo_address)
    TextView tvEditinfoAddress;
    @BindView(R.id.rll_editinfo_address)
    RelativeLayout rllEditinfoAddress;
    @BindView(R.id.tv_editinfo_birth)
    TextView tvEditinfoBirth;
    @BindView(R.id.rll_editinfo_birth)
    RelativeLayout rllEditinfoBirth;
    @BindView(R.id.tv_editinfo_tab)
    TextView tvEditinfoTab;
    @BindView(R.id.rll_editinfo_tab)
    RelativeLayout rllEditinfoTab;
    @BindView(R.id.tv_editinfo_weichat)
    TextView tvEditinfoWeichat;
    @BindView(R.id.rll_editinfo_weichat)
    RelativeLayout rllEditinfoWeichat;
    private String[] imagwall;
    private List<String> list_imagwall = new ArrayList<>();
    private boolean has_video = false;
    private String path, name;
    private long size;

    private String uploadAuth = "";
    private String uploadAddress = "";
    private String videoId = "";
    private String ctopic;
    private String photo_name;

    private VODUploadClient uploader;
    private final int GET_VIDEP_FILE = 0X12;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_userinfo_edit);
        ButterKnife.bind(this);
        initview();
        initdate();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, "编辑信息");
    }

    private void initdate() {
        OkGo.<BaseInfoEntity>post(AppConstant.BASE_URL + AppConstant.URL_BASE_INFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .execute(new JsonCallBack<BaseInfoEntity>(BaseInfoEntity.class) {
                    @Override
                    public void onSuccess(Response<BaseInfoEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            if (response.body().getInfo().getNgender().equals("0")) {//性别
                                tvEditinfoGender.setText("男");
                            } else if (response.body().getInfo().getNgender().equals("1")) {
                                tvEditinfoGender.setText("女");
                            }
                            tvEditinfoSign.setText(response.body().getInfo().getCidiograph() + " ");//签名
                            tvEditinfoName.setText(response.body().getInfo().getCname() + " ");//姓名
                            tvEditinfoAddress.setText(response.body().getInfo().getCcity() + " ");//城市
                            tvEditinfoBirth.setText(response.body().getInfo().getCbirthdate() + " ");//生日
                            tvEditinfoTab.setText(response.body().getInfo().getClabel() + " ");//标签
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCphoto()))
                                ImagUtil.set(context, AppConstant.BASE_IMG_URL + response.body().getInfo().getCphoto(), ivEditinfoPic1);//个人介绍视频图片
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCphotowall())) {
                                if (response.body().getInfo().getCphotowall().contains(";")) {//显示照片墙 并吧string数组赋值成list方便替换和增加
                                    imagwall = response.body().getInfo().getCphotowall().split(";");
                                    for (int i = 0; i < imagwall.length; i++) {
                                        list_imagwall.add(imagwall[i]);
                                        if (i == 0) {
                                            ImagUtil.setnoerror(context, AppConstant.BASE_IMG_URL + imagwall[0], ivEditinfoPic2);
                                        } else if (i == 1) {
                                            ImagUtil.setnoerror(context, AppConstant.BASE_IMG_URL + imagwall[1], ivEditinfoPic3);
                                        } else if (i == 2) {
                                            ImagUtil.setnoerror(context, AppConstant.BASE_IMG_URL + imagwall[2], ivEditinfoPic4);
                                        } else if (i == 3) {
                                            ImagUtil.setnoerror(context, AppConstant.BASE_IMG_URL + imagwall[3], ivEditinfoPic5);
                                        }
                                    }
                                }
                            }
                            //个人介绍视频图片
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCvideophoto()))
                                ImagUtil.set(context, AppConstant.BASE_IMG_URL + response.body().getInfo().getCvideophoto(), ivEditinfoVideo);//个人介绍视频图片
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCprofile())) {
                                has_video = true;
                                videoId = response.body().getInfo().getCprofile();
                            } else {
                                has_video = false;
                            }

                        } else {
                            startActivity(new Intent(context, LoginActivity.class));
                        }
                    }

                    @Override
                    public void onError(Response<BaseInfoEntity> response) {
                        super.onError(response);
                    }
                });
    }

    @OnClick({R.id.iv_editinfo_pic1, R.id.iv_editinfo_pic2, R.id.iv_editinfo_pic3, R.id.iv_editinfo_pic4,
            R.id.iv_editinfo_pic5, R.id.iv_editinfo_video, R.id.rll_editinfo_sign, R.id.rll_editinfo_name,
            R.id.rll_editinfo_gender, R.id.rll_editinfo_address, R.id.rll_editinfo_birth, R.id.rll_editinfo_tab,
            R.id.iv_editinfo_video_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_editinfo_pic1:
                break;
            case R.id.iv_editinfo_pic2:
                break;
            case R.id.iv_editinfo_pic3:
                break;
            case R.id.iv_editinfo_pic4:
                break;
            case R.id.iv_editinfo_pic5:
                break;
            case R.id.iv_editinfo_video:
                break;
            case R.id.rll_editinfo_sign:
                break;
            case R.id.rll_editinfo_name:
                break;
            case R.id.rll_editinfo_gender:
                break;
            case R.id.rll_editinfo_address:
                break;
            case R.id.rll_editinfo_birth:
                break;
            case R.id.rll_editinfo_tab:
                break;
            case R.id.iv_editinfo_video_play:
                if (has_video) {
                    new MaterialDialog.Builder(this)
                            .title(R.string.pick_action)
                            .items(R.array.items_action)
                            .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    switch (which) {
                                        case 0:
                                            dialog.dismiss();
                                            break;
                                        case 1:
                                            Intent intent = new Intent(context,VideoPlayActivity.class);
                                            intent.putExtra(AppConstant.VIDEOID,videoId);
                                            startActivity(intent);
                                            dialog.dismiss();
                                            break;
                                        case 2:
                                            dialog.dismiss();
                                            break;
                                    }
                                    return true;
                                }
                            })
                            .show();
                } else {
                    new MaterialDialog.Builder(this)
                            .title(R.string.pick_action)
                            .items(R.array.items_action_upload)
                            .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    switch (which) {
                                        case 0:
                                            Intent intent = new Intent();
                                            intent.setType("video/*");
                                            intent.setAction(Intent.ACTION_GET_CONTENT);
                                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                                            ((Activity) context).startActivityForResult(intent,
                                                    GET_VIDEP_FILE);
                                            break;
                                        case 1:
                                            dialog.dismiss();
                                            break;
                                    }
                                    return true;
                                }
                            })
                            .show();
                }

                break;
        }
    }

    /**
     * 获取阿里云上传凭证
     */
    private void getAliUploadAuth() {
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
    }

    /**
     * 调用阿里云上传操作
     *
     * @param name
     */
    private void publishfile(String name) {
        DialogFactory.showRequestDialog(context);
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
                DialogFactory.hideRequestDialog();
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

    /**
     * 视频上传阿里云后上传第一帧图片给自己服务器，并返回photo_name
     */
    private void upload_pic() {
        //上传图片
        try {
            OkGo.<UploadPhotoEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPLOAD_PHOTO)
                    .tag(this)
                    .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                    .params("sub_name", "userinfovideobg")
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

    /**
     * 将返回的photo_name更新到个人信息中
     */
    private void send_life_to_server() {
        OkGo.<PublishUpLoadEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPDATE_INFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("cprofile", videoId)
                .params("cvideophoto", photo_name)
                .execute(new JsonCallBack<PublishUpLoadEntity>(PublishUpLoadEntity.class) {
                    @Override
                    public void onSuccess(Response<PublishUpLoadEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            ToastUtil.show(context, "上传视频成功");
                            initdate();
                        }
                    }

                    @Override
                    public void onError(Response<PublishUpLoadEntity> response) {
                        super.onError(response);
                    }
                });
    }

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
                            ivEditinfoVideo.setImageBitmap(createVideoThumbnail(path));
                            getAliUploadAuth();
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
                            ivEditinfoVideo.setImageBitmap(createVideoThumbnail(path));
                            getAliUploadAuth();
                        }
                    }
                }
                break;
        }
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

    /**
     * 本地视频第一帧图片转换成bitmap显示
     *
     * @param filePath
     * @return
     */
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
        if (uploader != null)
            uploader.stop();
    }
}
