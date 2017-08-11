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
public class HomeNvShengAdapter extends BaseQuickAdapter<HomeEntity.InfoBean.NvShengListBean, BaseViewHolder> {
    private List<HomeEntity.InfoBean.NvShengListBean> list;

    public HomeNvShengAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeEntity.InfoBean.NvShengListBean item) {

        helper.setText(R.id.tv_item_home_online_age,item.getNage()==null?mContext.getString(R.string.null_string):item.getNage())
                .setText(R.id.tv_item_home_online_city,item.getCcity()==null?mContext.getString(R.string.null_string):item.getCcity())
                .setText(R.id.tv_item_home_online_name,item.getCalias()==null?mContext.getString(R.string.null_string):item.getCalias());
        if (!StringUtil.isEmptyandnull(item.getCphoto())){
            ImagUtil.setwithbg(mContext.getApplicationContext(), AppConstant.BASE_IMG_URL+item.getCphoto(),helper.getView(R.id.iv_item_home_online_bg));
        }
        if (item.getNgender().equals("1")){
           helper.setImageResource(R.id.iv_item_home_online_gender,R.drawable.ic_register_male_checked);
        }else if (item.getNgender().equals("0")){
            helper.setImageResource(R.id.iv_item_home_online_gender,R.drawable.ic_register_female_checked);
        }

    }
}
