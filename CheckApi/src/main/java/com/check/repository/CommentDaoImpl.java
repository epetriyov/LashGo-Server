package main.java.com.check.repository;

import com.check.model.dto.CommentDto;
import main.java.com.check.domain.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Comments> getCommentsByCheck(long checkId) {
        return jdbcTemplate.queryForList("" +
                "                  SELECT c.* FROM comments c" +
                "                   RIGHT JOIN check_comments cc ON (cc.comment_id = c.id)" +
                "                   WHERE cc.check_id = ?", Comments.class, checkId);
    }

    @Override
    public void addCheckComment(final long checkId, final CommentDto checkCommentDto) {
        Number commentId = addComment(checkCommentDto);
        jdbcTemplate.update("INSERT INTO check_comments (comment_id, check_id) " +
                "            VALUES (?,?)", commentId, checkId);
    }

    @Override
    public List<Comments> getCommentsByPhoto(long photoId) {
        return jdbcTemplate.queryForList("" +
                "                  SELECT c.* FROM comments c" +
                "                   RIGHT JOIN photo_comments pc ON (pc.comment_id = c.id)" +
                "                   WHERE pc.photo_id = ?", Comments.class, photoId);
    }

    private Number addComment(final CommentDto commentDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("INSERT INTO comments (content, create_date, user_id) VALUES (?,?,?)");
                        ps.setString(1, commentDto.getContent());
                        ps.setDate(2, new Date(commentDto.getCreateDate().getTime()));
                        ps.setLong(3, commentDto.getUser().getId());
                        return ps;
                    }
                },
                keyHolder
        );
        return keyHolder.getKey();
    }

    @Override
    public void addPhotoComment(final long photoId, final CommentDto photoCommentDto) {
        Number commentId = addComment(photoCommentDto);
        jdbcTemplate.update("INSERT INTO photo_comments (comment_id, photo_id) " +
                "            VALUES (?,?)", commentId, photoId);
    }

    @Override
    public void deleteComment(long commentId) {
        jdbcTemplate.update("DELETE FROM comments WHERE id = ?", commentId);
    }
}
