package com.fubang.video.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.entity.HomeEntity;
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
public class HomeTuHaoAdapter extends BaseQuickAdapter<HomeEntity.InfoBean.TuhaoListBean, BaseViewHolder> {
    private List<HomeEntity.InfoBean.TuhaoListBean> list;

    public HomeTuHaoAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeEntity.InfoBean.TuhaoListBean item) {
        helper.setText(R.id.tv_item_home_online_age,item.getNage()+"")
                .setText(R.id.tv_item_home_online_city,item.getCcity()+"")
                .setText(R.id.tv_item_home_online_name,item.getCalias()+"");
        if (!StringUtil.isEmptyandnull(item.getCphoto())){
            ImagUtil.setwithbg(mContext, AppConstant.BASE_IMG_URL+item.getCphoto(),helper.getView(R.id.iv_item_home_online_bg));
        }
        if (item.getNgender().equals("0")){
           helper.setImageResource(R.id.iv_item_home_online_gender,R.drawable.ic_register_male_checked);
        }else if (item.getNgender().equals("1")){
            helper.setImageResource(R.id.iv_item_home_online_gender,R.drawable.ic_register_female_checked);
        }

    }
}
