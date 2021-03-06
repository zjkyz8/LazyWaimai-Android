package com.waimai.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.waimai.R;
import com.waimai.base.BaseController;
import com.waimai.base.BaseFragment;
import com.waimai.context.AppContext;
import com.waimai.controller.UserController;
import com.cheikh.lazywaimai.model.bean.ResponseError;
import com.waimai.util.ContentView;
import com.waimai.ui.Display;
import com.waimai.util.RegisterStep;
import com.cheikh.lazywaimai.util.StringUtil;
import com.cheikh.lazywaimai.util.SystemUtil;
import com.cheikh.lazywaimai.util.ToastUtil;

@ContentView(R.layout.fragment_register_first_step)
public class RegisterFirstStepFragment extends BaseFragment<UserController.UserUiCallbacks>
        implements UserController.RegisterFirstStepUi {

    @Bind(R.id.edit_mobile)
    EditText mMobileEdit;

    @Bind(R.id.btn_clear_mobile)
    ImageView mClearMobileBtn;

    @Bind(R.id.btn_send_code)
    Button mSendCodeBtn;

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getUserController();
    }

    @Override
    public void sendCodeFinish() {
        cancelLoading();
        mSendCodeBtn.setEnabled(true);
        // 跳转到步骤2页面
        Display display = getDisplay();
        if (display != null) {
            display.showRegisterStep(RegisterStep.STEP_SECOND,
                    mMobileEdit.getText().toString().trim());
        }
    }

    @Override
    public void onResponseError(ResponseError error) {
        cancelLoading();
        mSendCodeBtn.setEnabled(true);
        ToastUtil.showToast(error.getMessage());
    }

    @OnTextChanged(R.id.edit_mobile)
    public void onMobileTextChange(CharSequence s) {
        int visible = StringUtil.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
        mClearMobileBtn.setVisibility(visible);
    }

    @OnClick({R.id.btn_clear_mobile, R.id.btn_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_mobile:
                mMobileEdit.setText("");
                break;
            case R.id.btn_send_code:
                sendSmsCode();
                break;
        }
    }

    /**
     * 执行发送验证码的操作
     */
    private void sendSmsCode() {
        // 隐藏软键盘
        SystemUtil.hideKeyBoard(getContext());

        // 验证手机号是否为空
        final String mobile = mMobileEdit.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.showToast(R.string.toast_error_empty_phone);
            return;
        }

        showLoading(R.string.label_being_something);
        // 避免重复点击
        mSendCodeBtn.setEnabled(false);
        // 发起发送验证码的API请求
        getCallbacks().sendCode(mobile);
    }
}