package com.photoselector.demo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ChooseAlbumAdapter extends CommonBaseAdapter<AlbumEntity> {
    public ChooseAlbumAdapter(Context context, ArrayList<AlbumEntity> models) {
        super(context, models);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumItemView albumItem = null;
        if (convertView == null) {
            albumItem = new AlbumItemView(context);
            convertView = albumItem;
        } else
            albumItem = (AlbumItemView) convertView;
        albumItem.update(models.get(position));

        return convertView;
    }
}
