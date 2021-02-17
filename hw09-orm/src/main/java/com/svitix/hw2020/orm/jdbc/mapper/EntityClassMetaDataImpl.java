package com.svitix.hw2020.orm.jdbc.mapper;

import com.svitix.hw2020.orm.jdbc.annotation.Id;
import com.svitix.hw2020.orm.jdbc.exception.ConstructorNotFoundException;
import com.svitix.hw2020.orm.jdbc.exception.IdNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> entityClass;
    private final List<Field> fieldsWithFirstId = new ArrayList<>();
    private Constructor<T> constructor;

    public EntityClassMetaDataImpl(final Class<T> clazz) {
        this.entityClass = clazz;
        initialize();
    }

    @Override
    public String getName() {
        return entityClass.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return fieldsWithFirstId.get(0);
    }

    @Override
    public List<Field> getAllFields() {
        return Collections.unmodifiableList(fieldsWithFirstId);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithFirstId.stream()
                .skip(1)
                .collect(Collectors.toUnmodifiableList());
    }

    @SuppressWarnings("unchecked")
    private void initialize() {
        this.constructor = (Constructor<T>) Arrays.stream(entityClass.getDeclaredConstructors())
                .filter(item -> item.getParameterCount() == 0)
                .findFirst()
                .orElseThrow(ConstructorNotFoundException::new);

        fieldsWithFirstId.add(
                Arrays.stream(entityClass.getDeclaredFields())
                        .filter(item -> item.isAnnotationPresent(Id.class))
                        .findFirst()
                        .orElseThrow(IdNotFoundException::new)
        );

        fieldsWithFirstId.addAll(
                Arrays.stream(entityClass.getDeclaredFields())
                        .filter(item -> !item.equals(fieldsWithFirstId.get(0)))
                        .collect(Collectors.toCollection(ArrayList::new))
        );
    }
}
