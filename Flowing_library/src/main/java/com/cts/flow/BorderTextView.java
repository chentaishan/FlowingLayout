package com.cts.flow;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 快速实现文字组件添加 边框颜色，边框宽度，内容颜色，圆角
 * <p>
 * 1.可以调用setDefaultConfig 使用默认配置
 * 2.可以使用setSelfConfig 配置自己的实现
 */
public class BorderTextView extends AppCompatTextView {

    public BorderTextView(Context context) {
        this(context, null);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    private GradientDrawable createShape(int cornerRadius, @ColorInt int shapeColor, int strokeWidth, @ColorInt int strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(shapeColor);// 设置颜色
        drawable.setCornerRadius(cornerRadius);//设置4个角的弧度
        drawable.setStroke(strokeWidth, strokeColor);

        return drawable;
    }

    /**
     * 使用默认配置的
     * 圆角边框 边框宽度，边框颜色 内容着色
     */
    public void setDefaultConfig() {
        GradientDrawable shape = createShape(5, getResources().getColor(R.color.selected_bg_color), 1, getResources().getColor(R.color.stroke_color));
        setBackground(shape);
    }

    /**
     * 配置
     * 圆角边框 边框宽度，边框颜色 内容着色
     */
    public void setSelfConfig(int cornerRadius, int shapeColor, int strokeWidth, int strokeColor) {
        GradientDrawable shape = createShape(cornerRadius, shapeColor, strokeWidth, strokeColor);
        setBackground(shape);
    }
}

