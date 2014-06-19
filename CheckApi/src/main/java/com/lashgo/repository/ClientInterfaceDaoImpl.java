package com.lashgo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 29.04.2014.
 */
@Repository
public class ClientInterfaceDaoImpl implements ClientInterfaceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getIntefaceIdByCode(String clientInterfaceCode) {
        Integer interfaceId = jdbcTemplate.queryForObject("SELECT id FROM client_interfaces WHERE code = ?",Integer.class,clientInterfaceCode);
        return interfaceId;
    }
}
