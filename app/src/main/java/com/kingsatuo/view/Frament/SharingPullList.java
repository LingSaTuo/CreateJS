package com.kingsatuo.view.Frament;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lingsatuo.adapter.PullAdapter;
import com.lingsatuo.bmob.ObjectData;
import com.lingsatuo.bmob.utils.BmobUtils;
import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.createjs.R;
import com.lingsatuo.createjs.Utils.SharingReader.SharingReaderUtils;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.openapi.Download;
import com.lingsatuo.utils.Utils;
import com.xw.repo.refresh.PullListView;
import com.xw.repo.refresh.PullToRefreshLayout;

import java.io.File;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2017/11/18.
 */

public class SharingPullList extends Fragment implements PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    PullToRefreshLayout mRefreshLayout;
    PullListView mPullListView;
    PullAdapter adapter;
    int mpage = 0;
    Dialog.A da;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sharing_pull_list, container, false);

        mRefreshLayout = view.findViewById(R.id.pullToRefreshLayout);
        mPullListView = view.findViewById(R.id.pullListView);
        mRefreshLayout.setOnRefreshListener(this);
        mPullListView.setOnItemClickListener(this);
        setData(0);
        return view;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        setData(0);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        setData(mpage);
        mpage++;
    }
    @SuppressLint("WrongConstant")
    private void setData(int page){
        BmobUtils.get(page, object -> {
            if (getActivity()==null)return;
            if (getContext()==null)return;;
            if (object instanceof BmobException){
                Toast.makeText(getContext(),((BmobException)object).getErrorCode()+"    "+((BmobException)object).getMessage(),0).show();
                ((BmobException)object).printStackTrace();
            }else{
                List<ObjectData> objectData = (List<ObjectData>) object;
                if (adapter==null) {
                    adapter = new PullAdapter(getActivity(), objectData);
                    mPullListView.setAdapter(adapter);
                }else{
                    adapter.updateListView(objectData);
                }
            }
            mRefreshLayout.loadMoreFinish(true);
            mRefreshLayout.refreshFinish(true);
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapter.getItemViewType(i)==0)return;
        da = new Dialog(1).LoadingDialog(getActivity())
                .setTile(getString(R.string.s_25))
                .showProgrss(true)
                .setMessage(getString(R.string.s_26))
                .canClose(false)
                .canCloseOut(false).show();
        ObjectData objectData = adapter.getItem(i);
        String name = Utils.getSD()+"/.CreateJS/download/"+objectData.getTitle()+".js";
        objectData.setPath(name);
        if (!new File(name).exists()){
            new Download(getActivity(), objectData.getFile().getUrl(), name, (progress, max, finish, e) -> {
                da.setMax(max);
                da.setProgress((int) progress);
                if(e!=null){
                    da.setMessage(e.getMessage());
                }
                if (finish){
                    da.dismiss();
                    new SharingReaderUtils(((MAIN) getActivity()),objectData).startActivity();
                }
            });
        }else{
            da.dismiss();
            new SharingReaderUtils(((MAIN) getActivity()),objectData).startActivity();
        }
    }
}