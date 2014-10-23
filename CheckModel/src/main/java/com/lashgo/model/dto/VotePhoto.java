package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Eugene on 25.08.2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiObject(name = "votePhoto", description = "photo for vote")
public class VotePhoto implements Serializable {

    @NotNull
    @ApiObjectField(description = "photoDto")
    private PhotoDto photoDto;

    @ApiObjectField(description = "isShown")
    private boolean isShown;

    @ApiObjectField(description = "isVoted")
    private boolean isVoted;

    public VotePhoto() {
    }

    public PhotoDto getPhotoDto() {
        return photoDto;
    }

    public void setPhotoDto(PhotoDto photoDto) {
        this.photoDto = photoDto;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean isShown) {
        this.isShown = isShown;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean isVoted) {
        this.isVoted = isVoted;
    }
}
