package com.fubang.video.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aliyun.vodplayer.media.AliyunDataSource;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.PlayVideoEntity;
import com.fubang.video.entity.PublishUpLoadEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;

/**
 * Created by jacky on 2017/7/20.
 */
public class VideoPlayActivity extends BaseActivity {
    private AliyunVodPlayerView mAliyunVodPlayerView;
    private String VideoId;
    private String PlayAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_videoplay);
        VideoId = getIntent().getStringExtra(AppConstant.VIDEOID);
        initview();
        initdate();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onResume();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
        }
    }
    private void initview() {
        //找到播放器对象
        mAliyunVodPlayerView = (AliyunVodPlayerView) findViewById(R.id.video_view);

    }

    private void initdate() {
        getAliPlayAuth();
    }
    /**
     * 获取阿里云播放凭证
     */
    private void getAliPlayAuth() {
        KLog.e(VideoId);
        OkGo.<PlayVideoEntity>post(AppConstant.BASE_URL + AppConstant.URL_PUNLISH_UPLOAD)
                .tag(this)
                .params("type", 1)
                .params("VideoId",VideoId)
                .execute(new JsonCallBack<PlayVideoEntity>(PlayVideoEntity.class) {
                    @Override
                    public void onSuccess(Response<PlayVideoEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            PlayAuth = response.body().getInfo().getPlayAuth();
                            //设置监听事件
                            mAliyunVodPlayerView.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared() {
//                                    mAliyunVodPlayerView.start();
                                }
                            });
                            KLog.e(VideoId);
                            AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
                            aliyunPlayAuthBuilder.setPlayAuth(PlayAuth);
                            aliyunPlayAuthBuilder.setVid(VideoId);
                            aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL);
                            mAliyunVodPlayerView.setAuthInfo(aliyunPlayAuthBuilder.build());
                        }
                    }

                    @Override
                    public void onError(Response<PlayVideoEntity> response) {
                        super.onError(response);
                    }
                });
    }
}