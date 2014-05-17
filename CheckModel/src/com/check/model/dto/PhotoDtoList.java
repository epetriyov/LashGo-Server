package com.check.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 05.05.2014.
 */
public class PhotoDtoList implements Serializable {

    private List<PhotoDto> photoDtoList;

    public PhotoDtoList() {

    }

    public PhotoDtoList(List<PhotoDto> photoDtoList) {
        this.photoDtoList = photoDtoList;
    }

    public List<PhotoDto> getPhotoDtoList() {
        return photoDtoList;
    }

    public void setPhotoDtoList(List<PhotoDto> photoDtoList) {
        this.photoDtoList = photoDtoList;
    }
}
