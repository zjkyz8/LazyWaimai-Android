package com.waimai.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.waimai.widget.CountDownTimerView;

@ContentView(R.layout.fragment_register_second_step)
public class RegisterSecondStepFragment extends BaseFragment<UserController.UserUiCallbacks>
        implements UserController.RegisterSecondStepUi {

    @Bind(R.id.txt_title)
    TextView mTitleTxt;

    @Bind(R.id.edit_code)
    EditText mCodeEdit;

    @Bind(R.id.btn_clear_code)
    ImageView mClearCodeBtn;

    @Bind(R.id.btn_send_code)
    CountDownTimerView mSendCodeBtn;

    @Bind(R.id.btn_submit)
    Button mSubmitCodeBtn;

    private String mMobile;


    public static RegisterSecondStepFragment create(String mobile) {
        RegisterSecondStepFragment fragment = new RegisterSecondStepFragment();
        if (!TextUtils.isEmpty(mobile)) {
            Bundle bundle = new Bundle();
            bundle.putString(Display.PARAM_OBJ, mobile);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getUserController();
    }

    @Override
    protected void handleArguments(Bundle arguments) {
        if (arguments != null) {
            mMobile = arguments.getString(Display.PARAM_OBJ);
        }
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mTitleTxt.setText(getString(R.string.label_send_code_title, mMobile));
        mSendCodeBtn.setOnCountDownListener(new CountDownTimerView.OnCountDownListener() {
            @Override
            public boolean onCountDownFinishState() {
                return mCodeEdit != null && !TextUtils.isEmpty(mCodeEdit.getText().toString());
            }
        });
        mSendCodeBtn.countDown(30000);
        ToastUtil.showToast(R.string.toast_success_send_sms_code);
    }

    @Override
    public void sendCodeFinish() {
        // 开始倒计时
        mSendCodeBtn.countDown(30000);
        cancelLoading();
        ToastUtil.showToast(R.string.toast_success_send_sms_code);
    }

    @Override
    public void verifyMobileFinish() {
        cancelLoading();
        mSubmitCodeBtn.setEnabled(true);
        Display display = getDisplay();
        if (display != null) {
            display.showRegisterStep(RegisterStep.STEP_THIRD, mMobile);
        }
    }

    @Override
    public void onResponseError(ResponseError error) {
        cancelLoading();
        mSubmitCodeBtn.setEnabled(true);
        ToastUtil.showToast(error.getMessage());
    }

    @OnTextChanged(R.id.edit_code)
    public void onCodeTextChange(CharSequence s) {
        int visible = StringUtil.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
        mClearCodeBtn.setVisibility(visible);
    }

    @OnClick({R.id.btn_clear_code, R.id.btn_send_code, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_code:
                mCodeEdit.setText("");
                break;
            case R.id.btn_send_code:
                sendSmsCode();
                break;
            case R.id.btn_submit:
                submitCode();
                break;
        }
    }

    /**
     * 执行发送验证码的操作
     */
    private void sendSmsCode() {
        showLoading(R.string.label_being_something);
        // 发起发送验证码的API请求
        getCallbacks().sendCode(mMobile);
    }

    /**
     * 执行发送验证码的操作
     */
    private void submitCode() {
        // 隐藏软键盘
        SystemUtil.hideKeyBoard(getContext());

        // 验证验证码是否为空
        final String code = mCodeEdit.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showToast(R.string.toast_error_empty_code);
            return;
        }

        showLoading(R.string.label_being_something);
        // 避免重复点击
        mSubmitCodeBtn.setEnabled(false);
        // 发起发送验证码的API请求
        getCallbacks().checkCode(mMobile, code);
    }
}