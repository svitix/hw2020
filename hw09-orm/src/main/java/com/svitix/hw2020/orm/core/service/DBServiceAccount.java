package com.svitix.hw2020.orm.core.service;


import com.svitix.hw2020.orm.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

    String saveAccount(Account account);

    Optional<Account> getAccount(String id);

    //List<Client> findAll();
}
