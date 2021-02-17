package com.svitix.hw2020.orm.core.dao;


import com.svitix.hw2020.orm.core.model.Client;
import com.svitix.hw2020.orm.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface ClientDao {
    Optional<Client> findById(long id);
    //List<Client> findAll();

    long insert(Client client);

    //void update(Client client);
    //long insertOrUpdate(Client client);

    SessionManager getSessionManager();
}
