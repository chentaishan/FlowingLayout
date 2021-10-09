package com.cts.flow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

public class BorderTextView extends AppCompatTextView {
    private int strokeWidth;    // 边框线宽
    private int strokeColor;    // 边框颜色
    private int contentColor;   // 背景颜色
    private int cornerRadius;   // 圆角半径
    private Paint mPaint = new Paint();                 // 画边框所使用画笔对象
    private RectF mRectF = new RectF();                 // 画边框要使用的矩形

    public BorderTextView(Context context) {
        this(context, null);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 读取属性值
        init(context, attrs);

        // 初始View
        initView();
    }

    /**
     * 初始资源
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BorderTextView);
        contentColor = ta.getColor(R.styleable.BorderTextView_contentBackColor, Color.WHITE);
        pressedColor = ta.getColor(R.styleable.BorderTextView_contentPressedColor,getResources().getColor(R.color.press_color));
        enableColor = ta.getColor(R.styleable.BorderTextView_enableBackColor, getResources().getColor(R.color.press_color));
        strokeWidth = ta.getDimensionPixelSize(R.styleable.BorderTextView_strokeWidth, 1);
        strokeColor = ta.getColor(R.styleable.BorderTextView_strokeColor, Color.GRAY);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.BorderTextView_cornerRadius, 0);
        ta.recycle();
    }

    /**
     * 初始化View
     */
    private void initView() {
        // 初始化画笔
        mPaint.setStyle(Paint.Style.STROKE);     // 空心效果
        mPaint.setAntiAlias(true);               // 设置画笔为无锯齿
        mPaint.setStrokeWidth(strokeWidth);      // 线宽

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置画笔颜色
        mPaint.setColor(strokeColor);
        // 画空心圆角矩形
        // 边框线宽大于0才画
        if (strokeWidth > 0) {
            mRectF.left = mRectF.top = 0.5f * strokeWidth;
            mRectF.right = getMeasuredWidth() - 0.5f * strokeWidth;
            mRectF.bottom = getMeasuredHeight() - 0.5f * strokeWidth;
            canvas.drawRoundRect(mRectF, cornerRadius, cornerRadius, mPaint);
        }
    }
    /**
     * 设置圆角度数、边框颜色、边框宽度、字体颜色
     * @param cornerRadius
     * @param strokeWidth
     * @param strokeColor
     * @param textColor
     */
    public void setStrokeConfig(int cornerRadius,int strokeWidth,@ColorInt int strokeColor,@ColorInt int textColor,@ColorInt int contentColor){
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.cornerRadius = cornerRadius;
        this.contentColor = contentColor;
        setTextColor(textColor);
        setBackgroundColor(this.contentColor);
        invalidate();
    }
}

