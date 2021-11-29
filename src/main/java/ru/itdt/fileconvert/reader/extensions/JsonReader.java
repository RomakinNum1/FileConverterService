package ru.itdt.fileconvert.reader.extensions;

import ru.itdt.fileconvert.constructions.Building;
import ru.itdt.fileconvert.reader.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record JsonReader(String path) implements FileReader {

    public List<Building> read() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        java.io.FileReader fileReader = new java.io.FileReader(path);
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
