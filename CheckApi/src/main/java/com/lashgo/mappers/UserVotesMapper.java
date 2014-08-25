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
        votePhoto.setPhotoUrl(rs.getString("photo"));
        votePhoto.setUserAvatar(rs.getString("user_avatar"));
        votePhoto.setUserId(rs.getInt("user_id"));
        votePhoto.setUserLogin(rs.getString("user_login"));
        votePhoto.setPhotoId(rs.getLong("photo_id"));
        return votePhoto;
    }
}
