package com.cts.flow;

import android.database.DataSetObservable;
import android.database.DataSetObserver;


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


}
