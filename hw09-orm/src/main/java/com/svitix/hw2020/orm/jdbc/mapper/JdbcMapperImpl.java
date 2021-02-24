package com.svitix.hw2020.orm.jdbc.mapper;

import com.svitix.hw2020.orm.jdbc.DbExecutorImpl;
import com.svitix.hw2020.orm.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private final DbExecutorImpl<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;
    private final EntityClassMetaData<T> entityClassMetaData;
    private final EntitySQLMetaData entitySQlMetaData;

    public JdbcMapperImpl(DbExecutorImpl<T> dbExecutor, SessionManagerJdbc sessionManager, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
        this.entityClassMetaData = entityClassMetaData;
        this.entitySQlMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
    }

    @Override
    public void insert(T object) {
        try {
            dbExecutor.executeInsert(
                    sessionManager.getCurrentSession().getConnection(),
                    entitySQlMetaData.getInsertSql(),
                    getValuesOfFields(object, entityClassMetaData.getAllFields())
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(T object) {
        try {
            dbExecutor.executeInsert(
                    sessionManager.getCurrentSession().getConnection(),
                    entitySQlMetaData.getUpdateSql(),
                    getValuesOfFields(object, entityClassMetaData.getAllFields())
            );

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insertOrUpdate(T object) {

    }

    @Override
    public T findById(Object id, Class<T> clazz) {
        Optional<T> resultOneObject = Optional.empty();
        try {
            resultOneObject = dbExecutor.executeSelect(
                    sessionManager.getCurrentSession().getConnection(),
                    entitySQlMetaData.getSelectByIdSql(),
                    id,
                    this::apply);
        } catch (Exception e) {
            System.out.println("Some trouble find by");
        }
        return resultOneObject.orElse(null);
    }

    private T apply(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                Constructor<T> constructor = entityClassMetaData.getConstructor();
                T obj = constructor.newInstance();
                for (Field f : entityClassMetaData.getAllFields()) {
                    String fieldName = f.getName();
                    f.setAccessible(true);
                    Object value = resultSet.getObject(fieldName);
                    f.set(obj, value);

                }
                return obj;
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.out.println("Some trouble apply");
        }
        return null;
    }

    public SessionManagerJdbc getSessionManager() {
        return this.sessionManager;
    }

    private List<Object> getValuesOfFields(T objectData, List<Field> fields) {
        List<Object> values = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                values.add(field.get(objectData));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }
}
