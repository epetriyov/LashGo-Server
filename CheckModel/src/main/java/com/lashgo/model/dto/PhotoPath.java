package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Eugene on 06.01.2015.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "photoPath")
public class PhotoPath implements Serializable {

    @Size(min = 1)
    @ApiObjectField(description = "name of photo")
    private String photoName;

    public PhotoPath() {
    }

    public PhotoPath(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoName() {

        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
