package com.fubang.video.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.video.R;
import com.fubang.video.entity.CircleInfoEntity;
import com.fubang.video.entity.NoticeEntity;
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
public class NotifyAdapter extends BaseQuickAdapter<NoticeEntity.InfoBean.DataListBean, BaseViewHolder> {
    private List<NoticeEntity.InfoBean.DataListBean> list;

    public NotifyAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeEntity.InfoBean.DataListBean item) {
        helper.setText(R.id.tv_notify_name, item.getCopalias() + " ");
        helper.setText(R.id.tv_notify_time, item.getDtime() != null ? item.getDtime().substring(0, 16) : "未知");
        helper.setText(R.id.tv_notify_content, item.getCcontent() + " ");
    }
}
