package com.lashgo.mappers;

import com.lashgo.model.dto.VotePhoto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 25.08.2014.
 */
public class UserVotesMapper implements RowMapper<VotePhoto> {
    @Override
    public VotePhoto mapRow(ResultSet rs, int rowNum) throws SQLException {
        VotePhoto votePhoto = new VotePhoto();
        PhotosDtoMapper photosDtoMapper = new PhotosDtoMapper(PhotosDtoMapper.MapType.CHECK_JOIN);
        votePhoto.setPhotoDto(photosDtoMapper.mapRow(rs, rowNum));
        votePhoto.setShown(rs.getInt("user_shown_count") > 0);
        votePhoto.setVoted(rs.getInt("user_votes_count") > 0);
        return votePhoto;
    }
}
