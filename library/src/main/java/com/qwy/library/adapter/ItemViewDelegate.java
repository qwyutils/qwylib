package com.qwy.library.adapter;

public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(CommonViewHolder holder, T t, int position);

}
