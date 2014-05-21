package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eugene on 06.05.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "content", description = "some info (news, events e t.c.)")
public class ContentDto implements Serializable {
    @NotNull
    @ApiObjectField(description = "create date")
    private Date createDate;
    @NotEmpty
    @ApiObjectField(description = "content text")
    private String text;

    public ContentDto() {

    }

    public ContentDto(Date createDate, String text) {
        this.createDate = createDate;
        this.text = text;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
