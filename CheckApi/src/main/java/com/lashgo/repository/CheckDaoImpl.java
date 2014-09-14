package com.lashgo.repository;

import com.lashgo.mappers.CheckCountersMapper;
import com.lashgo.mappers.CheckMapper;
import com.lashgo.model.dto.CheckCounters;
import com.lashgo.model.dto.CheckDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Eugene on 23.03.2014.
 */
@Repository
public class CheckDaoImpl implements CheckDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public CheckDto getNextCheck() {
        try {
            return jdbcTemplate.queryForObject("SELECT c.* FROM checks c WHERE c.start_date + c.duration * INTERVAL '1 hour' > current_date ORDER BY c.start_date ASC LIMIT 1", new CheckMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<CheckDto> getAllChecks(int userId) {
        return jdbcTemplate.query("SELECT " +
                "                         c.id as check_id, c.name as check_name," +
                "                         c.description as check_description," +
                "                         c.start_date as check_start_date, " +
                "                         c.duration as check_duration, " +
                "                         c.task_photo as check_task_photo," +
                "                         c.vote_duration as check_vote_duration," +
                "                         p2.picture as user_photo," +
                "                         ph.picture as winner_photo, w.id as winner_id, u.*" +
                "                    FROM checks c " +
                "                    LEFT JOIN photos p2 " +
                "                      ON (p2.check_id = c.id AND p2.user_id = ?)" +
                "                    LEFT JOIN check_winners w" +
                "                      ON (w.check_id = c.id) " +
                "                   LEFT JOIN users u" +
                "                      ON (u.id = w.winner_id) " +
                "                   LEFT JOIN photos ph" +
                "                      ON (ph.user_id = u.id AND ph.check_id = c.id) " +
                "                   WHERE c.start_date <= current_timestamp" +
                "                   GROUP BY c.id,ph.picture,w.id,u.id, p2.picture " +
                "                   ORDER BY c.start_date DESC", new CheckMapper(), userId);
    }

    @Override
    public int getActiveChecksCount() {
        try {
            return jdbcTemplate.queryForObject("SELECT count(c.id) FROM checks c WHERE c.start_date <= current_timestamp " +
                    "                              AND c.start_date + c.duration * INTERVAL '1 hour' > current_timestamp", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public void addWinners() {
        jdbcTemplate.update(
                "INSERT INTO check_winners (check_id,winner_id) " +
                        "(SELECT c.id,p.user_id FROM checks c " +
                        "LEFT JOIN photos p ON (p.check_id = c.id)" +
                        "INNER JOIN (SELECT uv.user_id AS user_id, MAX(uv.votes_count)" +
                        "FROM (SELECT user_id, COUNT(id) AS votes_count FROM user_votes GROUP BY user_id) uv GROUP BY uv.user_id) res ON (res.user_id = p.user_id)" +
                        " WHERE c.start_date + c.duration * INTERVAL '1 hour'  + c.vote_duration * INTERVAL '1 hour'> current_date" +
                        " AND c.start_date + c.duration * INTERVAL '1 hour'  + c.vote_duration * INTERVAL '1 hour' < current_date + INTERVAL '1 hour'" +
                        " GROUP BY c.id,p.user_id)"
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
        return jdbcTemplate.queryForObject("SELECT " +
                "                         c.id as check_id, c.name as check_name," +
                "                         c.description as check_description," +
                "                         c.start_date as check_start_date, " +
                "                         c.duration as check_duration, " +
                "                         c.task_photo as check_task_photo," +
                "                         c.vote_duration as check_vote_duration," +
                "                         p2.picture as user_photo," +
                "                         ph.picture as winner_photo, w.id as winner_id, u.*" +
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
                "                   GROUP BY c.id,ph.picture,w.id,u.id, p2.picture ", new CheckMapper(), userId, checkId);
    }
}
