package com.lashgo.repository;

import com.lashgo.model.dto.CommentDto;
import com.lashgo.domain.Comments;
import com.lashgo.mappers.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
    public List<CommentDto> getCommentsByCheck(long checkId) {
        return jdbcTemplate.query("" +
                "                  SELECT c.*,u.id as user_id, u.login, u.avatar FROM comments c " +
                "                  INNER JOIN users u ON (u.id = c.user_id)" +
                "                   RIGHT JOIN check_comments cc ON (cc.comment_id = c.id)" +
                "                   WHERE cc.check_id = ?", new CommentsMapper(), checkId);
    }

    @Override
    public void addCheckComment(final long checkId, final CommentDto checkCommentDto) {
        Number commentId = addComment(checkCommentDto);
        if (commentId != null) {
            jdbcTemplate.update("INSERT INTO check_comments (comment_id, check_id) " +
                    "            VALUES (?,?)", commentId, checkId);
        }
    }

    @Override
    public List<CommentDto> getCommentsByPhoto(long photoId) {
        return jdbcTemplate.query("" +
                "                  SELECT c.* FROM comments c" +
                "                   RIGHT JOIN photo_comments pc ON (pc.comment_id = c.id)" +
                "                   WHERE pc.photo_id = ?", new CommentsMapper(), photoId);
    }

    private Number addComment(final CommentDto commentDto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map<String, Object> params = new HashMap<>();
        params.put("content", commentDto.getContent());
        params.put("user_id", commentDto.getUser().getId());
        return simpleJdbcInsert.withTableName("comments").usingGeneratedKeyColumns("id").usingColumns("content", "user_id").executeAndReturnKey(params);
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
