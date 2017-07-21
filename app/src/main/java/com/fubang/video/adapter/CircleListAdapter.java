package com.fubang.video.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.entity.BaseInfoEntity;
import com.fubang.video.entity.CircleListEntity;
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
public class CircleListAdapter extends BaseQuickAdapter<CircleListEntity.InfoBean, BaseViewHolder> {
    private List<CircleListEntity.InfoBean> list;

    public CircleListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleListEntity.InfoBean item) {
        if (!StringUtil.isEmptyandnull(item.getCphoto()))//头像
            ImagUtil.set(mContext, AppConstant.BASE_IMG_URL + item.getCphoto(), helper.getView(R.id.iv_circle_list_pic));

        if (!StringUtil.isEmptyandnull(item.getNgender()))//性别
        {
            if (item.getNgender().endsWith("0")) {//男性
                helper.setImageResource(R.id.iv_circle_list_gender, R.drawable.ic_register_male_checked);
            } else {//女性
                helper.setImageResource(R.id.iv_circle_list_gender, R.drawable.ic_register_female_checked);
            }
        }
        helper.setText(R.id.tv_circle_list_name, item.getCalias() + "")//名字
                .setText(R.id.tv_circle_list_age, StringUtil.isEmptyandnull(item.getNage()) ? "未知" : item.getNage() + "")//年龄
                .setText(R.id.tv_circle_list_timeslong, item.getDtime() + "")//多久前发布
                .setText(R.id.tv_circle_list_city, StringUtil.isEmptyandnull(item.getCcity()) ? "未知" : item.getCcity() + "")//城市
                .setText(R.id.tv_circle_list_see_num, "浏览次数" + item.getNscan())//浏览次数
                .setText(R.id.tv_circle_list_content, item.getCcontent() + "")//内容
                .setText(R.id.tv_circlr_review_num, item.getNreview() + "")//评论次数
                .setText(R.id.tv_circle_flower_num, item.getNflowercount() + "");//收到鲜花量
    }
}
