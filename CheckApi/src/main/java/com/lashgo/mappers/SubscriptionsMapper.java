package com.lashgo.mappers;

import com.lashgo.model.dto.SubscriptionDto;
import com.lashgo.utils.CheckUtils;
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
        if (CheckUtils.hasColumn(resultSet, "id")) {
            subscriptionDto.setId(resultSet.getInt("id"));
        }
        subscriptionDto.setUserId(resultSet.getInt("uid"));
        subscriptionDto.setUserLogin(resultSet.getString("login"));
        subscriptionDto.setUserAvatar(resultSet.getString("avatar"));
        subscriptionDto.setFio(resultSet.getString("fio"));
        if (CheckUtils.hasColumn(resultSet, "sub_count")) {
            subscriptionDto.setAmISubscribed(resultSet.getInt("sub_count") > 0);
        }
        return subscriptionDto;
    }
}
