package com.fubang.video.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.entity.ChaoSongEntity;
import com.fubang.video.entity.GiftRankEntity;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.StringUtil;

import java.util.List;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by jacky on 17/3/10.
 */
public class RankGiftAdapter extends BaseQuickAdapter<GiftRankEntity.InfoBean.DataListBean, BaseViewHolder> {
    private List<GiftRankEntity.InfoBean.DataListBean> list;

    public RankGiftAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftRankEntity.InfoBean.DataListBean item) {
        if (!StringUtil.isEmptyandnull(item.getCphoto())) {//发送者头像
            ImagUtil.setnoerror(mContext, AppConstant.BASE_IMG_URL + item.getCphoto(), helper.getView(R.id.iv_gift_rank_sned_pic));
        }
        if (!StringUtil.isEmptyandnull(item.getTocphoto())) {//接受者头像
            ImagUtil.setnoerror(mContext, AppConstant.BASE_IMG_URL + item.getTocphoto(), helper.getView(R.id.iv_gift_rank_to_pic));
        }
        if (!StringUtil.isEmptyandnull(item.getDtime())) {
            try {
                String time = item.getDtime().substring(0, 16);
                helper.setText(R.id.tv_gift_rank_time, time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        helper.setText(R.id.tv_gift_rank_sned_name, item.getCalias() + "")
                .setText(R.id.tv_gift_rank_to_name, item.getTocalias())
                .setText(R.id.tv_gift_rank_gift_price, item.getNusermoney() + "金币")
                .addOnClickListener(R.id.iv_gift_rank_sned_pic)
                .addOnClickListener(R.id.iv_gift_rank_to_pic);

        try {
            getUrl(Integer.parseInt(item.getNgiftid()), (ImageView) helper.getView(R.id.iv_gift_rank_gift_pic));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void getUrl(int gift_id, ImageView view) {
        switch (gift_id) {
            case 21:
                //礼物图片替换
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_21).into(view);
                break;
            case 22:
                //礼物图片替换
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_22).into(view);
                break;
            case 23:
                //礼物图片替换
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_23).into(view);
                break;
            case 24:
                //礼物图片替换
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_24).into(view);
                break;
            case 25:
                //礼物图片替换
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_25).into(view);
                break;
            case 26:
                //礼物图片替换
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_26).into(view);
                break;
            case 27:
                //礼物图片替换
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_27).into(view);
                break;
            case 28:
                //礼物图片替换
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_28).into(view);
                break;
            case 29:
                //礼物图片替换
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_29).into(view);
                break;
            case 30:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_30).into(view);
                break;
            case 31:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_31).into(view);
                break;
            case 32:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_32).into(view);
                break;
            case 33:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_33).into(view);
                break;
            case 34:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_34).into(view);
                break;
            case 35:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_35).into(view);
                break;
            case 36:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_36).into(view);
                break;
            case 37:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_37).into(view);
                break;
            case 38:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_38).into(view);
                break;
            case 39:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_39).into(view);
                break;
            case 40:
                Glide.with(mContext).load(org.dync.giftlibrary.R.drawable.ic_gift_40).into(view);
                break;
        }
    }
}
