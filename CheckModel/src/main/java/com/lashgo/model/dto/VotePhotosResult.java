package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugene on 26.08.2014.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiObject(name = "votePhotosResult", description = "list of photos for vote")
public class VotePhotosResult implements Serializable {
    @ApiObjectField(description = "votePhotoList")
    private List<VotePhoto> votePhotoList;
    @Min(1)
    @ApiObjectField(description = "photosCount")
    private Integer photosCount;

    public VotePhotosResult() {
    }

    public VotePhotosResult(List<VotePhoto> votePhotoList, Integer photosCount) {
        this.votePhotoList = new ArrayList<>(votePhotoList);
        this.photosCount = photosCount;
    }

    public List<VotePhoto> getVotePhotoList() {
        return votePhotoList;
    }

    public void setVotePhotoList(List<VotePhoto> votePhotoList) {
        this.votePhotoList = votePhotoList;
    }

    public Integer getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(Integer photosCount) {
        this.photosCount = photosCount;
    }
}
