package com.cts.flow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TagView extends FrameLayout implements Checkable {

    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    private boolean isChecked;
    private int strokeWidth = 1;
    private int strokeColor = Color.RED;
    private int bgColor = Color.WHITE;


    public TagView(@NonNull Context context) {
        this(context, null);
    }

    public TagView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        setBackground(getBgDrawable());
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] state = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(state, CHECK_STATE);
        }
        return state;
    }

    @Override
    public void setChecked(boolean checked) {

        if (checked != this.isChecked) {
            this.isChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {

        setChecked(!isChecked);
    }


    public Drawable getBgDrawable() {

        GradientDrawable radiusBg = new GradientDrawable();
        //设置Shape类型
        radiusBg.setShape(GradientDrawable.RECTANGLE);
        //设置填充颜色
        radiusBg.setColor(bgColor);
        //设置线条粗心和颜色,px
        radiusBg.setStroke(strokeWidth, strokeColor);
        //设置圆角角度,如果每个角度都一样,则使用此方法
        radiusBg.setCornerRadius(15);

        return radiusBg;

    }

    /**
     * @param strokeWidth
     */
    public void setConfig(int strokeWidth, @ColorInt int strokeColor, @ColorInt int bgColor) {
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.bgColor = bgColor;

        Drawable drawable = getBgDrawable();

        setBackground(drawable);

    }
}
