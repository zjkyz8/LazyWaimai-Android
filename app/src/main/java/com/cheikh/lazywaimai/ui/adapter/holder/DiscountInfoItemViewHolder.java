package com.cheikh.lazywaimai.ui.adapter.holder;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import com.waimai.R;
import com.waimai.base.BaseViewHolder;
import com.waimai.model.bean.CartInfo;
import com.cheikh.lazywaimai.util.StringFetcher;

/**
 * author: cheikh.wang on 16/11/24
 * email: wanghonghi@126.com
 */

public class DiscountInfoItemViewHolder extends BaseViewHolder<CartInfo.DiscountInfo> {

    @Bind(R.id.txt_icon)
    TextView iconTxt;

    @Bind(R.id.txt_name)
    TextView nameTxt;

    @Bind(R.id.txt_price)
    TextView priceTxt;

    @Bind(R.id.txt_desc)
    TextView descTxt;

    public DiscountInfoItemViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(CartInfo.DiscountInfo item) {
        iconTxt.setText(item.getIconName());
        iconTxt.setBackgroundColor(Color.parseColor(item.getIconColor()));
        nameTxt.setText(item.getName());
        priceTxt.setText(StringFetcher.getString(R.string.label_discount_price, item.getPrice()));
        if (!TextUtils.isEmpty(item.getDescription())) {
            descTxt.setText(item.getDescription());
            descTxt.setVisibility(View.VISIBLE);
        } else {
            descTxt.setVisibility(View.GONE);
        }
    }
}
