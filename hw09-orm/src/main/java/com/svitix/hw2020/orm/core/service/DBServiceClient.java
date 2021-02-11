package com.svitix.hw2020.orm.core.service;


import com.svitix.hw2020.orm.core.model.Client;

import java.util.Optional;

public interface DBServiceClient {

    long saveClient(Client client);

    Optional<Client> getClient(long id);

    //List<Client> findAll();
}
