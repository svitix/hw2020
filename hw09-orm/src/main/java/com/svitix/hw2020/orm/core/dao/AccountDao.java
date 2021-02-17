package com.svitix.hw2020.orm.core.dao;


import com.svitix.hw2020.orm.core.model.Account;
import com.svitix.hw2020.orm.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findById(String id);
    //List<Account> findAll();

    String insert(Account account);

    //void update(Account account);
    //long insertOrUpdate(Account account);

    SessionManager getSessionManager();
}
