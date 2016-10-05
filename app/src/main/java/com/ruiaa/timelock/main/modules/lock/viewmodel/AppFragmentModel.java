package com.ruiaa.timelock.main.modules.lock.viewmodel;

import com.ruiaa.timelock.BR;
import com.ruiaa.timelock.R;
import com.ruiaa.timelock.main.entity.AppInfo;
import com.ruiaa.timelock.main.model.FiterUtil;
import com.ruiaa.timelock.main.model.MainDataCache;
import com.ruiaa.timelock.main.modules.BaseRecyclerViewAdapter;
import com.ruiaa.timelock.main.modules.BaseViewModel;

/**
 * Created by ruiaa on 2016/10/3.
 */

public class AppFragmentModel extends BaseViewModel{

    public AppFragmentModel(){

    }

    public BaseRecyclerViewAdapter<AppInfo> getAdapter(){
        return new BaseRecyclerViewAdapter<>(
                R.layout.item_app,
                FiterUtil.toInstall(MainDataCache.getInstance().getAppInfos()),
                ((holder, position, model) -> {
                    holder.getBinding().setVariable(BR.appInfo, model);
                })
        );
    }

    @Override
    public void destroy() {

    }
}
