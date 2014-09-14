package com.lashgo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by Eugene on 14.09.2014.
 */
@ApiObject(name = "check counters", description = "counters for check")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CheckCounters implements Serializable {
    @Min(0)
    @ApiObjectField(description = "count of shares")
    private int sharesCount;
    @Min(0)
    @ApiObjectField(description = "count of likes")
    private int likesCount;
    @Min(0)
    @ApiObjectField(description = "count of comments")
    private int commentsCount;
    @Min(0)
    @ApiObjectField(description = "count of players")
    private int playersCount;

    public CheckCounters() {

    }

    public int getSharesCount() {
        return sharesCount;
    }

    public void setSharesCount(int sharesCount) {
        this.sharesCount = sharesCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }
}
