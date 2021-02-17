package com.svitix.hw2020.orm.jdbc.dao;

import com.svitix.hw2020.orm.core.dao.AccountDao;
import com.svitix.hw2020.orm.core.model.Account;
import com.svitix.hw2020.orm.core.sessionmanager.SessionManager;
import com.svitix.hw2020.orm.jdbc.exception.DaoException;
import com.svitix.hw2020.orm.jdbc.mapper.JdbcMapper;

import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final JdbcMapper<Account> jdbcMapper;
    private final SessionManager sessionManager;

    public AccountDaoImpl(JdbcMapper<Account> jdbcMapper, SessionManager sessionManager) {
        this.jdbcMapper = jdbcMapper;
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<Account> findById(String id) {
        return Optional.ofNullable(jdbcMapper.findById(id, Account.class));
    }

    @Override
    public String insert(Account account) {
        try {
            jdbcMapper.insert(account);
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        }
        return account.getNo();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
