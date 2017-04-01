package com.yss.percentagebarchart;

import android.content.Context;

/**
 * Created by yss on 2017/4/1.
 */

public class DensityUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
