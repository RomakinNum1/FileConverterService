package Save.FileExtensions;

import Constructions.Building;
import Save.SaveFile;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class Json implements SaveFile {
    String path;

    public Json(String _path) {
        path = _path;
    }

    public void save(ArrayList<Building> result) {
        JsonWriter jsonWriter;
        try (FileWriter fileWriter = new FileWriter(path)) {
            jsonWriter = new JsonWriter(fileWriter);

            jsonWriter.beginObject();
            jsonWriter.name("films");
            jsonWriter.beginArray();
            for (Building b : result) {
                for (String film : b.films) {
                    jsonWriter.beginObject();
                    jsonWriter.name("film");

                    jsonWriter.beginObject();
                    jsonWriter.name("name");
                    jsonWriter.value(film);

                    jsonWriter.name("building");
                    jsonWriter.value(b.name);
                    jsonWriter.endObject();

                    jsonWriter.endObject();
                }
            }
            jsonWriter.endArray();
            jsonWriter.endObject();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
