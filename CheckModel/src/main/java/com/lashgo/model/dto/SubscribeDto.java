package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by Eugene on 19.10.2014.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ApiObject(name = "subscribe", description = "subscribe object")
public class SubscribeDto implements Serializable{
    @Min(1)
    @ApiObjectField(description = "userId")
    private int userId;

    public SubscribeDto() {
    }

    public SubscribeDto(int userId) {
        this.userId = userId;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
