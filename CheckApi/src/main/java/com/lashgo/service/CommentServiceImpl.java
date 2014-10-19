package com.lashgo.service;

import com.lashgo.domain.Users;
import com.lashgo.model.dto.CommentDto;
import com.lashgo.repository.CommentDao;
import com.lashgo.repository.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 14.04.2014.
 */
@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EventDao eventDao;

    public List<CommentDto> getCheckComments(int checkId) {
        return commentDao.getCommentsByCheck(checkId);
    }

    @Override
    public List<CommentDto> getPhotoComments(long photoId) {
        return commentDao.getCommentsByPhoto(photoId);
    }

    @Transactional
    @Override
    public CommentDto addCheckComment(String sessionId, int checkId, String commentText) {
        Users users = userService.getUserBySession(sessionId);
        return commentDao.addCheckComment(users.getId(), checkId, commentText, new Date());
    }

    @Transactional
    @Override
    public CommentDto addPhotoComment(String sessionId, long photoId, String commentText) {
        Users users = userService.getUserBySession(sessionId);
        CommentDto commentDto = commentDao.addPhotoComment(users.getId(), photoId, commentText, new Date());
        eventDao.addCommentEvent(users.getId(), photoId);
        return commentDto;
    }

    @Transactional
    @Override
    public void deleteComment(long commentId) {
        commentDao.deleteComment(commentId);
    }
}
