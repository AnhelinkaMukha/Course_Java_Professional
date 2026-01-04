package ru.otus.jdbc.mapper;

import ru.otus.crm.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> fieldsWithoutId;
    private final List<Field> allFields;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.name = clazz.getSimpleName();

        try {
            this.constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        idField = Arrays.stream(getFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No @Id field in " + clazz.getName()));

        fieldsWithoutId = Arrays.stream(getFields())
                .filter(field -> !field.equals(this.getIdField()))
                .toList();

        allFields = List.of(getFields());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    private Field[] getFields(){
        return clazz.getDeclaredFields();
    }
}
