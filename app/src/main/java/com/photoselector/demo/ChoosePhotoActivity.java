package com.photoselector.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChoosePhotoActivity extends AppCompatActivity implements ChoosePhotosAdapter.OnSelectResultListener, AdapterView.OnItemClickListener  {
    public static final String EXTRAS_SELECTED_PIC_RESULT = "extra_result";
    public static final String EXTRAS_MAX_SELECT_PIC_COUNT = "extra_max_count";
    public static final String EXTRAS_MAX_TIPS = "extra_max_tips";//超出最多时的提示

    private static final int REQUEST_CODE = 10;

    private GridView mGridView;
    private ListView mListView;
    private TextView mTvSendPhoto;
    private TextView mTvPreviewPhoto;
    private TextView mTvCancel;
    private ImageButton mIvBack;
    private TextView mTvTopTitle;
    private View mBottomView;


    private ChooseAlbumAdapter mChooseAlbumAdapter;
    private ChoosePhotosAdapter mChoosePhotoAdapter;
    private AlbumHelper mAlbumHelper;

    private String mMaxLimitTips;
    private int mMaxSelectPicCount;//最大照片选择数量

    public static final String RECENT_PHOTO = "最近照片";

    public static ArrayList<PhotoEntity> mSelectedPhotoEntities = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_choose_photo);

        initView();

        initData();
    }

    private void initView() {
        mMaxSelectPicCount = getIntent().getIntExtra(EXTRAS_MAX_SELECT_PIC_COUNT, 1);
        mMaxLimitTips = getIntent().getStringExtra(EXTRAS_MAX_TIPS);

        mGridView = (GridView) findViewById(R.id.gridView);
        mListView = (ListView) findViewById(R.id.list_view);
        mTvSendPhoto = (TextView) findViewById(R.id.tv_sending);
        mTvPreviewPhoto = (TextView) findViewById(R.id.tv_preview);
        mIvBack = (ImageButton) findViewById(R.id.ivBack);
        mTvCancel = (TextView) findViewById(R.id.tvCancel);
        mTvTopTitle = (TextView) findViewById(R.id.tvTitle);
        mBottomView = findViewById(R.id.bottom_view);

        mTvTopTitle.setText(RECENT_PHOTO);
        mTvSendPhoto.setTextColor(getResources().getColorStateList(R.color.selector_text_orange));
        mTvSendPhoto.setEnabled(false);
        mTvPreviewPhoto.setTextColor(getResources().getColorStateList(R.color.selector_text_enable));
        mTvPreviewPhoto.setEnabled(false);
        mTvPreviewPhoto.setText("预览");

        if (mMaxSelectPicCount <= 1) {
            mBottomView.setVisibility(View.GONE);
        }
    }

    private void initData() {
        mAlbumHelper = new AlbumHelper(this);
        mChoosePhotoAdapter = new ChoosePhotosAdapter(this, new ArrayList<PhotoEntity>(), mMaxSelectPicCount,mMaxLimitTips, this);
        mGridView.setAdapter(mChoosePhotoAdapter);
        mChooseAlbumAdapter = new ChooseAlbumAdapter(this, new ArrayList<AlbumEntity>());
        mListView.setAdapter(mChooseAlbumAdapter);

        mListView.setOnItemClickListener(this);

        mAlbumHelper.updateAllAlbums(mAlbumListener);
        mAlbumHelper.updateRecentAlbumPhoto(mRecentPhotoListener);
    }

    /**
     * 显示隐或藏相册
     */
    private void toggleAlbum() {
        if (mListView.getVisibility() == View.GONE) {
            popAlbum();
        } else {
            hideAlbum();
        }
    }

    /**
     * 弹出相册列表
     */
    private void popAlbum() {
        mListView.setVisibility(View.VISIBLE);
        mIvBack.setVisibility(View.INVISIBLE);
        mTvTopTitle.setText(getResources().getString(R.string.choose_album));
    }

    /**
     * 隐藏相册列表
     */
    private void hideAlbum() {
        mListView.setVisibility(View.GONE);
        mIvBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onSelectResult(PhotoEntity photoEntity, boolean isChecked) {

    }

    @Override
    public void onItemClick(PhotoEntity photoEntity, int position) {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_preview:
                Bundle bundle = new Bundle();
//                bundle.putSerializable("photos", mSelectedPhotoEntities);
                previewPhotos(bundle);
                break;
            case R.id.tv_sending:
                prepareToSendingPhotos();
                break;
        }
    }

    private void previewPhotos(Bundle bundle) {
//        Intent intent = new Intent(this, PhotoPreviewActivity.class);
//        intent.putExtras(bundle);
//        startActivityForResult(intent, REQUEST_CODE);
    }

    private void prepareToSendingPhotos() {
        ArrayList<String> mSelectedImageList = new ArrayList<>();
        for (PhotoEntity mSelectedPhotoEntite : mSelectedPhotoEntities) {
            mSelectedImageList.add(mSelectedPhotoEntite.getOriginalPath());
        }
        Intent intent = new Intent();
        intent.putStringArrayListExtra(EXTRAS_SELECTED_PIC_RESULT, mSelectedImageList);
        setResult(RESULT_OK, intent);
        finish();
    }

    private AlbumHelper.OnLocalAlbumListener mAlbumListener = new AlbumHelper.OnLocalAlbumListener() {
        @Override
        public void onAlbumLoaded(List<AlbumEntity> albumEntityList) {
            mChooseAlbumAdapter.update(albumEntityList);
        }
    };

    private AlbumHelper.OnLocalPhotoListener mRecentPhotoListener = new AlbumHelper.OnLocalPhotoListener() {
        @Override
        public void onPhotoLoaded(List<PhotoEntity> photoEntityList) {
            mChoosePhotoAdapter.update(photoEntityList);
            reset();
        }
    };

    /**
     * 清空选中的图片
     */
    private void reset() {
        mSelectedPhotoEntities.clear();
        mTvSendPhoto.setText("发送");
        mTvPreviewPhoto.setEnabled(false);
        mTvSendPhoto.setEnabled(false);
    }
}
