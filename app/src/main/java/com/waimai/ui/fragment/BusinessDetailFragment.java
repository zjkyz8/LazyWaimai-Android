package com.waimai.ui.fragment;

import android.os.Bundle;
import com.waimai.R;
import com.waimai.base.BaseController;
import com.waimai.base.BaseFragment;
import com.waimai.context.AppContext;
import com.waimai.controller.BusinessController;
import com.waimai.model.bean.Business;
import com.waimai.util.ContentView;
import com.waimai.ui.Display;

@ContentView(R.layout.fragment_business_detail)
public class BusinessDetailFragment extends BaseFragment<BusinessController.BusinessUiCallbacks>
        implements BusinessController.BusinessProfileUi {

    private Business mBusiness;

    public static BusinessDetailFragment create(Business business) {
        BusinessDetailFragment fragment = new BusinessDetailFragment();
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