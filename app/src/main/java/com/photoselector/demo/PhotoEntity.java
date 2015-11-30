package com.photoselector.demo;

import java.io.Serializable;

public class PhotoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String originalPath;
    private boolean isChecked;

    public PhotoEntity(String originalPath, boolean isChecked) {
        super();
        this.originalPath = originalPath;
        this.isChecked = isChecked;
    }

    public PhotoEntity(String originalPath) {
        this.originalPath = originalPath;
    }

    public PhotoEntity() {
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
