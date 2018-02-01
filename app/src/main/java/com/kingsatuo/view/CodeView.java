package com.kingsatuo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lingsatuo.createjs.R;


/**
 * Created by Administrator on 2017/11/5.
 */

public class CodeView extends Fragment {
    private io.github.kbiakov.codeview.CodeView mCodeView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Context context = getContext();
        if (context==null)
            return null;
        View rowView = inflater.inflate(R.layout.fragment_codeview, container, false);
        io.github.kbiakov.codeview.CodeView codeView = rowView.findViewById(R.id.codeview);
        setHasOptionsMenu(true);
        mCodeView = codeView;
        return codeView;
    }

    public io.github.kbiakov.codeview.CodeView getCodeView(){
        return mCodeView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
