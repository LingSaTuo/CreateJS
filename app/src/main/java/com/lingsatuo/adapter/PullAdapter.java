package com.lingsatuo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingsatuo.bmob.ObjectData;
import com.lingsatuo.bmob.UserInfo;
import com.lingsatuo.createjs.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17.
 */

public class PullAdapter extends PullListViewAdapter {
    private Context context;
    private List<UserInfo> objects;
    public PullAdapter(Context context, List<ObjectData> objects) {
        super(context, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if (getItemViewType(position) == 0) {
                convertView = ((Activity) (this.context)).getLayoutInflater().inflate(R.layout.pull_list_no_data, parent, false);
                holder.linearLayout = convertView.findViewById(R.id.root_layout);
            } else {
                convertView = ((Activity) (mContext)).getLayoutInflater().inflate(R.layout.pull_listview_item, parent, false);
                holder.title = convertView.findViewById(R.id.title);
                holder.name = convertView.findViewById(R.id.name);
                holder.time = convertView.findViewById(R.id.time);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (hasNoData) {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(getScreenWidth(), getScreenHeight() * 2 / 3);
            holder.linearLayout.setLayoutParams(lp);
        } else {
            holder.title.setText(mDataList.get(position).getTitle());
           // holder.time.setText(mDataList.get(position).getStr());
            holder.name.setText(mDataList.get(position).getStr());
        }

        return convertView;
    }
    private final class ViewHolder {
        TextView title,name,time;
        LinearLayout linearLayout;
    }
    private int getScreenWidth() {
        DisplayMetrics displayMetric = Resources.getSystem().getDisplayMetrics();
        return displayMetric.widthPixels;
    }

    private int getScreenHeight() {
        DisplayMetrics displayMetric = Resources.getSystem().getDisplayMetrics();
        return displayMetric.heightPixels;
    }
}
