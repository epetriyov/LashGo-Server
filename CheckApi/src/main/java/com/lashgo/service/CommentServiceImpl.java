package com.lashgo.service;

import com.lashgo.domain.Comments;
import com.lashgo.domain.Users;
import com.lashgo.model.dto.CommentDto;
import com.lashgo.model.dto.UserDto;
import com.lashgo.repository.CommentDao;
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

    public List<CommentDto> getCheckComments(long checkId) {
        return convertCommentsDto(commentDao.getCommentsByCheck(checkId));
    }

    private List<CommentDto> convertCommentsDto(List<Comments> commentsList) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(commentsList)) {
            for (Comments comments : commentsList) {
                Users users = comments.getUser();
                UserDto userDto = new UserDto(users.getId(), users.getLogin(), users.getName(), users.getSurname(), users.getAbout(), users.getCity(), users.getBirthDate(), users.getAvatar(), users.getEmail());
                commentDtoList.add(new CommentDto(comments.getId(), comments.getContent(), comments.getCreateDate(), userDto));
            }
        }
        return commentDtoList;
    }

    @Override
    public List<CommentDto> getPhotoComments(long photoId) {
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
