package com.lashgo.mappers;

import com.lashgo.model.dto.SubscriptionDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eugene on 05.05.2014.
 */
public class SubscriptionsMapper implements RowMapper<SubscriptionDto> {

    @Override
    public SubscriptionDto mapRow(ResultSet resultSet, int i) throws SQLException {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setId(resultSet.getInt("id"));
        subscriptionDto.setUserId(resultSet.getInt("user_id"));
        subscriptionDto.setUserLogin(resultSet.getString("login"));
        subscriptionDto.setUserAvatar(resultSet.getString("avatar"));
        return subscriptionDto;
    }
}
