package com.svitix.hw2020.orm.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> metaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> metaData) {
        this.metaData = metaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + metaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("SELECT * FROM %s WHERE %s = ?",
                metaData.getName(),
                metaData.getIdField().getName().toLowerCase()
        );
    }

    @Override
    public String getInsertSql() {
        StringBuilder insertsql = new StringBuilder();
        insertsql
                .append("INSERT INTO ")
                .append(metaData.getName())
                .append(" (")
                .append(arrayWithComma(metaData.getAllFields(), Field::getName))
                .append(") VALUES (")
                .append(arrayWithComma(metaData.getAllFields(), i -> " ? "))
                .append(")");

        return insertsql.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ")
                .append(metaData.getName())
                .append(" SET ")
                .append(arrayWithComma(metaData.getFieldsWithoutId(), i -> i.getName() + " = ?"))
                .append(" WHERE ")
                .append(metaData.getIdField().getName())
                .append(" = ?");
        return updateSql.toString();
    }

    private String arrayWithComma(List<Field> listFields, Function<? super Field, String> functionOutputString) {
        return listFields.stream()
                .map(functionOutputString)
                .collect(Collectors.joining(","));
    }
}
