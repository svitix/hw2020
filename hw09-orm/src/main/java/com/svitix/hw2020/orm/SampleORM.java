package com.svitix.hw2020.orm;

import com.svitix.hw2020.orm.core.dao.AccountDao;
import com.svitix.hw2020.orm.core.dao.ClientDao;
import com.svitix.hw2020.orm.core.model.Account;
import com.svitix.hw2020.orm.core.model.Client;
import com.svitix.hw2020.orm.core.service.DbServiceAccountImpl;
import com.svitix.hw2020.orm.core.service.DbServiceClientImpl;
import com.svitix.hw2020.orm.datasource.MyPGDataSource;
import com.svitix.hw2020.orm.jdbc.DbExecutorImpl;
import com.svitix.hw2020.orm.jdbc.dao.AccountDaoImpl;
import com.svitix.hw2020.orm.jdbc.dao.ClientDaoImpl;
import com.svitix.hw2020.orm.jdbc.mapper.EntityClassMetaDataImpl;
import com.svitix.hw2020.orm.jdbc.mapper.JdbcMapper;
import com.svitix.hw2020.orm.jdbc.mapper.JdbcMapperImpl;
import com.svitix.hw2020.orm.jdbc.sessionmanager.SessionManagerJdbc;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SampleORM {
    private static final Logger logger = LoggerFactory.getLogger(SampleORM.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new MyPGDataSource();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);

// Работа с пользователем
        DbExecutorImpl<Client> dbExecutor = new DbExecutorImpl<>();
        JdbcMapper<Client> jdbcMapperClient = new JdbcMapperImpl<>(dbExecutor, sessionManager, new EntityClassMetaDataImpl<>(Client.class));
        ClientDao clientDao = new ClientDaoImpl(jdbcMapperClient, sessionManager);


// Код дальше должен остаться, т.е. clientDao должен использоваться
        var dbServiceClient = new DbServiceClientImpl(clientDao);
        var id = dbServiceClient.saveClient(new Client(0, "dbServiceClient"));
        Optional<Client> clientOptional = dbServiceClient.getClient(id);

        clientOptional.ifPresentOrElse(
                client -> logger.info("created client, name:{}", client.getName()),
                () -> logger.info("client was not created")
        );


// Работа со счетом
        DbExecutorImpl<Account> dbExecutorAccount = new DbExecutorImpl<>();
        JdbcMapper<Account> jdbcMapperAccount = new JdbcMapperImpl<Account>(dbExecutorAccount, sessionManager, new EntityClassMetaDataImpl<Account>(Account.class));
        AccountDao accountDao = new AccountDaoImpl(jdbcMapperAccount, sessionManager);


        var dbServiceAccount = new DbServiceAccountImpl(accountDao);
        var idaccount = dbServiceAccount.saveAccount(new Account("no1", "physic"));
        Optional<Account> accountOptional = dbServiceAccount.getAccount(idaccount);

        accountOptional.ifPresentOrElse(
                account -> logger.info("created account, name:{}", account.getType()),
                () -> logger.info("account was not created")
        );

    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");

        Map<String, String> props = new HashMap<>();

        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }
}