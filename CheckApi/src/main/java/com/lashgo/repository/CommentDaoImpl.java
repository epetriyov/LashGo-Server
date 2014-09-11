package com.lashgo.repository;

import com.lashgo.mappers.CommentsMapper;
import com.lashgo.model.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eugene on 14.04.2014.
 */
@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CommentDto> getCommentsByCheck(int checkId) {
        return jdbcTemplate.query("" +
                "                  SELECT c.*,u.id as user_id, u.login, u.avatar FROM comments c " +
                "                  INNER JOIN users u ON (u.id = c.user_id)" +
                "                   RIGHT JOIN check_comments cc ON (cc.comment_id = c.id)" +
                "                   WHERE cc.check_id = ?", new CommentsMapper(), checkId);
    }

    @Override
    public CommentDto addCheckComment(int userId, int checkId, String commentText, Date commentDate) {
        Number commentId = addComment(userId, commentText, commentDate);
        jdbcTemplate.update("INSERT INTO check_comments (comment_id, check_id) " +
                "            VALUES (?,?)", commentId.longValue(), checkId);
        return getCommentById(commentId);
    }

    private CommentDto getCommentById(Number commentId) {
        return jdbcTemplate.queryForObject("" +
                "                  SELECT c.*,u.id as user_id, u.login, u.avatar FROM comments c " +
                "                  INNER JOIN users u ON (u.id = c.user_id)" +
                "                   WHERE c.id = ?", new CommentsMapper(), commentId);
    }

    @Override
    public List<CommentDto> getCommentsByPhoto(long photoId) {
        return jdbcTemplate.query("" +
                "                  SELECT c.*,u.id as user_id, u.login, u.avatar FROM comments c" +
                "                  INNER JOIN users u ON (u.id = c.user_id)" +
                "                   RIGHT JOIN photo_comments pc ON (pc.comment_id = c.id)" +
                "                   WHERE pc.photo_id = ?", new CommentsMapper(), photoId);
    }

    private Number addComment(int userId, String commentText, Date commentDate) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map<String, Object> params = new HashMap<>();
        params.put("content", commentText);
        params.put("user_id", userId);
        params.put("create_date", commentDate);
        return simpleJdbcInsert.withTableName("comments").usingGeneratedKeyColumns("id").usingColumns("content", "user_id", "create_date").executeAndReturnKey(params);
    }

    @Override
    public CommentDto addPhotoComment(int userId, long photoId, String commentText, Date commentDate) {
        Number commentId = addComment(userId, commentText, commentDate);
        jdbcTemplate.update("INSERT INTO photo_comments (comment_id, photo_id) " +
                "            VALUES (?,?)", commentId.longValue(), photoId);
        return getCommentById(commentId);
    }

    @Override
    public void deleteComment(long commentId) {
        jdbcTemplate.update("DELETE FROM comments WHERE id = ?", commentId);
    }
}
