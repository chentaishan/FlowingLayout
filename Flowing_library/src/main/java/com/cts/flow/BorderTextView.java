package com.cts.flow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

public class BorderTextView extends AppCompatTextView {
    private int strokeWidth;    // 边框线宽
    private int strokeColor;    // 边框颜色
    private int enableColor;    // 不可点击颜色
    private int contentColor;   // 背景颜色
    private int pressedColor;   // 按下背景颜色
    private int cornerRadius;   // 圆角半径
    private boolean mFollowTextColor; // 边框颜色是否跟随文字颜色
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
        contentColor = ta.getColor(R.styleable.BorderTextView_contentBackColor, Color.TRANSPARENT);
        pressedColor = ta.getColor(R.styleable.BorderTextView_contentPressedColor, contentColor);
        enableColor = ta.getColor(R.styleable.BorderTextView_enableBackColor, Color.TRANSPARENT);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.BorderTextView_strokeWidth, 0);
        strokeColor = ta.getColor(R.styleable.BorderTextView_strokeColor, Color.GRAY);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.BorderTextView_cornerRadius, 0);
        mFollowTextColor = ta.getBoolean(R.styleable.BorderTextView_followTextColor, false);
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
        // 设置边框线的颜色, 如果声明为边框跟随文字颜色且当前边框颜色与文字颜色不同时重新设置边框颜色
        if (mFollowTextColor && strokeColor != getCurrentTextColor()) {
            strokeColor = getCurrentTextColor();
        }
        // 设置背景
        setBackground(getPressedSelector(enableColor, contentColor, pressedColor, cornerRadius));
    }

    private Drawable getPressedSelector(int enabledColor, int normalColor, int pressedColor, int radius) {
        Drawable enabled = createShape(enabledColor, radius);
        Drawable pressed = createShape(pressedColor, radius);
        Drawable normal = createShape(normalColor, radius);
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);    // 按下状态 , 设置按下的图片
        drawable.addState(new int[]{android.R.attr.state_enabled}, normal);     // 默认状态,默认状态下的图片
        drawable.addState(new int[]{}, enabled);
        return drawable;
    }

    private GradientDrawable createShape(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);//设置4个角的弧度
        drawable.setColor(color);// 设置颜色
        return drawable;
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
    public void setStrokeConfig(int strokeWidth,@ColorInt int strokeColor){
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;

        invalidate();
    }

    public void setRadiusWidth(int cornerRadius){
        this.cornerRadius = cornerRadius;
        invalidate();
    }
}

