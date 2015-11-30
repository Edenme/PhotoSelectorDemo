package com.photoselector.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class AlbumItemView extends LinearLayout {
    private ImageView ivAlbum;
    private TextView tvName, tvCount;

    public AlbumItemView(Context context) {
        this(context, null);
    }

    public AlbumItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.album_item_view, this, true);

        ivAlbum = (ImageView) findViewById(R.id.iv_album);
        tvName = (TextView) findViewById(R.id.tv_name_la);
        tvCount = (TextView) findViewById(R.id.tv_count);
    }

    public AlbumItemView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    /**
     * 设置相册封面
     */
    public void setAlbumImage(String path) {
        ImageLoader.getInstance().displayImage("file://" + path, ivAlbum);
    }

    /**
     * 初始化
     */
    public void update(AlbumEntity album) {
        setAlbumImage(album.getRecent());
        setName(album.getName());
        setCount(album.getCount());
    }

    public void setName(CharSequence title) {
        tvName.setText(title);
    }

    public void setCount(int count) {
        tvCount.setHint(count + "张");
    }
}

