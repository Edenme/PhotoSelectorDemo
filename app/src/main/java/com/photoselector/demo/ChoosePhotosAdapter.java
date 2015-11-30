package com.photoselector.demo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.ArrayList;


public class ChoosePhotosAdapter extends CommonBaseAdapter<PhotoEntity> {
    private int horizontalNum = 3;
    private int itemWidth;
    private int mMaxLimit;
    private String mMaxLimitTips;
    private AbsListView.LayoutParams itemLayoutParams;

    public ChoosePhotosAdapter(Context context, ArrayList<PhotoEntity> models, int maxLimit, String maxLimitTips, ChoosePhotosAdapter.OnSelectResultListener listener) {
        super(context, models);

        setItemWidth(ScreenUtils.getScreenWidth(context));
        mMaxLimit = maxLimit;
        mMaxLimitTips = maxLimitTips;
        this.mSelectResultListener = listener;
    }

    public ChoosePhotosAdapter(Context context, ArrayList<PhotoEntity> models, int maxLimit, String maxLimitTips) {
        super(context, models);
        setItemWidth(ScreenUtils.getScreenWidth(context));
        mMaxLimit = maxLimit;
        mMaxLimitTips = maxLimitTips;
    }

    /**
     * 设置每一个Item的宽高
     */
    public void setItemWidth(int screenWidth) {
        int horizontalSpace = context.getResources().getDimensionPixelSize(R.dimen.sticky_item_horizontalSpacing);
        this.itemWidth = (screenWidth - (horizontalSpace * (horizontalNum - 1))) / horizontalNum;
        this.itemLayoutParams = new AbsListView.LayoutParams(itemWidth, itemWidth);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoItemView photoItemView = null;
        if (convertView == null) {
            photoItemView = new PhotoItemView(context, mSelectResultListener);
            photoItemView.setLayoutParams(itemLayoutParams);
            convertView = photoItemView;
        } else {
            photoItemView = (PhotoItemView) convertView;
        }

        photoItemView.setImageDrawable(models.get(position));
        photoItemView.setMaxSelectedImgNum(mMaxLimit, mMaxLimitTips);
        photoItemView.setSelectStateWhenScroll();
        photoItemView.setCurrentIndex(position);

        return convertView;
    }


    private OnSelectResultListener mSelectResultListener;

    public void setOnSelectResultListener(OnSelectResultListener onSelectResultListener) {
        mSelectResultListener = onSelectResultListener;
    }

    public interface OnSelectResultListener {
        void onSelectResult(PhotoEntity photoEntity, boolean isChecked);

        void onItemClick(PhotoEntity photoEntity, int position);
    }


}
