package com.check.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 06.05.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ContentDto implements Serializable {
    private Date createDate;
    private String value;

    public ContentDto() {

    }

    public ContentDto(Date createDate, String value) {
        this.createDate = createDate;
        this.value = value;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
