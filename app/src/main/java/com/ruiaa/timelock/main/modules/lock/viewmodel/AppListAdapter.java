package com.ruiaa.timelock.main.modules.lock.viewmodel;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ruiaa.timelock.BR;
import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.main.entity.AppInfo;

import java.util.List;

/**
 * Created by ruiaa on 2016/10/3.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.BindingHolder>{

    private List<AppInfo> appInfoList;

    public static class BindingHolder extends RecyclerView.ViewHolder{
        private final ViewDataBinding binding;
        public BindingHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public AppListAdapter(List<AppInfo> appInfoList) {
        this.appInfoList = appInfoList;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_app,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final AppInfo appInfo = appInfoList.get(position);

        holder.getBinding().setVariable(BR.appInfo,appInfo);
        holder.getBinding().getRoot().setOnClickListener(view->{
            LogUtil.i("onclock item--");
        });
        holder.getBinding().executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return appInfoList.size();
    }
}
