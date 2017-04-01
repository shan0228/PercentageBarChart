package com.yss.percentagebarchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yss on 2017/3/31.
 */

public class PercentageBarChartView extends View {
    float[] radii={10,10,0,0,0,0,10,10};//画圆角的参数
    private Paint mTitlePaint;//画title的画笔
    private Paint mRectPaint;//画比例bar的画笔
    private Bitmap mIcon;//title前的icon
    private float mItemHeight;//每一组数据的高
    private Rect mTitleBounds;
    private float mBarHeight;//横条的高度
    private List<ItemData> mDataList =new ArrayList<>();
    private  int[] mBarColorList;//比例bar的颜色值
    private float mSpace = 40;//没一个item的间隔
    private List<Path> mPathList=new ArrayList<>();//画比例时候 控制矩形圆角 用的path

    public PercentageBarChartView(Context context) {
        this(context,null);
    }

    public PercentageBarChartView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PercentageBarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int color = 0;
        float  textSize =0;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PercentageBarChartView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.PercentageBarChartView_titleTextColor:
                    // 默认颜色设置为黑色
                    color = a.getColor(attr,Color.BLACK);
                    break;
                case R.styleable.PercentageBarChartView_titleIcon:

                    mIcon = BitmapFactory.decodeResource(context.getResources(),a.getResourceId(attr, R.drawable.ic_launcher));
                    break;
                case R.styleable.PercentageBarChartView_titleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    textSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.PercentageBarChartView_barHeight:
                    mBarHeight = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                    Log.e("yss",mBarHeight+"");
                    break;
            }

        }
        a.recycle();
        mTitlePaint =new Paint();
        mTitlePaint.setColor(color);
        mTitlePaint.setStrokeWidth(2);
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setTextAlign(Paint.Align.LEFT);
        mTitlePaint.setTextSize(textSize);
        mTitleBounds = new Rect();
        mTitlePaint.getTextBounds(2017-03-31 + "", 0, (2017-03-31 + "").length(), mTitleBounds);
        mRectPaint =new Paint();
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setTextSize(1f);
        mRectPaint.setAntiAlias(true);
        mRectPaint.setColor(Color.RED);
        //初始化画圆角矩形的path

        //每条data所占的高度 title和bar之间的间隔mSpace/2
        mItemHeight = mTitleBounds.height() + mBarHeight + mSpace*2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int windowWidth = wm.getDefaultDisplay().getWidth();
        int windowHeight = wm.getDefaultDisplay().getHeight();

        if(windowHeight < mItemHeight*mDataList.size()){
            windowHeight =  (int)mItemHeight*mDataList.size();
        }
        int width = measureDimension(windowWidth, widthMeasureSpec);
        int height = measureDimension(windowHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
    public int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;

        } else {
            result = defaultSize;   //UNSPECIFIED

        }
        return result;
    }

    private void drawTitle(Canvas canvas,String title,int itemCount){
        mTitlePaint.getTextBounds(title, 0, title.length(), mTitleBounds);
        RectF rectF =new RectF(0,mItemHeight*itemCount ,mTitleBounds.height(),mTitleBounds.height()+mItemHeight*itemCount);
        canvas.drawBitmap(mIcon,null,rectF,mTitlePaint);
        canvas.drawText(title,mTitleBounds.height()+mSpace/2,mTitleBounds.height()+mItemHeight*itemCount,mTitlePaint);

    }

    private void initPath(){
        if(mBarColorList.length<1){
            return;
        }
        for (int i=0;i<mBarColorList.length-1;i++){
            mPathList.add(new Path());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawBar(Canvas canvas, double[] percentage, int itemCount){
        if(percentage.length < 1 && mBarColorList.length < percentage.length){
            return;
        }
        float top =mSpace*2/3+mTitleBounds.height()+mItemHeight*itemCount;
        float bottom = mSpace*2/3+mTitleBounds.height()+mBarHeight+mItemHeight*itemCount;
        double totol=0;
        for (int j=0;j< percentage.length;j++){
            totol+=percentage[0];
        }
        double scale = 0;
        for (int i=0;i<percentage.length;i++){

            mRectPaint.setColor(mBarColorList[i]);
            if(i==0){
                canvas.drawRoundRect(0,top,getWidth(),bottom,10,10,mRectPaint);
            }else {
                scale+=percentage[i-1]/totol;
                if(mPathList.get(i-1) != null){
                    mPathList.get(i-1).addRoundRect(0,top, (float) (getWidth()*(1-scale)),bottom,radii, Path.Direction.CCW);
                    canvas.drawPath(mPathList.get(i-1),mRectPaint);
                }

            }
        }


    }
    //设置数据
    public void setChartData(List<ItemData> dataList){
        this.mDataList =dataList;


    }
    //设置bar的比例颜色
    public void setBarColor(int[] barColorlist){
        this.mBarColorList =barColorlist;
        initPath();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<mDataList.size();i++){
            drawTitle(canvas,mDataList.get(i).getTitle(),i);
            drawBar(canvas,mDataList.get(i).getData(),i);
        }
    }


    class ItemData{
        private  String title;
        private double[] data;
        public ItemData( String title,double[] data){
            this.title=title;
            this.data=data;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double[] getData() {
            return data;
        }

        public void setData(double[] data) {
            this.data = data;
        }
    }
}
