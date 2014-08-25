package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by Eugene on 25.08.2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiObject(name = "voteAction", description = "vote data")
public class VoteAction implements Serializable {

    @ApiObjectField(description = "photoIds array")
    private long[] photoIds;

    @Min(1)
    @ApiObjectField(description = "votedPhotoId")
    private long votedPhotoId;

    public VoteAction() {
    }

    public VoteAction(long[] photoIds, long votedPhotoId) {

        this.photoIds = photoIds;
        this.votedPhotoId = votedPhotoId;
    }

    public long[] getPhotoIds() {

        return photoIds;
    }

    public void setPhotoIds(long[] photoIds) {
        this.photoIds = photoIds;
    }

    public long getVotedPhotoId() {
        return votedPhotoId;
    }

    public void setVotedPhotoId(long votedPhotoId) {
        this.votedPhotoId = votedPhotoId;
    }
}
