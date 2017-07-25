package com.fubang.video.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.LoginEntity;
import com.fubang.video.entity.SendMsgEntity;
import com.fubang.video.entity.UploadPhotoEntity;
import com.fubang.video.util.FileUtils;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.SystemStatusManager;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.widget.ClearableEditText;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by jacky on 2017/7/18.
 */
public class RegisterActivity extends TakePhotoActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_register_pic)
    CircleImageView ivRegisterPic;
    @BindView(R.id.et_register_nickname)
    ClearableEditText etRegisterNickname;
    @BindView(R.id.cb_register_pick_male)
    ImageView cbRegisterPickMale;
    @BindView(R.id.iv_register_pick_male)
    ImageView ivRegisterPickMale;
    @BindView(R.id.ll_register_pick_male)
    LinearLayout llRegisterPickMale;
    @BindView(R.id.cb_register_pick_female)
    ImageView cbRegisterPickFemale;
    @BindView(R.id.iv_register_pick_female)
    ImageView ivRegisterPickFemale;
    @BindView(R.id.ll_register_pick_female)
    LinearLayout llRegisterPickFemale;
    @BindView(R.id.et_register_password)
    ClearableEditText etRegisterPassword;
    @BindView(R.id.btn_register_submit)
    Button btnRegisterSubmit;
    private String phone, calias, password, cphoto, photo_name;
    private int gender = 1;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_register_info);
        ButterKnife.bind(this);
        context = this;
        initview();

    }

    private void initview() {
        tvTitle.setText("注册");
        phone = getIntent().getStringExtra(AppConstant.USERID);
    }

    @OnClick({R.id.iv_register_pic, R.id.iv_back, R.id.ll_register_pick_male, R.id.ll_register_pick_female, R.id.btn_register_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_register_pic:
                ShowPopAction();
                break;
            case R.id.ll_register_pick_male:
                gender = 1;
                cbRegisterPickMale.setImageResource(R.drawable.ic_checkbox_checked);
                ivRegisterPickMale.setImageResource(R.drawable.ic_register_male_checked);
                cbRegisterPickFemale.setImageResource(R.drawable.ic_checkbox_uncheked);
                ivRegisterPickFemale.setImageResource(R.drawable.ic_register_female_unchecked);
                break;
            case R.id.ll_register_pick_female:
                gender = 0;
                cbRegisterPickMale.setImageResource(R.drawable.ic_checkbox_uncheked);
                ivRegisterPickMale.setImageResource(R.drawable.ic_register_male);
                cbRegisterPickFemale.setImageResource(R.drawable.ic_checkbox_checked);
                ivRegisterPickFemale.setImageResource(R.drawable.ic_register_female_checked);
                break;
            case R.id.btn_register_submit:
                calias = etRegisterNickname.getText().toString().trim();
                password = etRegisterPassword.getText().toString().trim();
                if (StringUtil.isEmptyandnull(calias)) {
                    ToastUtil.show(context, "昵称不能为空");
                    return;
                }
                if (StringUtil.isEmptyandnull(password)) {
                    ToastUtil.show(context, "密码不能为空");
                    return;
                }
                KLog.e(photo_name);


                OkGo.<LoginEntity>post(AppConstant.BASE_URL + AppConstant.URL_REGISTER)
                        .tag(this)
                        .params("ctel", phone)
                        .params("calias", calias)
                        .params("password", password)
                        .params("ngender", gender)
                        .params("cphoto", photo_name)
                        .execute(new JsonCallBack<LoginEntity>(LoginEntity.class) {
                            @Override
                            public void onSuccess(Response<LoginEntity> response) {
                                if (response.body().getStatus().equals("success")) {
                                    //本地存数据
                                    VMSPUtil.put(context, AppConstant.TOKEN, response.body().getInfo().getCtoken());
                                    VMSPUtil.put(context, AppConstant.PASSWORD, password);
                                    VMSPUtil.put(context, AppConstant.USERID, response.body().getInfo().getNuserid());
                                    VMSPUtil.put(context, AppConstant.GENDER, String.valueOf(gender));
                                    VMSPUtil.put(context, AppConstant.USERNAME, calias);
                                    VMSPUtil.put(context, AppConstant.USERPIC, AppConstant.BASE_IMG_URL + photo_name);
                                    VMSPUtil.put(context, AppConstant.PHONE, phone);
                                    //去登录

                                    OkGo.<LoginEntity>post(AppConstant.BASE_URL + AppConstant.URL_LOGIN)
                                            .tag(this)
                                            .params("ctel", phone)
                                            .params("password", password)
                                            .execute(new JsonCallBack<LoginEntity>(LoginEntity.class) {
                                                @Override
                                                public void onSuccess(Response<LoginEntity> response) {
                                                    if (response.body().getStatus().equals("success")) {
                                                        VMSPUtil.put(context, AppConstant.USERID, response.body().getInfo().getNuserid());
                                                        VMSPUtil.put(context, AppConstant.TOKEN, response.body().getInfo().getCtoken());
                                                        //环信登录
                                                        Intent intent = new Intent(context, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
//                                                        loginHX();
                                                    } else {
                                                        ToastUtil.show(context, "密码错误");
                                                    }
                                                }

                                                @Override
                                                public void onError(Response<LoginEntity> response) {
                                                    super.onError(response);
                                                }
                                            });
                                } else {
                                    ToastUtil.show(context, "注册失败");
                                }
                            }

                            @Override
                            public void onError(Response<LoginEntity> response) {
                                super.onError(response);
                            }
                        });
                break;
        }
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
                if (file.exists()) {
                    file.delete();
                }
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
                if (file.exists()) {
                    file.delete();
                }
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
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        KLog.e("takeFail");
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        KLog.e("takeSuccess");
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        KLog.e(images.size());
        if (images.size() > 0) {
            KLog.e(images.get(0).getOriginalPath());
            ImagUtil.set(context, new File(images.get(0).getOriginalPath()), ivRegisterPic);
//            Glide.with(context).load(new File(images.get(0).getOriginalPath())).fitCenter().into(ivRegisterPic);
            cphoto = images.get(0).getOriginalPath();
            //上传图片
            OkGo.<UploadPhotoEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPLOAD_PHOTO)
                    .tag(this)
                    .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                    .params("sub_name", "touxiang")
                    .params("file", new File(cphoto))
                    .execute(new JsonCallBack<UploadPhotoEntity>(UploadPhotoEntity.class) {
                        @Override
                        public void onSuccess(Response<UploadPhotoEntity> response) {
                            if (response.body().getStatus().equals("success")) {
                                photo_name = response.body().getInfo().getFilename();
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
    }

    private CropOptions getCropOptions() {
        int height = Integer.parseInt("500");
        int width = Integer.parseInt("600");
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setOutputX(width).setOutputY(height);
        return builder.create();
    }

    /**
     * 设置状态栏背景状态
     */
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemStatusManager tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(0);//状态栏无背景

    }
}
