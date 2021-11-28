package ru.itdt.fileconvert.constructions;

import java.util.ArrayList;
import java.util.List;

public final class Building {
    String name;
    List<String> films;

    public Building()
    {
        films = new ArrayList<>();
    }

    public List<String> getFilms() {
        return films;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
