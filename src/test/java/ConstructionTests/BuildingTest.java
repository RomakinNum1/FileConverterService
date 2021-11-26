package ConstructionTests;

import Constructions.Building;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BuildingTest {
    Building building;

    @Before
    public void createBuilding()
    {
        building = new Building();
        building.name = "Test";
        building.films = new ArrayList<>();

        building.films.add("film1");
        building.films.add("film2");
    }

    @Test
    public void notNullOdject()
    {
        assertNotNull(building);
    }

    @Test
    public void getName()
    {
        assertEquals(building.name, "Test");
    }

    @Test
    public void getFilms1()
    {
        assertEquals(building.films.get(0), "film1");
    }

    @Test
    public void getFilms2()
    {
        assertEquals(building.films.get(1), "film2");
    }

    @Test
    public void setName()
    {
        building.name = "NewTest";
        assertEquals(building.name, "NewTest");
    }

    @Test
    public void setFilms()
    {
        building.films = new ArrayList<>();
        building.films.add("film3");
        assertEquals(building.films.get(0), "film3");
    }
}
