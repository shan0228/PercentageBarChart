package com.yss.percentagebarchart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by yss on 2017/4/1.
 */

public class PercentageBarChart2View extends FrameLayout {

    private int[] resId = new int[]{R.drawable.bg4_shape, R.drawable.bg2_shape,
            R.drawable.bg3_shape,
            R.drawable.bg_shape};
    private double [] scales = new double[4];
    private int mWidth;
    public PercentageBarChart2View(Context context) {
        this(context,null);
    }

    public PercentageBarChart2View(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
        this(context,attrs,0);
    }

    public PercentageBarChart2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setScales(double[] scales) {
        this.scales = scales;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int count = getChildCount();
        //依次改变textview的宽度
        for (int i = 0; i < count; i++) {
            TextView view = (TextView) getChildAt(i);
            float scale = 0;
            for(int j = 0;j<scales.length-i;j++){
                scale+=scales[j];
            }
            view.getLayoutParams().width = (int) (mWidth*scale);
        }
    }

    private void init(Context context) {
        mWidth = context.getResources().getDisplayMetrics().widthPixels-DensityUtil.dip2px(context, 40);
        //创建四个textview 宽度都为ViewGroup.LayoutParams.MATCH_PARENT 依次覆盖
        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(context);
            textView.setHeight(DensityUtil.dip2px(context, 20));
            textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setBackgroundResource(resId[i]);
            addView(textView);
        }
    }


}
