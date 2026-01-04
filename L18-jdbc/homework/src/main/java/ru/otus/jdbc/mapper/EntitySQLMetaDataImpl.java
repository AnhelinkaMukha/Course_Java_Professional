package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;

import java.util.stream.Collectors;


public class EntitySQLMetaDataImpl implements EntitySQLMetaData{

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData){
        this.entityClassMetaData = entityClassMetaData;
    }
    @Override
    public String getSelectAllSql() {
        return "select * from " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from " + entityClassMetaData.getName() + " where " + entityClassMetaData.getIdField().getName() + "  = ?";
    }

    @Override
    public String getInsertSql() {
        //this logic could be common used in feature, so I suppose also good to move it out in future
        var fields = entityClassMetaData.getFieldsWithoutId();
        var columns = fields.stream().map(Field::getName).collect(Collectors.joining(", "));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append("?");
        }
        String params = sb.toString();

        return "insert into "+entityClassMetaData.getName()+"("+ columns +") values ("+params+")";
    }

    @Override
    public String getUpdateSql() {
        var setPart = entityClassMetaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));

        return "update "+ entityClassMetaData.getName() +" set "+setPart+" where "+entityClassMetaData.getIdField().getName()+" = ?";
    }
}
