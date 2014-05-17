package com.check.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 06.05.2014.
 */
public class ContentDtoList implements Serializable {
    private List<ContentDto> contentDtoList;

    public ContentDtoList(List<ContentDto> contentDtoList) {
        this.contentDtoList = contentDtoList;
    }

    public ContentDtoList() {
    }

    public List<ContentDto> getContentDtoList() {
        return contentDtoList;
    }

    public void setContentDtoList(List<ContentDto> contentDtoList) {
        this.contentDtoList = contentDtoList;
    }
}
