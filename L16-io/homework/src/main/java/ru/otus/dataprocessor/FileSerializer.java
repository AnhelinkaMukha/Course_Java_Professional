package ru.otus.dataprocessor;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;
    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        data.forEach(jsonObjectBuilder::add);

        try (Writer writer = Files.newBufferedWriter(Path.of(fileName))) {
            Json.createWriter(writer).writeObject(jsonObjectBuilder.build());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
