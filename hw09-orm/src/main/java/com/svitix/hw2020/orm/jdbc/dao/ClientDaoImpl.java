package com.svitix.hw2020.orm.jdbc.dao;

import com.svitix.hw2020.orm.core.dao.ClientDao;
import com.svitix.hw2020.orm.core.model.Client;
import com.svitix.hw2020.orm.core.sessionmanager.SessionManager;
import com.svitix.hw2020.orm.jdbc.exception.DaoException;
import com.svitix.hw2020.orm.jdbc.mapper.JdbcMapper;

import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    private final JdbcMapper<Client> jdbcMapper;
    private final SessionManager sessionManager;

    public ClientDaoImpl(JdbcMapper<Client> jdbcMapper, SessionManager sessionManager) {
        this.jdbcMapper = jdbcMapper;
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<Client> findById(long id) {

        return Optional.ofNullable(jdbcMapper.findById(id, Client.class));
    }

    @Override
    public long insert(Client client) {
        try {
            jdbcMapper.insert(client);
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        }
        return client.getId();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
