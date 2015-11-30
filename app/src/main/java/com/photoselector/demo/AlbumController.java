package com.photoselector.demo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AlbumController {
    private ContentResolver mContentResolver;

    public AlbumController(Context context) {
        mContentResolver = context.getContentResolver();
    }

    /**
     * 获取所有相册
     *
     * @return 相册集合
     */
    public List<AlbumEntity> getAllAlbum() {
        List<AlbumEntity> albums = new ArrayList<>();
        Map<String, AlbumEntity> map = new HashMap<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.SIZE};

        Cursor cursor = mContentResolver.query(uri, projection, null, null, null);
        if (cursor == null || !cursor.moveToNext()) {
            return new ArrayList<>();
        }

        cursor.moveToLast();
        AlbumEntity current = new AlbumEntity("最近照片", 0, cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)), true); // "最近照片"相册
        albums.add(current);
        do {
            if (cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) < 1024 * 10)
                continue;
            current.increaseCount();
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            if (map.keySet().contains(name)) {
                map.get(name).increaseCount();
            } else {
                AlbumEntity album = new AlbumEntity(name, 1, cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
                map.put(name, album);
                albums.add(album);
            }
        } while (cursor.moveToPrevious());

        cursor.close();

        return albums;
    }

    /**
     * 获取对应相册下的照片
     */
    public List<PhotoEntity> getAssignedAlbumPhotos(String name) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DATE_ADDED,
                MediaStore.Images.ImageColumns.SIZE};
        String selection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = ?";

        Cursor cursor = mContentResolver.query(uri, projection, selection,
                new String[]{name}, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext())
            return new ArrayList<>();

        List<PhotoEntity> photos = new ArrayList<>();
        cursor.moveToLast();
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                PhotoEntity photoModel = new PhotoEntity();
                photoModel.setOriginalPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
                photos.add(photoModel);
            }
        } while (cursor.moveToPrevious());

        cursor.close();

        return photos;
    }

    /**
     * 获取最近照片列表
     */
    public List<PhotoEntity> getRecentAlbumPhotos() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE};

        Cursor cursor = mContentResolver.query(uri, projection, null, null, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext())
            return new ArrayList<>();

        List<PhotoEntity> photos = new ArrayList<>();
        cursor.moveToLast();
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                PhotoEntity photoModel = new PhotoEntity();
                photoModel.setOriginalPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
                photos.add(photoModel);
//                if (photos.size() > 30) {
//                    break;
//                }
            }
        } while (cursor.moveToPrevious());

        cursor.close();

        return photos;
    }

}
