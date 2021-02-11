package com.svitix.hw2020.orm.core.service;

import com.svitix.hw2020.orm.core.dao.ClientDao;
import com.svitix.hw2020.orm.core.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final ClientDao clientDao;

    public DbServiceClientImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public long saveClient(Client client) {
        try (var sessionManager = clientDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var clientId = clientDao.insert(client);
                sessionManager.commitSession();

                logger.info("created client: {}", clientId);
                return clientId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Client> getClient(long id) {
        try (var sessionManager = clientDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Client> clientOptional = clientDao.findById(id);

                logger.info("client: {}", clientOptional.orElse(null));
                return clientOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
