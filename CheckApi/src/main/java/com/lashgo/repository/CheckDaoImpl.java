package com.lashgo.repository;

import com.lashgo.domain.Check;
import com.lashgo.mappers.CheckCountersMapper;
import com.lashgo.mappers.CheckDtoMapper;
import com.lashgo.mappers.CheckMapper;
import com.lashgo.mappers.GcmCheckMapper;
import com.lashgo.model.CheckType;
import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.CheckDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eugene on 23.03.2014.
 */
@Repository
public class CheckDaoImpl implements CheckDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CheckDto> getAllStartedChecks(int userId, String searchText, String checkType) {
        List<Object> params = new ArrayList<>();
        params.add(userId);
        if (!StringUtils.isEmpty(searchText)) {
            params.add("%" + searchText.toLowerCase() + "%");
            params.add("%" + searchText.toLowerCase() + "%");
        }
        if (!StringUtils.isEmpty(checkType)) {
            params.add(checkType);
        }
        String sql =
                "SELECT " +
                        "                         c.id as check_id, c.name as check_name," +
                        "                         c.description as check_description," +
                        "                         c.start_date as check_start_date, " +
                        "                         c.duration as check_duration, " +
                        "                         c.task_photo as check_task_photo," +
                        "                         c.vote_duration as check_vote_duration," +
                        "                         c.check_type as check_type," +
                        "                         p2.picture as user_photo," +
                        "                         ph.id as winner_photo_id," +
                        "                         ph.picture as winner_photo, " +
                        "                         w.winner_id as winner_id, " +
                        "                         u.*," +
                        "                         (SELECT COUNT (p.id) FROM photos p WHERE p.check_id = c.id) AS players_count," +
                        "                         (SELECT COUNT (uv.id) FROM user_votes uv WHERE uv.photo_id = ph.id) AS likes_count," +
                        "                         (SELECT COUNT (com.id) FROM photo_comments com WHERE com.photo_id = ph.id) AS comments_count" +
                        "                    FROM checks c " +
                        "                    LEFT JOIN photos p2 " +
                        "                      ON (p2.check_id = c.id AND p2.user_id = ?)" +
                        "                    LEFT JOIN check_winners w" +
                        "                      ON (w.check_id = c.id) " +
                        "                   LEFT JOIN users u" +
                        "                      ON (u.id = w.winner_id) " +
                        "                   LEFT JOIN photos ph" +
                        "                      ON (ph.user_id = u.id AND ph.check_id = c.id) " +
                        "                   WHERE c.start_date <= clock_timestamp()" +
                        (StringUtils.isEmpty(searchText) ? "" : " AND (LOWER(c.name) LIKE ? OR LOWER(c.description) LIKE ?)") +
                        (StringUtils.isEmpty(checkType) ? "" : " AND (c.check_type = ?)") +
                        "                   GROUP BY c.id,ph.id,ph.picture,w.winner_id,u.id, p2.picture " +
                        "                   ORDER BY c.start_date DESC";
        return jdbcTemplate.query(sql, params.toArray(new Object[params.size()]), new CheckMapper());
    }

    @Override
    public int getActiveChecksCount(CheckType checkType) {
        List<String> params = new ArrayList<>();
        if (checkType != null) {
            params.add(checkType.name());
        }
        try {
            return jdbcTemplate.queryForObject("SELECT count(c.id) FROM checks c WHERE c.start_date <= clock_timestamp() " +
                    "                              AND c.start_date + c.duration * INTERVAL '1 hour' > clock_timestamp()" +
                    (checkType != null ?
                            "                              AND c.check_type = ?"
                            : ""), Integer.class, params.toArray(new String[params.size()]));
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public List<Integer> getFinishedChecks() {
        return jdbcTemplate.queryForList("SELECT id FROM checks c WHERE (c.start_date + INTERVAL '1 hour' * c.duration + INTERVAL '1 hour' * c.vote_duration) <= clock_timestamp() " +
                        "AND (c.start_date + INTERVAL '1 hour' * c.duration + INTERVAL '1 hour' * c.vote_duration  + INTERVAL '1 minute') > clock_timestamp() ",
                Integer.class
        );
    }

    public CheckCounters getCheckCounters(int checkId) {
        return jdbcTemplate.queryForObject(
                "SELECT (SELECT COUNT (lc.id) FROM user_check_likes lc WHERE lc.check_id = ?) AS likes_count," +
                        "(SELECT COUNT (p.id) FROM photos p WHERE p.check_id = ?) AS players_count," +
                        "(SELECT COUNT (com.id) FROM check_comments com WHERE com.check_id = ?) AS comments_count;"
                , new CheckCountersMapper(), checkId, checkId, checkId);
    }

    @Override
    public CheckDto getCheckById(int userId, int checkId) {
        try {
            return jdbcTemplate.queryForObject("SELECT " +
                    "                         c.id as check_id, c.name as check_name," +
                    "                         c.description as check_description," +
                    "                         c.start_date as check_start_date, " +
                    "                         c.duration as check_duration, " +
                    "                         c.task_photo as check_task_photo," +
                    "                         c.vote_duration as check_vote_duration," +
                    "                         c.check_type as check_type," +
                    "                         p2.picture as user_photo," +
                    "                         ph.id as winner_photo_id," +
                    "                         ph.picture as winner_photo, w.winner_id as winner_id, u.*," +
                    "                         (SELECT COUNT (uv.id) FROM user_votes uv WHERE uv.photo_id = ph.id) AS likes_count," +
                    "                         (SELECT COUNT (p.id) FROM photos p WHERE p.check_id = c.id) AS players_count," +
                    "                         (SELECT COUNT (com.id) FROM photo_comments com WHERE com.photo_id = ph.id) AS comments_count" +
                    "                    FROM checks c " +
                    "                    LEFT JOIN photos p2 " +
                    "                      ON (p2.check_id = c.id AND p2.user_id = ?)" +
                    "                    LEFT JOIN check_winners w" +
                    "                      ON (w.check_id = c.id) " +
                    "                   LEFT JOIN users u" +
                    "                      ON (u.id = w.winner_id) " +
                    "                   LEFT JOIN photos ph" +
                    "                      ON (ph.user_id = u.id AND ph.check_id = c.id) " +
                    "                   WHERE c.id = ?" +
                    "                   GROUP BY c.id,ph.id,ph.picture,w.winner_id,u.id, p2.picture ", new CheckMapper(), userId, checkId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Check getCurrentCheck() {
        try {
            return jdbcTemplate.queryForObject("SELECT c.* FROM checks c WHERE c.start_date <= clock_timestamp() " +
                    "AND (c.start_date + INTERVAL '1 minute') >= clock_timestamp() " +
                    "ORDER BY c.start_date ASC LIMIT 1", new GcmCheckMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean isCheckActive(int checkId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(c.id) FROM checks c where c.id = ? " +
                "AND (c.start_date + INTERVAL '1 hour' * c.duration) > clock_timestamp() " +
                "AND c.start_date <= clock_timestamp()", Integer.class, checkId) > 0;
    }

    @Override
    public boolean isVoteGoing(int checkId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(c.id) FROM checks c where c.id = ? " +
                "AND (c.start_date + INTERVAL '1 hour' * (c.vote_duration + c.duration)) > clock_timestamp() " +
                "AND (c.start_date + INTERVAL '1 hour' * c.duration  <= clock_timestamp())", Integer.class, checkId) > 0;
    }

    @Override
    public Check getVoteCheck() {
        try {
            return jdbcTemplate.queryForObject("SELECT c.* FROM checks c WHERE (c.start_date + INTERVAL '1 hour' * c.duration) <= clock_timestamp() " +
                    "AND (c.start_date + INTERVAL '1 hour' * c.duration  + INTERVAL '1 minute') > clock_timestamp() " +
                    "ORDER BY c.start_date ASC LIMIT 1", new GcmCheckMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Check getFinishedCheck() {
        try {
            return jdbcTemplate.queryForObject("SELECT c.* FROM checks c " +
                    "WHERE (c.start_date + INTERVAL '1 hour' * c.duration + INTERVAL '1 hour' * c.vote_duration) <= clock_timestamp() " +
                    "AND (c.start_date + INTERVAL '1 hour' * c.duration + INTERVAL '1 hour' * c.vote_duration  + INTERVAL '1 minute') > clock_timestamp() " +
                    "ORDER BY c.start_date ASC LIMIT 1", new GcmCheckMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public CheckDto getCheckById(int checkId) {
        try {
            return jdbcTemplate.queryForObject("SELECT c.* FROM checks c WHERE  c.id = ?", new CheckDtoMapper(), checkId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Number addNewCheck(CheckDto checkDto) {
        if (checkDto == null) {
            throw new IllegalArgumentException("can't create null check");
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map<String, Object> params = new HashMap<>();
        params.put("name", checkDto.getName());
        params.put("description", checkDto.getDescription());
        params.put("start_date", new Timestamp(checkDto.getStartDate().getTime()));
        params.put("duration", checkDto.getDuration());
        params.put("task_photo", checkDto.getTaskPhotoUrl());
        params.put("vote_duration", checkDto.getVoteDuration());
        params.put("check_type", checkDto.getCheckType());
        return simpleJdbcInsert.withTableName("checks").usingGeneratedKeyColumns("id").usingColumns("name", "description", "start_date", "duration", "vote_duration", "task_photo", "check_type").executeAndReturnKey(params);
    }

    @Override
    public boolean doesCheckNameExists(String checkName) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM checks WHERE name = ?", Integer.class, checkName) > 0;
    }
}
