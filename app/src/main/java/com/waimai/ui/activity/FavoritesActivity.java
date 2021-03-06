package com.waimai.ui.activity;

import com.waimai.base.BaseController;
import com.waimai.base.BaseListActivity;
import com.waimai.context.AppContext;
import com.waimai.controller.UserController;
import com.waimai.model.bean.Favorite;
import com.cheikh.lazywaimai.ui.adapter.FavoriteListAdapter;
import static com.waimai.util.Constants.ClickType.CLICK_TYPE_BUSINESS_CLICKED;


public class FavoritesActivity extends BaseListActivity<Favorite, UserController.UserUiCallbacks>
        implements UserController.UserFavoriteListUi {

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getUserController();
    }

    @Override
    protected FavoriteListAdapter getAdapter() {
        return new FavoriteListAdapter();
    }

    @Override
    protected void refreshPage() {
        getCallbacks().refresh();
    }

    @Override
    protected void nextPage() {
        getCallbacks().nextPage();
    }

    @Override
    protected void onItemClick(int actionId, Favorite favorite) {
        switch (actionId) {
            case CLICK_TYPE_BUSINESS_CLICKED:
                getCallbacks().showBusiness(favorite.getBusiness());
                break;
        }
    }
}
