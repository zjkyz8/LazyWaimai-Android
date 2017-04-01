package com.cheikh.lazywaimai.ui.fragment;

import android.os.Bundle;
import com.waimai.R;
import com.waimai.base.BaseController;
import com.waimai.base.BaseFragment;
import com.waimai.context.AppContext;
import com.waimai.controller.BusinessController;
import com.cheikh.lazywaimai.model.bean.Business;
import com.cheikh.lazywaimai.util.ContentView;
import com.cheikh.lazywaimai.ui.Display;

@ContentView(R.layout.fragment_comment)
public class CommentFragment extends BaseFragment<BusinessController.BusinessUiCallbacks>
        implements BusinessController.CommentListUi {

    private Business mBusiness;

    public static CommentFragment create(Business business) {
        CommentFragment fragment = new CommentFragment();
        if (business != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Display.PARAM_OBJ, business);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getBusinessController();
    }

    @Override
    protected void handleArguments(Bundle arguments) {
        if (arguments != null) {
            mBusiness = (Business) arguments.getSerializable(Display.PARAM_OBJ);
        }
    }

    @Override
    public Business getRequestParameter() {
        return mBusiness;
    }
}
