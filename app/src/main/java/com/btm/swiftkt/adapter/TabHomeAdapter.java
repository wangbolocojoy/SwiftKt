package com.btm.swiftkt.adapter;

import androidx.annotation.Nullable;
import com.btm.swiftkt.R;
import com.btm.swiftkt.bean.Data;
import com.btm.swiftkt.utils.ImageAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.BaseIndicator;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @Auther: hero
 * @datetime: 2020/8/10 21:10
 * @desc:
 * @项目: SwiftKt
 */
public class TabHomeAdapter extends BaseQuickAdapter<Data, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public TabHomeAdapter(@Nullable ArrayList<Data> data) {
        super(R.layout.adapter_tab_home, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper,Data item) {
        helper.setText(R.id.textView2, Objects.requireNonNull(item.getAuthor()).getNickName() );
        helper.setText(R.id.textView4,item.getPostAddress());
        helper.setText(R.id.textView5,item.getPostStarts()+"");
        helper.setText(R.id.textView7,item.getAuthor().getNickName());
        helper.setText(R.id.textView9,item.getPostDetail());
        helper.setText(R.id.textView10,item.getPostMessageNum()+"");
        helper.setText(R.id.textView8,item.getCreatTime());
        Banner banner = helper.getView(R.id.banner);
        ImageAdapter adapter = new ImageAdapter(item.getPostImages());
        banner.setAdapter(adapter,false);
        banner.isAutoLoop(false);
        banner.setIndicator(new CircleIndicator(mContext));
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
    }


}
