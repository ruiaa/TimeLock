package com.ruiaa.timelock.main.modules.lock.ui;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruiaa.timelock.BR;
import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.base.BaseFragment;
import com.ruiaa.timelock.common.consts.SqlField;
import com.ruiaa.timelock.common.utils.ToastUtil;
import com.ruiaa.timelock.databinding.FragmentLockBinding;
import com.ruiaa.timelock.main.entity.Lock;
import com.ruiaa.timelock.main.modules.BaseRecyclerViewAdapter;
import com.ruiaa.timelock.main.modules.lock.viewmodel.LockFragmentModel;

public class LockFragment extends BaseFragment {

    private LockFragmentModel lockFragmentModel;
    private RecyclerView recyclerView;

    public LockFragment() {

    }

    public static LockFragment newInstance(String packageName) {
        LockFragment fragment = new LockFragment();
        Bundle args = new Bundle();
        args.putString(SqlField.PACKAGE,packageName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String  packageName=getArguments().getString(SqlField.PACKAGE);
            lockFragmentModel=new LockFragmentModel(packageName,getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLockBinding fragmentLockBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_lock,container,false);
        fragmentLockBinding.setAppInfo(lockFragmentModel.getAppInfo());

        View view=fragmentLockBinding.getRoot();

        recyclerView = (RecyclerView) view.findViewById(R.id.list_lock);
        BaseRecyclerViewAdapter<Lock> baseRecyclerViewAdapter = lockFragmentModel.getAdapter();
        baseRecyclerViewAdapter.setItemListenerBinding((holder, position, model) -> {
            holder.getBinding().getRoot().setOnClickListener(v -> {
                showAddLock(container,model);
            });
        });
        recyclerView.setAdapter(baseRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton button=(FloatingActionButton)view.findViewById(R.id.lock_add);
        button.setOnClickListener(v -> {
            showAddLock(container,null);
        });

        return view;
    }

    public void showAddLock(ViewGroup viewGroup,Lock lock){

        ViewDataBinding  viewDataBinding= DataBindingUtil.inflate(LayoutInflater.from(getActivity()),R.layout.add_lock,viewGroup,false);

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(viewDataBinding.getRoot());
        builder.setCancelable(true);
        final AlertDialog  dialog=builder.create();

        Lock newLock=new Lock(lockFragmentModel.getPackageName());
        if (lock==null){
            viewDataBinding.getRoot().findViewById(R.id.lock_add_delete).setVisibility(View.GONE);
        }else {
            lock.copySettingTo(newLock);
        }

        viewDataBinding.getRoot().findViewById(R.id.lock_add_delete).setOnClickListener(v -> {
            lockFragmentModel.deleteLock(lock);
            dialog.dismiss();
        });
        viewDataBinding.getRoot().findViewById(R.id.lock_add_cancel).setOnClickListener(v -> {
            dialog.dismiss();
        });
        viewDataBinding.getRoot().findViewById(R.id.lock_add_sure).setOnClickListener(v -> {
            if (newLock.getStartTime()<newLock.getFinishTime()){
                if (lock==null){
                    lockFragmentModel.addLock(newLock);
                }else {
                    lockFragmentModel.saveLock(newLock,lock);
                }
                dialog.dismiss();
            }else {
                ToastUtil.showShort(R.string.toast_finishAfterStart);
            }
        });

        viewDataBinding.setVariable(BR.lockAdd,newLock);
        viewDataBinding.setVariable(BR.modelAddLock,lockFragmentModel);

        dialog.show();
    }


    @Override
    public void onDestroy() {
        lockFragmentModel.destroy();
        lockFragmentModel=null;
        super.onDestroy();
    }
}
