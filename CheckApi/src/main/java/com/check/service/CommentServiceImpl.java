package main.java.com.check.service;

import com.check.model.dto.*;
import main.java.com.check.domain.Comments;
import main.java.com.check.domain.Users;
import main.java.com.check.repository.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    public CommentsDto getCheckComments(long checkId) {
        return convertCommentsDto(commentDao.getCommentsByCheck(checkId));
    }

    private CommentsDto convertCommentsDto(List<Comments> commentsList) {
        CommentsDto checkCommentsDto = new CommentsDto();
        List<CommentDto> commentDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(commentsList)) {
            for (Comments comments : commentsList) {
                Users users = comments.getUser();
                UserDto userDto = new UserDto(users.getId(), users.getLogin(), users.getName(), users.getSurname(), users.getAbout(), users.getCity(), users.getBirthDate(), users.getAvatar());
                commentDtoList.add(new CommentDto(comments.getId(), comments.getContent(), comments.getCreateDate(), userDto));
            }
        }
        checkCommentsDto.setCommentDtoList(commentDtoList);
        return checkCommentsDto;
    }

    @Override
    public CommentsDto getPhotoComments(long photoId) {
        return convertCommentsDto(commentDao.getCommentsByPhoto(photoId));
    }

    @Transactional
    @Override
    public void addCheckComment(long checkId, CommentDto checkCommentDto) {
        commentDao.addCheckComment(checkId, checkCommentDto);
    }

    @Transactional
    @Override
    public void addPhotoComment(long photoId, CommentDto photoCommentDto) {
        commentDao.addPhotoComment(photoId, photoCommentDto);
    }

    @Transactional
    @Override
    public void deleteComment(long commentId) {
        commentDao.deleteComment(commentId);
    }
}
