package com.cts.flow;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * flowingLayout 只负责排序子元素，比如计算一行是否放的下子元素，如果放不下换行继续排放，仅此而已
 */
public class FlowingLayout extends ViewGroup {

    private final int mGravity;

    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;
    private CommonAdapter mTagAdapter;

    DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();

            changeAdapter();
        }
    };
    private int l = 15;
    private int t = 5;
    private int r = 15;
    private int b = 5;

    public FlowingLayout(Context context) {
        this(context, null);
    }

    public FlowingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlowingLayout);
        mGravity = ta.getInt(R.styleable.FlowingLayout_tag_gravity, LEFT);
        ta.recycle();


    }

    private static final String TAG = "FlowingLayout";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取XML设置的大小和测量模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);


//        当前控件的宽度
        int width = getPaddingLeft() + getPaddingRight();

        int height = getPaddingTop() + getPaddingBottom();
        // 添加元素后，计算当前占用的行宽
        int lineWidth = 0;
        // 行高
        int lineHeight = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {

            View child = getChildAt(i);

            //测量子元素的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

//            获取子元素的宽高
            int childWidth = child.getMeasuredWidth() + leftMargin + rightMargin;
            ;
            int childHeight = child.getMeasuredHeight() + topMargin + bottomMargin;
            ;

            if (lineWidth + childWidth > widthSize) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;


                height += lineHeight;
                lineHeight = childHeight;
                Log.d(TAG, "onMeasure: " + height);
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);

            }
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }

        }

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? widthSize : width,
                modeHeight == MeasureSpec.EXACTLY ? heightSize : height);

    }

    //数据的总结构
    protected List<List<View>> mAllViews = new ArrayList<List<View>>(); //0-list   1-list
    //每一行的高度
    protected List<Integer> mLineHeight = new ArrayList<Integer>();
    //每一行的宽度
    protected List<Integer> mLineWidth = new ArrayList<Integer>();

    //每一行的子元素
    private List<View> lineViews = new ArrayList<>();

    private int leftMargin = 10;
    private int rightMargin = 10;
    private int topMargin = 5;
    private int bottomMargin = 5;

    /**
     * 设置左右 外边距
     *
     * @param margin 注意是px
     */
    public void setChildLRMargin(int margin) {

        setLeftMargin(margin);
        setRightMargin(margin);

    }

    /**
     * 设置上下边距
     *
     * @param margin
     */
    public void setChildTBMargin(int margin) {

        setTopMargin(margin);
        setBottomMargin(margin);

    }

    private void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }


    /**
     * 设置内边距    注意---像素px
     *
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public void setPadding(int l, int t, int r, int b) {
        this.l = l;
        this.t = t;
        this.r = r;
        this.b = b;
    }


    private void setTopMargin(int topMargin) {

        this.topMargin = topMargin;
    }

    public void setLeftMargin(int margin) {
        this.leftMargin = margin;
    }

    public void setRightMargin(int margin) {
        this.rightMargin = margin;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        mLineWidth.clear();
        lineViews.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {

            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) continue;

//            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //子元素的宽高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();


            if (childWidth + lineWidth + leftMargin + rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                mLineWidth.add(lineWidth);

                lineWidth = 0;
                lineHeight = childHeight + topMargin + bottomMargin;
                ;
                lineViews = new ArrayList<View>();
            }
            lineWidth += childWidth + leftMargin + rightMargin;
            ;
            lineHeight = Math.max(lineHeight, childHeight + topMargin + bottomMargin);
            lineViews.add(child);

        }

        mLineHeight.add(lineHeight);
        mLineWidth.add(lineWidth);
        mAllViews.add(lineViews);

        //开始布局子元素

        int left = getPaddingLeft();
        int top = getPaddingTop();

//        获取一共多少行view
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            //获取每一行的view集合
            lineViews = mAllViews.get(i);
//            获取每一行的高度
            lineHeight = mLineHeight.get(i);

            // set gravity 获取每一行的宽度
            int currentLineWidth = this.mLineWidth.get(i);

            left = setGravity(width, currentLineWidth);

            layoutLineChild(left, top);

            top += lineHeight;
        }

    }


    /**
     * 布局每行的view到当前容器里
     */
    public void layoutLineChild(int left, int top) {
        for (int j = 0; j < lineViews.size(); j++) {
            View child = lineViews.get(j);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            int lc = left + leftMargin;
            int tc = top + topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();

            child.layout(lc, tc, rc, bc);

            left += child.getMeasuredWidth() + leftMargin + rightMargin;
        }

    }

    /**
     * 解析gravity属性
     */
    public int setGravity(int width, int currentLineWidth) {
        int left = 0;
        switch (this.mGravity) {
            case LEFT:
                left = getPaddingLeft();
                break;
            case CENTER:
                left = (width - currentLineWidth) / 2 + getPaddingLeft();
                break;
            case RIGHT:
                left = width - currentLineWidth + getPaddingLeft();
                break;
        }

        return left;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    /**
     * 设置适配器
     *
     * @param adapter
     */
    public <D, V> void setAdapter(CommonAdapter<D, V> adapter) {

        if (mTagAdapter != null) {
            mTagAdapter.unregisterDataSetObserver(dataSetObserver);
        }
        mTagAdapter = adapter;
        mTagAdapter.registerDataSetObserver(dataSetObserver);

        changeAdapter();

    }

    private <V> void changeAdapter() {
        removeAllViews();

        for (int i = 0; i < mTagAdapter.getCount(); i++) {
            //获取内部子元素控件
            V tagView = (V) mTagAdapter.getView(this, i, mTagAdapter.getItem(i));

            View itemView = (View) tagView;
            itemView.setPadding(l, t, r, b);

            int finalI = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 重置上次选中的状态
                    resetSelectBGDrawable();

                    if (onItemChangedListener != null) {
                        onItemChangedListener.itemClick(tagView, finalI);
                    }
                    setSelectBGDrawable(itemView, finalI);
                }
            });
            if (unSelectedDrawable != null) {
                itemView.setBackground(unSelectedDrawable);
            }
            //第一个被默认选中
            if (currSelectedPos != -1 && finalI == 0) {
                setSelectBGDrawable(itemView, finalI);
            }
            addView(itemView);
        }
    }

    /**
     * 设置默认显示第一个被选中
     */
    public void setSelectedFirstItem() {
        currSelectedPos = 0;
    }

    private void setSelectBGDrawable(View itemView, int finalI) {
        if (selectedDrawable == null)
            return;
        currSelectedPos = finalI;
        itemView.setBackground(selectedDrawable);
    }

    /**
     * 重置上次选中的状态
     */
    private <V> void resetSelectBGDrawable() {
        if (unSelectedDrawable == null)
            return;
        if (mTagAdapter == null || currSelectedPos == -1)
            return;
        for (int i = 0; i < mTagAdapter.getCount(); i++) {
            if (currSelectedPos == i) {
                V item = (V) mTagAdapter.getView(this, i, mTagAdapter.getItem(i));
                ((View) item).setBackground(unSelectedDrawable);
            }
        }
    }

    //单选记录上次选中的view的下标
    private int currSelectedPos = -1;

    private Drawable selectedDrawable;
    private Drawable unSelectedDrawable;

    private OnItemChangedListener onItemChangedListener;

    public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener) {
        this.onItemChangedListener = onItemChangedListener;
    }

    public void setBgDrawable(Drawable selectedDrawable, Drawable unSelectedDrawable) {
        this.selectedDrawable = selectedDrawable;
        this.unSelectedDrawable = unSelectedDrawable;
    }

    public interface OnItemChangedListener<V> {
        void itemClick(V itemView, int pos);
    }
}