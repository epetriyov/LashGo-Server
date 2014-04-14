package main.java.com.check.repository;

import com.check.model.dto.CommentDto;
import main.java.com.check.domain.Comments;

import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
public interface CommentDao {

    List<Comments> getCommentsByCheck(long checkId);

    void addCheckComment(long checkId, CommentDto checkCommentDto);

    void deleteComment(long commentId);

    List<Comments> getCommentsByPhoto(long photoId);

    void addPhotoComment(long photoId, CommentDto photoCommentDto);
}
