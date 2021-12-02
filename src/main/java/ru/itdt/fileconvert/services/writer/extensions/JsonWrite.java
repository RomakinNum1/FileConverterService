package ru.itdt.fileconvert.services.writer.extensions;

import com.google.gson.stream.JsonWriter;
import ru.itdt.fileconvert.services.constructions.Building;
import ru.itdt.fileconvert.services.writer.FileWrite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class JsonWrite implements FileWrite {
    public void write(List<Building> buildings, String path) throws IOException {
        try (FileWriter fileWriter = new FileWriter(path)) {
            JsonWriter jsonWriter = new JsonWriter(fileWriter);

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
}
