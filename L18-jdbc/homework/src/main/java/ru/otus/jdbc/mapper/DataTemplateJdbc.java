package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                return rs.next() ? createObject(rs) : null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var objList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            objList.add(createObject(rs));
                        }
                        return objList;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T obj) {
        try {
            List<Object> params = new ArrayList<>();
            for (Field f : entityClassMetaData.getFieldsWithoutId()) {
                f.setAccessible(true);
                params.add(f.get(obj)); //this part of reflection broke my brains
            }

            return dbExecutor.executeStatement(
                    connection, entitySQLMetaData.getInsertSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T obj) {
        try {
            List<Object> params = new ArrayList<>();
            for (Field f : entityClassMetaData.getAllFields()) {
                f.setAccessible(true);
                params.add(f.get(obj)); //this part of reflection broke my brains
            }
            dbExecutor.executeStatement(
                    connection, entitySQLMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    //may be better put in some util class in future
    private T createObject(ResultSet rs) {
        try {
            T obj = entityClassMetaData.getConstructor().newInstance();

            for (Field field : entityClassMetaData.getAllFields()) {
                field.setAccessible(true);

                Object value = rs.getObject(field.getName());
                field.set(obj, value);
            }
            return obj;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
