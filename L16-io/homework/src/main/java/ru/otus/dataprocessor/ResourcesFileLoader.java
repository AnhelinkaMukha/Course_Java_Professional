package ru.otus.dataprocessor;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import ru.otus.model.Measurement;

import java.util.ArrayList;
import java.util.List;
public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        List<Measurement> measurementsList = new ArrayList<>();
        try (var jsonReader =
                     Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            if (jsonReader == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            JsonArray jsonFromTheFile = jsonReader.read().asJsonArray();
            for (jakarta.json.JsonValue jsonValue : jsonFromTheFile) {
                Measurement measurement = new Measurement(jsonValue.asJsonObject().getString("name"), jsonValue.asJsonObject().getJsonNumber("value").doubleValue());
                measurementsList.add(measurement);
            }
        }
        return measurementsList;
    }
}
