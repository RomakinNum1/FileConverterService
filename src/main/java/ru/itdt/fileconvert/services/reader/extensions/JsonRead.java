package ru.itdt.fileconvert.services.reader.extensions;

import ru.itdt.fileconvert.services.constructions.Building;
import ru.itdt.fileconvert.services.reader.FileRead;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class JsonRead implements FileRead {
    public List<Building> read(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try (FileReader fileReader = new FileReader(path)) {
            JSONArray jsonBuild = (JSONArray) (((JSONObject) parser.parse(fileReader)).get("buildings"));

            List<Building> buildings = new ArrayList<>();
            for (Object node : jsonBuild) {
                JSONObject jsonObject = (JSONObject) ((JSONObject) node).get("building");
                Building building = new Building();
                building.setName(((String) jsonObject.get("name")));
                building.getFilms().addAll(getFilms(jsonObject));
                buildings.add(building);
            }

            return buildings;
        }
    }

    private ArrayList<String> getFilms(JSONObject json) {
        JSONArray jsonFilms = (JSONArray) json.get("films");
        ArrayList<String> filmsName = new ArrayList<>();

        for (Object film : jsonFilms) {
            JSONObject jsonObject = (JSONObject) film;

            filmsName.add((String) jsonObject.get("film"));
        }

        return filmsName;
    }
}
