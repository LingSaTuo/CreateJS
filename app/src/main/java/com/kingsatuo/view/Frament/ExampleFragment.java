package com.kingsatuo.view.Frament;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kingsatuo.view.Frament.ExampleData.EPData;
import com.kingsatuo.view.Frament.ExampleData.ExampleAdapter;
import com.lingsatuo.createjs.CodeReader;
import com.lingsatuo.createjs.R;

/**
 * Created by Administrator on 2017/11/6.
 */

public class ExampleFragment extends Fragment implements ExpandableListView.OnChildClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.example_layout,container,false);
        ExpandableListView elv = view.findViewById(R.id.example_list);

        if(getContext()==null){
            android.os.Process.killProcess(Process.myPid());
        }
        ExampleAdapter adapter = new ExampleAdapter(getContext());
        elv.setAdapter(adapter);
        elv.deferNotifyDataSetChanged();
        elv.setOnChildClickListener(this);
        return view;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        EPData epData = (EPData) expandableListView.getExpandableListAdapter().getChild(i,i1);
        Intent intent = new Intent(getContext(), CodeReader.class);
        intent.putExtra("path",epData.path);
        getContext().startActivity(intent);
        return false;
    }
}
