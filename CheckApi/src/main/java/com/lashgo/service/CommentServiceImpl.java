package com.lashgo.service;

import com.lashgo.model.dto.CommentDto;
import com.lashgo.repository.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return commentDao.getCommentsByCheck(checkId);
    }

    @Override
    public List<CommentDto> getPhotoComments(long photoId) {
        return commentDao.getCommentsByPhoto(photoId);
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
