package com.photoselector.demo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

public class PhotoItemView extends LinearLayout implements View.OnClickListener {
    private ImageView mIvPhoto;
    private ImageView mIvSelect;

    private PhotoEntity mPhotoEntity;
    private int mMaxSeleImgNum;
    private String mMaxLimitTips;
    private int mCurrentIndex;
    private ChoosePhotosAdapter.OnSelectResultListener mListener;

    public PhotoItemView(Context context, ChoosePhotosAdapter.OnSelectResultListener listener) {
        super(context);
        initView();
        mListener = listener;
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.photo_item_view, this, true);
        mIvPhoto = (ImageView) findViewById(R.id.iv_photo);
        mIvSelect = (ImageView) findViewById(R.id.iv_select);

        mIvPhoto.setOnClickListener(this);
        mIvSelect.setOnClickListener(this);
    }

    /**
     * 设置路径下的图片对应的缩略图
     */
    public void setImageDrawable(final PhotoEntity photo) {
        this.mPhotoEntity = photo;
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
//            Glide.with(getContext()).load("file://" + photo.getOriginalPath())
//                    .centerCrop().placeholder(R.mipmap.icon_picture_no).crossFade()
//                    .into(mIvPhoto);
//        } else {
//            ImageLoaderHelper.loadDefaultImageLoader(mIvPhoto, "file://" + photo.getOriginalPath(),
//                    ImageLoaderHelper.ImageType.PHOTO_CHOOSE_DEFAULT);
//        }
        ImageLoader.getInstance().displayImage("file://" + photo.getOriginalPath(), mIvPhoto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_photo:
                if (mListener != null) {
                    mListener.onItemClick(mPhotoEntity, mCurrentIndex);
                }
                break;
            case R.id.iv_select:
                togglePhotoSelState();
                break;
        }
    }

    public void setMaxSelectedImgNum(int maxNum, String limitTips) {
        mMaxSeleImgNum = maxNum;
        mMaxLimitTips = limitTips;
        if (maxNum == 1) {
            mIvSelect.setVisibility(View.GONE);
        } else {
            mIvSelect.setVisibility(View.VISIBLE);
        }
    }

    public void setCurrentIndex(int position) {
        mCurrentIndex = position;
    }


    private void togglePhotoSelState() {
//        boolean isChecked;
//        if (mPhotoEntity.isChecked()) {
//            isChecked = false;
//            mIvPhoto.clearColorFilter();
//            mIvSelect.setImageResource(R.mipmap.icon_photo_unselected);
//        } else {
//            if (ChoosePhotosActivity.mSelectedPhotoEntities.size() == mMaxSeleImgNum) {
//                YzToastUtils.show(getContext(), StringUtils.getNotNullString(mMaxLimitTips));
//                return;
//            }
//            isChecked = true;
//            mIvPhoto.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
//            mIvSelect.setImageResource(R.mipmap.icon_photo_select);
//        }
//
//        mPhotoEntity.setChecked(isChecked);
//
//        if (mListener != null) {
//            mListener.onSelectResult(mPhotoEntity, mPhotoEntity.isChecked());
//        }

    }

    public void setSelectStateWhenScroll() {
        if (mPhotoEntity.isChecked()) {
            mIvPhoto.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            mIvSelect.setImageResource(R.mipmap.icon_photo_select);
        } else {
            mIvPhoto.clearColorFilter();
            mIvSelect.setImageResource(R.mipmap.icon_photo_unselected);
        }
    }
}
