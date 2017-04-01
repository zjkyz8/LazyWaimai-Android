package com.cheikh.lazywaimai.ui.activity;

import android.content.Intent;
import com.waimai.R;
import com.waimai.base.BaseActivity;
import com.waimai.base.BaseController;
import com.waimai.context.AppContext;
import com.waimai.controller.UserController;
import com.cheikh.lazywaimai.util.ContentView;
import com.cheikh.lazywaimai.ui.Display;
import com.cheikh.lazywaimai.util.RegisterStep;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity<UserController.UserUiCallbacks>
        implements UserController.UserRegisterUi {

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getUserController();
    }

    @Override
    protected void handleIntent(Intent intent, Display display) {
        if (!display.hasMainFragment()) {
            display.showRegisterStep(RegisterStep.STEP_FIRST, null);
        }
    }
}
