package com.ruiaa.timelock.main.modules.usage.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ruiaa on 2016/10/5.
 */

public class UsageLengthView extends View {

    private int maxUsage=0;
    private int thisUsage=0;


    public UsageLengthView(Context context) {
        super(context);
    }

    public UsageLengthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UsageLengthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //super(context, attrs);
        //TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.item_usage_length);
        //maxUsage=typedArray.getInt(R.styleable.item_usage_length_maxUsage,0);
        //thisUsage=typedArray.getInt(R.styleable.item_usage_length_thisUsage,0);
        //typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (thisUsage==0||maxUsage==0){
            setMeasuredDimension(0,0);
        }else {
            setMeasuredDimension((int)(MeasureSpec.getSize(widthMeasureSpec)*((double)thisUsage/(double)maxUsage)), MeasureSpec.getSize(heightMeasureSpec));
        }
    }

    public void setMaxUsage(int maxUsage) {
        this.maxUsage = maxUsage;
    }

    public void setThisUsage(int thisUsage) {
        this.thisUsage = thisUsage;
    }
}
