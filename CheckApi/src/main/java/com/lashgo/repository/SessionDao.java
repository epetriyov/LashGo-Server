package com.lashgo.repository;

import com.lashgo.domain.Sessions;

/**
 * Created by Eugene on 14.02.14.
 */
public interface SessionDao {

    Sessions createSession(int userId);

    Sessions getSessionByUser(int userId);

    Sessions getSessionById(String sessionId);
}
