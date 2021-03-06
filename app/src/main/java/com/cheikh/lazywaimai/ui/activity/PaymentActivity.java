package com.cheikh.lazywaimai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import butterknife.OnClick;
import com.waimai.R;
import com.waimai.base.BaseActivity;
import com.waimai.base.BaseController;
import com.waimai.base.BaseViewHolder;
import com.waimai.context.AppContext;
import com.waimai.controller.OrderController;
import com.waimai.model.bean.Order;
import com.cheikh.lazywaimai.model.bean.PaymentPlatform;
import com.cheikh.lazywaimai.model.bean.ResponseError;
import com.cheikh.lazywaimai.ui.adapter.PlatformListAdapter;
import com.waimai.util.ContentView;
import com.waimai.ui.Display;
import com.cheikh.lazywaimai.util.ToastUtil;
import com.waimai.util.ViewEventListener;
import java.util.List;

/**
 * author: cheikh.wang on 17/1/5
 * email: wanghonghi@126.com
 */
@ContentView(R.layout.activity_payment)
public class PaymentActivity extends BaseActivity<OrderController.OrderUiCallbacks>
        implements OrderController.OrderPaymentUi {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.btn_payment)
    Button mBtnPayment;

    private PlatformListAdapter mAdapter;

    private Order mOrder;

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getOrderController();
    }

    @Override
    protected void handleIntent(Intent intent, Display display) {
        mOrder = (Order) intent.getSerializableExtra(Display.PARAM_OBJ);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mAdapter = new PlatformListAdapter();
        mAdapter.setSelectedPosition(0);
        mAdapter.setViewEventListener(new ViewEventListener<PaymentPlatform>() {
            @Override
            public void onViewEvent(int actionId, PaymentPlatform item, int position, View view) {
                mAdapter.setSelectedPosition(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public String getRequestParameter() {
        return mOrder.getId();
    }

    @Override
    public void setPlatforms(List<PaymentPlatform> platforms) {
        mAdapter.setItems(platforms);
    }

    @Override
    public void onFinishPayment(Order order) {
        cancelLoading();
        ToastUtil.showToast(R.string.toast_success_online_payment);
        getCallbacks().showOrderDetail(order);
    }

    @Override
    public void onResponseError(ResponseError error) {
        cancelLoading();
        mBtnPayment.setEnabled(true);
        ToastUtil.showToast(error.getMessage());
    }

    @OnClick({R.id.btn_payment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_payment:
                payment();
                break;
        }
    }

    /**
     * 执行付款操作
     */
    private void payment() {
        // 获取选择的支付平台
        int selectedPosition = mAdapter.getSelectedPosition();
        PaymentPlatform platform = mAdapter.getItem(selectedPosition);

        showLoading(R.string.label_being_simulate_payment);
        mBtnPayment.setEnabled(false);
        getCallbacks().payment(mOrder, platform.getId());
    }

    @Override
    public void onBackPressed() {
        Display display = getDisplay();
        if (display != null) {
            display.showOrderDetail(mOrder);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Display display = getDisplay();
                if (display != null) {
                    display.showOrderDetail(mOrder);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}