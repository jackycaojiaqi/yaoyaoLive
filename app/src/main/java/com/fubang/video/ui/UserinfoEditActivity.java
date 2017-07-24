package com.fubang.video.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.common.utils.DateUtil;
import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.TimePickerView;
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
import com.fubang.video.util.LocationUtil;
import com.fubang.video.util.StartUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.SystemStatusManager;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.util.dataUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/18.
 */
public class UserinfoEditActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, AMapLocationListener {
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
    private String VideoCover;
    private String ctopic;
    private String photo_name;
    private String pic_name;
    private String picwall_name;
    private int pic_type = 0;   // 1 头像  2 3 4 5  背景墙  10 生日    20 地址定位
    private VODUploadClient uploader;
    private final int GET_VIDEP_FILE = 0X12;
    private Context context;
    private boolean is_first_add = true;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        context = this;
        setContentView(R.layout.activity_userinfo_edit);
        ButterKnife.bind(this);
        initview();
        initlocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initdate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private void initview() {
        back(ivBack);
        tvTitle.setText("编辑信息");
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
                            tvEditinfoName.setText(response.body().getInfo().getCalias() + " ");//姓名
                            tvEditinfoAddress.setText(response.body().getInfo().getCcity() + " ");//城市
                            tvEditinfoBirth.setText(response.body().getInfo().getCbirthdate() + " ");//生日
                            tvEditinfoTab.setText(response.body().getInfo().getClabel() + " ");//标签
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCphoto())) {
                                pic_name = response.body().getInfo().getCphoto();
                                ImagUtil.set(context, AppConstant.BASE_IMG_URL + pic_name, ivEditinfoPic1);//个人头像
                            }
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCphotowall())) {
                                picwall_name = response.body().getInfo().getCphotowall();
                                if (picwall_name.contains(";")) {//显示照片墙 并吧string数组赋值成list方便替换和增加
                                    imagwall = picwall_name.split(";");
                                    for (int i = 0; i < imagwall.length; i++) {
                                        if (is_first_add)
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
                                } else if (picwall_name.contains(".")) {//只有一张图片
                                    ImagUtil.setnoerror(context, AppConstant.BASE_IMG_URL + picwall_name, ivEditinfoPic2);
                                    if (is_first_add)
                                        list_imagwall.add(picwall_name);
                                }
                                KLog.e(list_imagwall.size());
                            }
                            if (is_first_add) {//只在第一次进入的时候将图片加入到列表
                                is_first_add = false;
                            }
                            //个人介绍视频图片
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCvideophoto()))
                                ImagUtil.set(context, AppConstant.BASE_IMG_URL + response.body().getInfo().getCvideophoto(), ivEditinfoVideo);//个人介绍视频图片
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCprofile())) {
                                has_video = true;
                                videoId = response.body().getInfo().getCprofile();
                                VideoCover = response.body().getInfo().getCvideophoto();
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

    private void initlocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位

    }

    private int age = 18;

    @OnClick({R.id.iv_editinfo_pic1, R.id.iv_editinfo_pic2, R.id.iv_editinfo_pic3, R.id.iv_editinfo_pic4,
            R.id.iv_editinfo_pic5, R.id.iv_editinfo_video, R.id.rll_editinfo_sign, R.id.rll_editinfo_name,
            R.id.rll_editinfo_address, R.id.rll_editinfo_birth, R.id.rll_editinfo_tab,
            R.id.iv_editinfo_video_play})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_editinfo_pic1:
                pic_type = 1;
                ShowPopAction();
                break;
            case R.id.iv_editinfo_pic2:
                pic_type = 2;
                if (list_imagwall.size() == 4) {//如果照片满了，则删除用户选中的照片
                    list_imagwall.remove(0);
                }
                ShowPopAction();
                break;
            case R.id.iv_editinfo_pic3:
                pic_type = 3;
                if (list_imagwall.size() == 4) {
                    list_imagwall.remove(1);
                }
                ShowPopAction();
                break;
            case R.id.iv_editinfo_pic4:
                pic_type = 4;
                if (list_imagwall.size() == 4) {
                    list_imagwall.remove(2);
                }
                ShowPopAction();
                break;
            case R.id.iv_editinfo_pic5:
                pic_type = 5;
                if (list_imagwall.size() == 4) {
                    list_imagwall.remove(3);
                }
                ShowPopAction();
                break;
            case R.id.rll_editinfo_sign:
                intent = new Intent(context, EditInfoActivity.class);
                intent.putExtra(AppConstant.OBJECT, 1);
                startActivity(intent);
                break;
            case R.id.rll_editinfo_name:
                intent = new Intent(context, EditInfoActivity.class);
                intent.putExtra(AppConstant.OBJECT, 2);
                startActivity(intent);
                break;
            case R.id.rll_editinfo_address:
                pic_type = 20;
                mlocationClient.startLocation();
                break;
            case R.id.rll_editinfo_birth:
                pic_type = 10;
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        tvEditinfoBirth.setText(dataUtils.formatDateToString(date));
                        try {
                            age = dataUtils.getAge(date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        update_imag_to_server(dataUtils.formatDateToString(date));
                    }
                }).setType(new boolean[]{true, true, true, false, false, false}).build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.rll_editinfo_tab:
                intent = new Intent(context, EditInfoActivity.class);
                intent.putExtra(AppConstant.OBJECT, 3);
                startActivity(intent);
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
                                            Intent intent1 = new Intent();
                                            intent1.setType("video/*");
                                            intent1.setAction(Intent.ACTION_GET_CONTENT);
                                            intent1.addCategory(Intent.CATEGORY_OPENABLE);
                                            ((Activity) context).startActivityForResult(intent1,
                                                    GET_VIDEP_FILE);
                                            dialog.dismiss();
                                            break;
                                        case 1:
                                            Intent intent = new Intent(context, VideoPlayActivity.class);
                                            intent.putExtra(AppConstant.VIDEOID, videoId);
                                            intent.putExtra(AppConstant.OBJECT, VideoCover);
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
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
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

    /**
     * 处理拍照弹窗
     */
    private PopupWindow pop_pic;
    private Uri imageUri;

    private void ShowPopAction() {
        final View popupView = getLayoutInflater().inflate(R.layout.pop_user_pic, null);
        pop_pic = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop_pic.showAtLocation(tvTitle, Gravity.CENTER_HORIZONTAL, 0, 0);
        pop_pic.setOutsideTouchable(false);
        ImageView iv_cancle = (ImageView) popupView.findViewById(R.id.tv_user_info_pic_cancle);
        TextView tv_album = (TextView) popupView.findViewById(R.id.tv_user_info_pic_form_album);
        TextView tv_camera = (TextView) popupView.findViewById(R.id.tv_user_info_pic_form_camera);
        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_pic.dismiss();
            }
        });
        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(FileUtils.getTempFiles() + System.currentTimeMillis() + "tempalbum.jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                imageUri = Uri.fromFile(file);
                getTakePhoto().onPickFromGalleryWithCrop(imageUri, getCropOptions());
                pop_pic.dismiss();
            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(FileUtils.getTempFiles() + System.currentTimeMillis() + "tempcamera.jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                imageUri = Uri.fromFile(file);
                getTakePhoto().onPickFromCaptureWithCrop(imageUri, getCropOptions());
                pop_pic.dismiss();
            }
        });
    }

    @Override
    public void takeCancel() {
        KLog.e("takeCancel");
    }

    @Override
    public void takeFail(TResult result, String msg) {
        KLog.e("takeFail");
    }

    private String cphoto;

    @Override
    public void takeSuccess(TResult result) {
        KLog.e("takeSuccess");
        if (result.getImages().size() > 0) {
            cphoto = result.getImages().get(0).getOriginalPath();
            uploadImage();
        }
    }

    private StringBuffer imagwall_str;

    /**
     * 上传选中的本地图片到服务器
     */
    private void uploadImage() {
        Map<String, String> map = new HashMap<>();
        if (pic_type == 1) {
            map.put("sub_name", "touxiang");
        } else {
            map.put("sub_name", "beijing");
        }
        //上传图片
        OkGo.<UploadPhotoEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPLOAD_PHOTO)
                .tag(this)
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params(map)
                .params("file", new File(cphoto))
                .execute(new JsonCallBack<UploadPhotoEntity>(UploadPhotoEntity.class) {
                    @Override
                    public void onSuccess(Response<UploadPhotoEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            if (pic_type == 1) {
                                pic_name = response.body().getInfo().getFilename();
                                update_imag_to_server(pic_name);
                            } else {
                                picwall_name = response.body().getInfo().getFilename();
                                list_imagwall.add(picwall_name);
                                KLog.e(list_imagwall.size());
                                imagwall_str = new StringBuffer();
                                for (int i = 0; i < list_imagwall.size(); i++) {
                                    if (i == 0) {
                                        imagwall_str.append(list_imagwall.get(0));
                                        KLog.e(imagwall_str.toString());
                                    } else if (i == 1) {
                                        imagwall_str.append(";" + list_imagwall.get(1));
                                        KLog.e(imagwall_str.toString());
                                    } else if (i == 2) {
                                        imagwall_str.append(";" + list_imagwall.get(2));
                                        KLog.e(imagwall_str.toString());
                                    } else if (i == 3) {
                                        imagwall_str.append(";" + list_imagwall.get(3));
                                        KLog.e(imagwall_str.toString());
                                    }
                                }
                                KLog.e(imagwall_str.toString());
                                update_imag_to_server(imagwall_str.toString());
                            }
                        } else {
                            ToastUtil.show(context, "上传头像失败");
                        }
                    }

                    @Override
                    public void onError(Response<UploadPhotoEntity> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 将返回的图片名字、年龄、生日更新到个人信息中
     */
    private void update_imag_to_server(String pic_or_wall_name) {
        Map<String, String> map = new HashMap<>();
        if (pic_type == 1) {
            map.put("cphoto", pic_or_wall_name);
        } else if (pic_type == 10) {
            map.put("cbirthdate", pic_or_wall_name);
            map.put("nage", String.valueOf(age));
        } else if (pic_type == 20) {
            map.put("ccity", String.valueOf(VMSPUtil.get(context, AppConstant.CITY, "")));
            map.put("cprovince", String.valueOf(VMSPUtil.get(context, AppConstant.PRIVINCE, "")));
            map.put("cdistrict", String.valueOf(VMSPUtil.get(context, AppConstant.ADDRDETAIL, "")));
        } else {
            map.put("cphotowall", pic_or_wall_name);
        }
        OkGo.<PublishUpLoadEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPDATE_INFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params(map)
                .execute(new JsonCallBack<PublishUpLoadEntity>(PublishUpLoadEntity.class) {
                    @Override
                    public void onSuccess(Response<PublishUpLoadEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            ToastUtil.show(context, "上传成功");
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
     * 更新用户扩展
     */
    private void update_et_to_server() {
        Map<String, String> map = new HashMap<>();
        map.put("ccity", String.valueOf(VMSPUtil.get(context, AppConstant.CITY, "")));
        map.put("clocation", String.valueOf(VMSPUtil.get(context, AppConstant.ADDRDETAIL, "")));
        map.put("nlongitude", String.valueOf(VMSPUtil.get(context, AppConstant.LON, "")));
        map.put("nlatitude", String.valueOf(VMSPUtil.get(context, AppConstant.LAT, "")));
        OkGo.<PublishUpLoadEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPDATE_EXTINFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params(map)
                .execute(new JsonCallBack<PublishUpLoadEntity>(PublishUpLoadEntity.class) {
                    @Override
                    public void onSuccess(Response<PublishUpLoadEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            ToastUtil.show(context, "上传成功");
                            initdate();
                        }
                    }

                    @Override
                    public void onError(Response<PublishUpLoadEntity> response) {
                        super.onError(response);
                    }
                });
    }

    private CropOptions getCropOptions() {
        int height = Integer.parseInt("500");
        int width = Integer.parseInt("600");
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setOutputX(width).setOutputY(height);
        return builder.create();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            VMSPUtil.put(context, AppConstant.CITY, aMapLocation.getCity());
            VMSPUtil.put(context, AppConstant.PRIVINCE, aMapLocation.getProvince());
            VMSPUtil.put(context, AppConstant.ADDRDETAIL, aMapLocation.getAddress());
            VMSPUtil.put(context, AppConstant.LAT, aMapLocation.getLatitude());
            VMSPUtil.put(context, AppConstant.LON, aMapLocation.getLongitude());
            update_et_to_server();
            update_imag_to_server("");
        }
    }
}
