package com.ruiaa.timelock.main.modules;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ruiaa on 2016/10/3.
 */

public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BindingHolder>{

    private List<T> list;
    private int itemLayoutId;
    private ItemDataBinding<T> itemDataBinding;
    private ItemListenerBinding<T> itemListenerBinding;

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

    public BaseRecyclerViewAdapter() {
    }

    public BaseRecyclerViewAdapter(int itemLayoutId, List<T> list, ItemDataBinding<T> itemDataBinding) {
        this.itemLayoutId = itemLayoutId;
        this.list = list;
        this.itemDataBinding = itemDataBinding;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                itemLayoutId,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final T model = list.get(position);

        itemDataBinding.bindData(holder,position,model);
        itemListenerBinding.bindListener(holder,position,model);

        holder.getBinding().executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    /*
     *
     * 接口
     *
     */

    public interface ItemDataBinding<T>{
        void bindData(BindingHolder holder, int position, T model);
    }

    public interface ItemListenerBinding<T>{
        void bindListener(BindingHolder holder, int position, T model);
    }

    public BaseRecyclerViewAdapter setItemLayoutId(int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
        return this;
    }

    public BaseRecyclerViewAdapter setList(List<T> list) {
        this.list = list;
        return this;
    }

    /*
     *数据相关绑定  setValue  getViewDataChange
     */
    public BaseRecyclerViewAdapter setItemDataBinding(ItemDataBinding<T> itemDataBinding) {
        this.itemDataBinding = itemDataBinding;
        return this;
    }

    /*
     *视图相关绑定  视图跳转
     */
    public BaseRecyclerViewAdapter setItemListenerBinding(ItemListenerBinding<T> itemListenerBinding) {
        this.itemListenerBinding = itemListenerBinding;
        return this;
    }
}
