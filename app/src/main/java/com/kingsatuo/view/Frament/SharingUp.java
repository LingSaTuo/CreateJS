package com.kingsatuo.view.Frament;

import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lingsatuo.Dialog.FileSelector;
import com.lingsatuo.bmob.DataInfo;
import com.lingsatuo.bmob.ObjectData;
import com.lingsatuo.bmob.file.ObjectFile;
import com.lingsatuo.createjs.R;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.openapi.Files;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/11/18.
 */

public class SharingUp extends Fragment implements View.OnClickListener {
    TextView textView;
    EditText title, introduction;
    Dialog.A dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sharing_up, container, false);
        Button button = view.findViewById(R.id.select);
        button.setOnClickListener(this);
        textView = view.findViewById(R.id.path);
        view.findViewById(R.id.up).setOnClickListener(this);
        title = view.findViewById(R.id.title);
        introduction = view.findViewById(R.id.messgae);
        return view;
    }

    @Override
    public void onClick(View view) {

        if(getContext()==null){
            android.os.Process.killProcess(Process.myPid());
        }
        switch (view.getId()) {
            case R.id.select:
                new FileSelector(new File(new Files().getSD()), getContext(), object -> {
                    File file = (File) object;
                    textView.setText(file.getPath());
                });
                break;
            case R.id.up:
                dialog = new Dialog(1).LoadingDialog(getActivity());
                dialog.setMessage(getString(R.string.s_69))
                        .canClose(false)
                        .canCloseOut(false)
                        .showProgrss(false)
                        .setTile(getString(R.string.s_57))
                        .setWindowType(true)
                        .show();
                String t = title.getText().toString();
                t = t.replaceAll(" ", "");
                if (t == "") {
                    dialog.dismiss();
                    Snackbar.make(view, R.string.s_67, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                String mes = introduction.getText().toString();
                BmobFile bmobFile = new BmobFile(new File(textView.getText().toString()));
                ObjectFile file = new ObjectFile(bmobFile);
                ObjectData objectData = new ObjectData();
                objectData.setFile(file);
                objectData.setTitle(t);
                objectData.setStr(mes);
                new DataInfo().createNewOne(objectData).upFile(p -> {
                    if (!p[2].equals("")) {
                        Snackbar.make(view, p[2], Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(view, getString(R.string.s_68), Snackbar.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
                break;
        }
    }
}
