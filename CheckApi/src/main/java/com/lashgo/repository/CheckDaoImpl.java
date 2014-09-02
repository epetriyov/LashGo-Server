package com.lashgo.repository;

import com.lashgo.mappers.CheckMapper;
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
        return jdbcTemplate.query("SELECT count(lc.id) as likes_count, " +
                "                         count(p.id) as players_count," +
                "                         count(com.id) as comments_count ," +
                "                         c.id as check_id, c.name as check_name," +
                "                         c.description as check_description," +
                "                         c.start_date as check_start_date, " +
                "                         c.duration as check_duration, " +
                "                         c.task_photo as check_task_photo," +
                "                         c.vote_duration as check_vote_duration," +
                "                         ph.picture as winner_photo, w.id as winner_id, u.*," +
                "                         count(ph2.id) as checks_count, " +
                "                         count(comm.id) as comments_count, " +
                "                         count(likes.id) as likes_count, " +
                "                         count(subs.id) as user_subscribes, " +
                "                         count(subs2.id) as user_subscribers " +
                "                    FROM checks c " +
                "                    LEFT JOIN photos p " +
                "                      ON (p.check_id = c.id)" +
                "                    LEFT JOIN check_comments com " +
                "                      ON (com.check_id = c.id)" +
                "                    LEFT JOIN user_check_likes lc" +
                "                      ON (lc.check_id = c.id)" +
                "                    LEFT JOIN check_winners w" +
                "                      ON (w.check_id = c.id) " +
                "                   INNER JOIN users u" +
                "                      ON (u.id = w.winner_id) " +
                "                   LEFT JOIN photos ph" +
                "                      ON (ph.user_id = u.id AND ph.check_id = c.id) " +
                "                   LEFT JOIN photos ph2" +
                "                      ON (ph2.user_id = u.id) " +
                "                   LEFT JOIN comments comm" +
                "                      ON (comm.user_id = u.id) " +
                "                   LEFT JOIN user_photo_likes likes" +
                "                      ON (likes.user_id = u.id) " +
                "                   LEFT JOIN subscriptions subs" +
                "                      ON (subs.user_id = u.id) " +
                "                   LEFT JOIN subscriptions subs2 " +
                "                      ON (subs2.checklist_id = u.id) " +
                "                   GROUP BY c.id,ph.picture,w.id,u.id " +
                "                   ORDER BY c.start_date DESC", new CheckMapper());
    }

    @Override
    public int getActiveChecksCount() {
        try {
            return jdbcTemplate.queryForObject("SELECT count(c.id) FROM checks c WHERE c.start_date + c.duration * INTERVAL '1 hour' > current_date", Integer.class);
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
}
