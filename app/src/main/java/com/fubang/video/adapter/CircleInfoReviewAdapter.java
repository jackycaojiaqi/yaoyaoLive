package com.fubang.video.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.video.R;
import com.fubang.video.entity.CircleInfoEntity;
import com.fubang.video.entity.CircleReviewEntity;
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
public class CircleInfoReviewAdapter extends BaseQuickAdapter<CircleInfoEntity.InfoBean.ReviewListBean, BaseViewHolder> {
    private List<CircleInfoEntity.InfoBean.ReviewListBean> list;

    public CircleInfoReviewAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleInfoEntity.InfoBean.ReviewListBean item) {
        if (StringUtil.isEmptyandnull(item.getHcalias())) {//不是回复
            helper.setText(R.id.tv_circle_list_review_pname,item.getPcalias()+" ");
            helper.setText(R.id.tv_circle_list_review_huifu_or_maohao,": ");
            helper.setText(R.id.tv_circle_list_review_hname_and_neirong,item.getCreview()+" ");
        } else {//是回复
            helper.setText(R.id.tv_circle_list_review_pname,item.getPcalias()+" ");
            helper.setText(R.id.tv_circle_list_review_huifu_or_maohao,"回复:");
            helper.setText(R.id.tv_circle_list_review_hname_and_neirong,item.getHcalias()+"  "+item.getCreview());
        }
    }
}
