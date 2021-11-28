package ru.itdt.fileconvert.writer.extensions;

import ru.itdt.fileconvert.constructions.Building;
import ru.itdt.fileconvert.writer.FileWriter;

import java.io.IOException;
import java.util.List;

public final class JsonWriter implements FileWriter {
    private final String path;

    public JsonWriter(String path) {
        this.path = path;
    }

    public void save(List<Building> buildings) throws IOException {
        com.google.gson.stream.JsonWriter jsonWriter;
        java.io.FileWriter fileWriter = new java.io.FileWriter(path);
        jsonWriter = new com.google.gson.stream.JsonWriter(fileWriter);

        jsonWriter.beginObject();
        jsonWriter.name("films");
        jsonWriter.beginArray();
        for (Building building : buildings) {
            for (String film : building.getFilms()) {
                jsonWriter.beginObject();
                jsonWriter.name("film");

                jsonWriter.beginObject();
                jsonWriter.name("name");
                jsonWriter.value(film);

                jsonWriter.name("building");
                jsonWriter.value(building.getName());
                jsonWriter.endObject();

                jsonWriter.endObject();
            }
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
        jsonWriter.close();
    }
}
