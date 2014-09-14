package com.lashgo.mappers;

import com.lashgo.model.dto.CheckCounters;
import com.lashgo.utils.CheckUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 14.09.2014.
 */
public class CheckCountersMapper implements org.springframework.jdbc.core.RowMapper<com.lashgo.model.dto.CheckCounters> {
    @Override
    public CheckCounters mapRow(ResultSet rs, int rowNum) throws SQLException {
        CheckCounters checkCounters = new CheckCounters();
        if (CheckUtils.hasColumn(rs, "players_count")) {
            checkCounters.setPlayersCount(rs.getInt("players_count"));
        }
        checkCounters.setLikesCount(rs.getInt("likes_count"));
        checkCounters.setCommentsCount(rs.getInt("comments_count"));
        return checkCounters;
    }
}
