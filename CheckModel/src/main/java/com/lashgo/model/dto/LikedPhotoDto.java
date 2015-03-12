package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by Eugene on 06.03.2015.
 */
@ApiObject(name = "likedPhotoDto")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class LikedPhotoDto implements Serializable {

    @Min(1)
    @ApiObjectField(description = "photoDto")
    private long photoId;

    public LikedPhotoDto() {
    }

    public LikedPhotoDto(long photoId) {

        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return "LikedPhotoDto{" +
                "photoId=" + photoId +
                '}';
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }
}
