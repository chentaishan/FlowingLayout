package com.cts.flow;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;


public abstract class BaseAdapter<D, V> {
    private DataSetObservable mDataSetObservable = new DataSetObservable();


    public void unregisterDataSetObserver(DataSetObserver mDataSetObserver) {
        mDataSetObservable.unregisterObserver(mDataSetObserver);
    }

    public void registerDataSetObserver(DataSetObserver mDataSetObserver) {
        mDataSetObservable.registerObserver(mDataSetObserver);
    }

    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public abstract int getCount();

    public abstract D getItem(int pos);

    public abstract V getView(FlowingLayout flowingLayout, int i, D item);

    /**
     * 可以配置itemview的点击状态和未点击状态下的Drawable
     * eg: 边框宽度，边框颜色 圆角弧度 内容颜色
     *    GradientDrawable gradientDrawable = new GradientDrawable();
     *         gradientDrawable.setShape(GradientDrawable.RECTANGLE);
     *         gradientDrawable.setColor(shapeColor);
     *         gradientDrawable.setStroke(strokeWidth, strokeColor);
     *         gradientDrawable.setCornerRadius(cornerRadius);
     *
     * @return Drawable对象
     */
    protected abstract Drawable setSelectedSeletor();

    /**
     * 同上，配置未点击状态下的Drawable
     * @return Drawable对象
     */
    protected abstract Drawable setUnSelectedSeletor();

}
