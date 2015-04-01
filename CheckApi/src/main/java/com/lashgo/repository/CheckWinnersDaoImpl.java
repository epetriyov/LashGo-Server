package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 19.10.2014.
 */
@Repository
public class CheckWinnersDaoImpl implements CheckWinnersDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addCheckWinner(int check) {
        jdbcTemplate.update(
                "           INSERT INTO check_winners (check_id,winner_id) " +
                        "  (SELECT p.check_id,p.user_id FROM photos p " +
                        "    LEFT JOIN " +
                        " (SELECT al1.photo_id as photo_id, MAX(al1.votes_count) as votes_count FROM" +
                        " (SELECT uvv.photo_id, COUNT(uvv.id) AS votes_count FROM user_votes uvv " +
                        " INNER JOIN photos pp ON (pp.id = uvv.photo_id AND pp.check_id = ?) GROUP BY photo_id) al1 " +
                        " GROUP BY al1.photo_id order by votes_count desc limit 1) al2 " +
                        " ON (al2.photo_id = p.id) WHERE p.check_id = ? " +
                        " GROUP BY p.check_id,p.user_id,p.make_date,al2.votes_count" +
                        " ORDER BY al2.votes_count ASC,p.make_date ASC  LIMIT 1) ", check, check
        );
    }

    @Override
    public int getCheckWinner(int check) {
        try {
            return jdbcTemplate.queryForObject("SELECT winner_id FROM check_winners WHERE check_id = ?", Integer.class, check);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
}
