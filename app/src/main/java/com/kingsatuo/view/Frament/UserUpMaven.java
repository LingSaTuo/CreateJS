package com.kingsatuo.view.Frament;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lingsatuo.adapter.PullAdapter;
import com.lingsatuo.bmob.ObjectData;
import com.lingsatuo.bmob.UserMaven;
import com.lingsatuo.callbackapi.FunctionCallBACK;
import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.createjs.R;
import com.lingsatuo.createjs.Utils.SharingUtils.SharingMaven;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.openapi.Download;
import com.lingsatuo.utils.Utils;
import com.lingsatuo.utils.ZipUtils;
import com.xw.repo.refresh.PullListView;
import com.xw.repo.refresh.PullToRefreshLayout;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2018/2/6.
 */

public class UserUpMaven extends Fragment implements PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    PullToRefreshLayout mRefreshLayout;
    PullListView mPullListView;
    PullAdapter<UserMaven> adapter;
    FloatingActionButton fab;
    int mpage = 0;
    Dialog.A da;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_pull_maven, container, false);

        mRefreshLayout = view.findViewById(R.id.pullToRefreshLayout);
        mPullListView = view.findViewById(R.id.pullListView);
        fab = view.findViewById(R.id.fab);
        mRefreshLayout.setOnRefreshListener(this);
        mPullListView.setOnItemClickListener(this);
        fab.setOnClickListener(view1 -> {
            new SharingMaven((MAIN)getActivity()).startActivity();
        });
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
    private void setData(int page) {
        Data.get(page, object -> {
            if (getActivity() == null) return;
            if (getContext() == null) return;
            ;
            if (object instanceof BmobException) {
                Toast.makeText(getContext(), ((BmobException) object).getErrorCode() + "    " + ((BmobException) object).getMessage(), 0).show();
                ((BmobException) object).printStackTrace();
            } else {
                List<UserMaven> objectData = (List<UserMaven>) object;
                if (adapter == null) {
                    adapter = new PullAdapter<>(getActivity(), objectData);
                    mPullListView.setAdapter(adapter);
                } else {
                    adapter.updateListView(objectData);
                }
            }
            mRefreshLayout.loadMoreFinish(true);
            mRefreshLayout.refreshFinish(true);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapter.getItemViewType(i) == 0) return;
        ObjectData objectData = adapter.getItem(i);
        show(objectData);
    }
    private void download(ObjectData objectData){
        da = new Dialog(1).LoadingDialog(getActivity())
                .setTile(getString(R.string.s_25))
                .showProgrss(true)
                .setMessage(getString(R.string.s_26))
                .canClose(false)
                .canCloseOut(false).show();
        String name = Utils.getSD() + "/.CreateJS/download/" + objectData.getTitle() + "_.zip";
        objectData.setPath(name);
        new Download(getActivity(), objectData.getFile().getUrl(), name, (progress, max, finish, e) -> {
            da.setMax(max);
            da.setProgress((int) progress);
            if (e != null) {
                da.setMessage(e.getMessage());
            }
            if (finish) {
                new Thread(() -> {
                    try {
                        ZipUtils.loadZip(name, Environment.getExternalStorageDirectory() + "/.CreateJS/libs/js_libs/" + (new File(name).getName().split("_")[0]));
                        da.setMessage(getContext().getResources().getString(R.string.s_31));
                    } catch (Exception e1) {
                        da.setMessage(e1.getMessage());
                    }
                }).start();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void show(ObjectData obj){
        AlertDialog ad ;
        ad =  new AlertDialog.Builder(getContext())
                .setTitle(obj.getTitle())
                .setMessage(obj.getStr())
                .setPositiveButton("下载", null)
                .setNegativeButton("取消",null)
                .setNeutralButton("复制临时直链", null).show();
        ad.getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
            ad.dismiss();
            download(obj);
        });
        ad.getButton(DialogInterface.BUTTON3).setOnClickListener(view -> {
            ClipboardManager clip = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clip.setText(obj.getFile().getFileUrl());
            Toast.makeText(getContext(),"已复制临时直链  "+obj.getFile().getFileUrl(),1).show();
        });
    }







































    static class Data {
        public static void get(int page, FunctionCallBACK callback) {
            BmobQuery<UserMaven> query = new BmobQuery<>();
            query.order("-createdAt");
            if (page != 0) {
                query.setSkip(page * 20 + 1);
            } else {
                query.setSkip(0);
            }
            query.setLimit(20);
            query(query, callback);
        }

        private static void query(BmobQuery<UserMaven> query, FunctionCallBACK callBACK) {
            @SuppressLint("HandlerLeak")
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    callBACK.T(msg.obj);
                    super.handleMessage(msg);
                }
            };
            query.findObjects(new FindListener<UserMaven>() {
                @Override
                public void done(List<UserMaven> list, BmobException e) {
                    if (callBACK != null) {
                        Message message = new Message();
                        message.what = 0;
                        if (e != null)
                            message.obj = e;
                        else
                            message.obj = list;
                        handler.sendMessage(message);
                    }
                }
            });
        }
    }
}