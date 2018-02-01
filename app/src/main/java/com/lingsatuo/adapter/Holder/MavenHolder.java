package com.lingsatuo.adapter.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.lingsatuo.createjs.R;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MavenHolder extends RecyclerView.ViewHolder {
    public Button title;
    public Button title_local;
    public Button title_error;
    public MavenHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        title_local = itemView.findViewById(R.id.title_local);
        title_error = itemView.findViewById(R.id.title_error);
    }
}
