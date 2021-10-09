package com.cts.flow;

import android.content.Context;
import android.util.SparseArray;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public abstract class CommonFlowAdapter<D, V> extends FlowBaseAdapter<D, V> {
    protected List<D> mDatas = new ArrayList<>();
    protected SparseArray<V> mViews = new SparseArray<>();

    private Context mContext;

    private Class<V> viewClass;

    public CommonFlowAdapter(Context context, Class<V> viewClass, List<D> datas) {
        this.mContext = context;
        this.viewClass = viewClass;
        this.mDatas.clear();
        this.mDatas.addAll(datas);
    }

    public CommonFlowAdapter(Context context, Class<V> viewClass) {
        this.mContext = context;
        this.viewClass = viewClass;
    }

    public void addItems(List<D> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public V getView(FlowingLayout flowingLayout, int pos, D itemData) {

        V itemView = mViews.get(pos);
        if (itemView==null){
            itemView = newTclass(viewClass);
            mViews.put(pos,itemView);
        }
        convert(itemView, pos, itemData);
        return itemView;
    }

    public abstract void convert(V itemView, int position, D item);

    private <T> T newTclass(Class<T> clazz) {
        T a = null;
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(Context.class);
            a = constructor.newInstance(this.mContext);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return a;
    }
}