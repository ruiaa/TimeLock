package com.ruiaa.timelock.main.modules.lock.ui;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.base.BaseFragment;
import com.ruiaa.timelock.main.entity.AppInfo;
import com.ruiaa.timelock.main.modules.BaseRecyclerViewAdapter;
import com.ruiaa.timelock.main.modules.lock.viewmodel.AppFragmentModel;


public class AppFragment extends BaseFragment {

    private AppFragmentModel appFragmentModel;

    public AppFragment() {
        // Required empty public constructor
    }


    public static AppFragment newInstance() {
        return new AppFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appFragmentModel=new AppFragmentModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_app);

        BaseRecyclerViewAdapter<AppInfo> baseRecyclerViewAdapter = appFragmentModel.getAdapter();
        baseRecyclerViewAdapter.setItemListenerBinding((holder, position, model) -> {
            holder.getBinding().getRoot().setOnClickListener(v -> {
                ((LockActivity)getActivity()).openFragment(AppFragment.this, LockFragment.newInstance(model.getPackageName()));
            });
        });

        recyclerView.setAdapter(baseRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onDestroy() {
        appFragmentModel.destroy();
        super.onDestroy();
    }
}
