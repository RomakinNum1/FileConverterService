package Open.FileExtensions;

import Constructions.Building;
import Open.OpenFile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Json implements OpenFile {
    String path;

    public Json(String _path) {
        path = _path;
    }

    public List<Building> getData() {
        JSONArray jsonBuild = readFile();

        List<Building> buildings = new ArrayList<>();
        for (Object obj : jsonBuild) {
            JSONObject jsonObject = (JSONObject) ((JSONObject) obj).get("building");
            Building building = new Building();
            building.name = ((String) jsonObject.get("name"));
            building.films.addAll(getFilms(jsonObject));

            buildings.add(building);
        }

        return buildings;
    }

    private JSONArray readFile() {
        JSONParser parser = new JSONParser();
        JSONArray jsonBuild = null;

        try (FileReader fileReader = new FileReader(path)) {
            jsonBuild = (JSONArray) (((JSONObject) parser.parse(fileReader)).get("buildings"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonBuild;
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
