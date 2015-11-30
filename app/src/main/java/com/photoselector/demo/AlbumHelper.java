package com.photoselector.demo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.List;

public class AlbumHelper {

    private AlbumController mAlbumController;

    public AlbumHelper(Context context) {
        mAlbumController = new AlbumController(context);
    }

    public void updateAllAlbums(final OnLocalAlbumListener onLocalAlbumListener) {
        final Handler handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                onLocalAlbumListener.onAlbumLoaded((List<AlbumEntity>) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AlbumEntity> albumEntities = mAlbumController.getAllAlbum();
                Message message = handler.obtainMessage();
                message.obj = albumEntities;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void updateAssignedAlbumPhotos(final String name, final OnLocalPhotoListener onLocalPhotoListener) {
        final Handler handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                onLocalPhotoListener.onPhotoLoaded((List<PhotoEntity>) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<PhotoEntity> photoEntities = mAlbumController.getAssignedAlbumPhotos(name);
                Message message = handler.obtainMessage();
                message.obj = photoEntities;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void updateRecentAlbumPhoto(final OnLocalPhotoListener onLocalPhotoListener) {
        final Handler handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                onLocalPhotoListener.onPhotoLoaded((List<PhotoEntity>) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<PhotoEntity> photoEntities = mAlbumController.getRecentAlbumPhotos();
                Message message = handler.obtainMessage();
                message.obj = photoEntities;
                handler.sendMessage(message);
            }
        }).start();
    }


    public interface OnLocalAlbumListener {
        void onAlbumLoaded(List<AlbumEntity> albumEntityList);
    }

    public interface OnLocalPhotoListener {
        void onPhotoLoaded(List<PhotoEntity> photoEntityList);
    }
}


