package com.fubang.video.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.entity.ChaoSongEntity;
import com.fubang.video.entity.HomeListEntity;
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
public class RankListAdapter extends BaseQuickAdapter<ChaoSongEntity.InfoBean.DataListBean, BaseViewHolder> {
    private List<ChaoSongEntity.InfoBean.DataListBean> list;

    public RankListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChaoSongEntity.InfoBean.DataListBean item) {
        if (!StringUtil.isEmptyandnull(item.getCphoto())) {//头像
            ImagUtil.setnoerror(mContext, AppConstant.BASE_IMG_URL + item.getCphoto(), helper.getView(R.id.iv_rank_pic));
        }
        if (item.getNstatus().equals("0")) { //(0-空闲,1-繁忙,2-勿扰,10-冻结)';
            helper.setText(R.id.tv_rank_state, mContext.getString(R.string.leasure));
        } else if (item.getNstatus().equals("1")) { //(0-空闲,1-繁忙,2-勿扰,10-冻结)';
            helper.setText(R.id.tv_rank_state,mContext.getString(R.string.busy));
        } else if (item.getNstatus().equals("2")) { //(0-空闲,1-繁忙,2-勿扰,10-冻结)';
            helper.setText(R.id.tv_rank_state, mContext.getString(R.string.dont_call));
        } else if (item.getNstatus().equals("10")) { //(0-空闲,1-繁忙,2-勿扰,10-冻结)';
            helper.setText(R.id.tv_rank_state, mContext.getString(R.string.freze));
        }
        helper.setText(R.id.tv_rank_name, item.getCalias())//名字
                .setText(R.id.tv_rank_num, helper.getPosition() + 3 + " ")//序号
                .setText(R.id.tv_rank_city, item.getCcity() != null ? mContext.getString(R.string.null_string) : item.getNage())
                .setText(R.id.tv_rank_age, item.getNage() != null ? mContext.getString(R.string.null_string): item.getNage() + mContext.getString(R.string.year_old));
        if (item.getNgender().equals("0")) {
            helper.setImageResource(R.id.tv_rank_gender, R.drawable.ic_home_female);
        } else if (item.getNgender().equals("1")) {
            helper.setImageResource(R.id.tv_rank_gender, R.drawable.ic_home_male);
        }
    }
}
