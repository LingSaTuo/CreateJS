package com.kingsatuo.view;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.jraska.console.Console;

/**
 * Created by 15176 on 2017/9/23.
 */

public class LogView extends
        Console{
    private int color = Color.BLACK;
    private boolean AutoScroll = true;
    public LogView(Context context) {
        this(context,null);
    }
    public void setDefColor(int color){
        this.color = color;
    }
    public LogView(Context context, AttributeSet attributeSet){
        this(context,attributeSet,0);
    }
    public LogView(Context context, AttributeSet attributeSet, int def){
        super(context,attributeSet,def);
        setSelected(true);
    }
    private SpannableString getSpannable(CharSequence charSequence,int color){
        SpannableString spannable = new SpannableString(charSequence);
        spannable.setSpan(new ForegroundColorSpan(color),0,charSequence.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannable;
    }
    public void clear(){
        super.clear();
    }
    public void addLog(CharSequence log,int color){
        if (log==null)return;
        SpannableString spannable = getSpannable(log,color);
        super.write(spannable);
    }
    public void write(CharSequence log){
        super.write(log);
    }
    public void setAutoScroll(boolean autoScroll){
        this.AutoScroll = autoScroll;
    }
    public boolean getAutoScroll(){
        return this.AutoScroll;
    }
    public void addLog(CharSequence log, String color){
        int mColor ;
        try{
            mColor = Color.parseColor(color);
        }catch (Exception e){
            mColor = this.color;
        }
        addLog(log,mColor);
    }
}
