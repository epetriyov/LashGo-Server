package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 22.02.2015.
 */
@Repository
public class ApnDaoImpl implements ApnDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addApnsToken(String apnsToken, int userId) {
        jdbcTemplate.update("INSERT INTO apns_registrations (token,user_id,register_date) VALUES (?,?,clock_timestamp())", apnsToken, userId);
    }

    @Override
    public boolean isApnsTokenExists(String apnsToken) {
        Integer registrationIdsCount = jdbcTemplate.queryForObject("SELECT COUNT(ar.token) FROM apns_registrations ar WHERE ar.token = ?",
                Integer.class, apnsToken);
        return registrationIdsCount > 0;
    }

    @Override
    public List<String> getAllApnsTokens() {
        return jdbcTemplate.queryForList("SELECT ar.token FROM apns_registrations ar", String.class);
    }

    @Override
    public Date getRegisterDateByToken(String apnsToken) {
        try {
            return jdbcTemplate.queryForObject("SELECT register_date FROM apns_registrations WHERE token = ?", Timestamp.class, apnsToken);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void removeInactiveTokens(List<String> tokens) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedParameterJdbcTemplate.update("DELETE FROM apns_registrations WHERE token IN (:ids)", Collections.singletonMap("ids", tokens));
    }

    @Override
    public void updateRegisterDate(String token) {
        jdbcTemplate.update("UPDATE apns_registrations SET register_date = clock_timestamp() WHERE token = ?", token);
    }
}
