package com.fubang.video.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.entity.CircleReviewEntity;
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
public class HomeListAdapter extends BaseQuickAdapter<HomeListEntity.InfoBean.DataListBean, BaseViewHolder> {
    private List<HomeListEntity.InfoBean.DataListBean> list;

    public HomeListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListEntity.InfoBean.DataListBean item) {
        if (!StringUtil.isEmptyandnull(item.getCphoto())) {//头像
            ImagUtil.setnoerror(mContext, AppConstant.BASE_IMG_URL + item.getCphoto(), helper.getView(R.id.iv_home_list_pic));
        }
        if (item.getNstatus().equals("0")) { //(0-空闲,1-繁忙,2-勿扰,10-冻结)';
            helper.setText(R.id.tv_home_list_state, "空闲");
        } else if (item.getNstatus().equals("1")) { //(0-空闲,1-繁忙,2-勿扰,10-冻结)';
            helper.setText(R.id.tv_home_list_state, "繁忙");
        } else if (item.getNstatus().equals("2")) { //(0-空闲,1-繁忙,2-勿扰,10-冻结)';
            helper.setText(R.id.tv_home_list_state, "勿扰");
        } else if (item.getNstatus().equals("10")) { //(0-空闲,1-繁忙,2-勿扰,10-冻结)';
            helper.setText(R.id.tv_home_list_state, "冻结");
        }
        helper.setText(R.id.tv_home_list_price, item.getNprice() + "金币/分钟")
                .setText(R.id.tv_home_list_name, item.getCalias())
                .setText(R.id.tv_home_list_city, item.getCcity()!=null ? "未知" : item.getNage())
                .setText(R.id.tv_home_list_age, item.getNage()!=null ? "未知" : item.getNage() + "岁");
        if (item.getNgender().equals("0")) {
            helper.setImageResource(R.id.iv_home_list_gender, R.drawable.ic_home_female);
        } else if (item.getNgender().equals("1")) {
            helper.setImageResource(R.id.iv_home_list_gender, R.drawable.ic_home_male);
        }
        //接听率
        if (!StringUtil.isEmptyandnull(item.getNsuccess())) {
            if (Integer.parseInt(item.getAll_con())!=0){
                int rate = Integer.parseInt(item.getNsuccess()) /  Integer.parseInt(item.getAll_con());
                helper.setText(R.id.tv_home_list_pickup_rate, rate + "%");
            }else {
                helper.setText(R.id.tv_home_list_pickup_rate, "0%");
            }
        } else {
            helper.setText(R.id.tv_home_list_pickup_rate, "0%");
        }
    }
}
