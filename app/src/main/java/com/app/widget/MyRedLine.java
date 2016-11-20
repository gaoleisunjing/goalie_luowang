package com.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.app.luooapp.R;

/**
 * Created by hao on 2016-09-27.
 */
public class MyRedLine extends View {
    private Paint mPaint;

    public MyRedLine(Context context) {
        this(context, null);
    }

    public MyRedLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRedLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorTxtSelected));
        mPaint.setStrokeWidth(7);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(15,10,15,50,mPaint);
    }
}
