package read;

import ru.itdt.fileconvert.constructions.Building;
import ru.itdt.fileconvert.reader.ReadFactory;
import ru.itdt.fileconvert.reader.FileReader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class JsonTest {
    FileReader file;
    List<Building> buildings;

    @Before
    public void openJson() throws Exception {
        String path = "src/test/resources/test.json";
        file = new ReadFactory().openFile(path);
        buildings = new ArrayList<>(file.getData());
    }

    @Test
    public void notNullFile()
    {
        assertNotNull(file);
    }

    @Test
    public void notNullList()
    {
        assertNotNull(buildings);
    }

    @Test
    public void correctLengthList()
    {
        assertEquals(buildings.size(), 2);
    }

    @Test
    public void correctDataList1()
    {
        assertEquals(buildings.get(0).getName(), "JamMool");
    }

    @Test
    public void correctDataList2()
    {
        assertEquals(buildings.get(0).getFilms().size(), 4);
    }

    @Test
    public void correctDataList3()
    {
        assertEquals(buildings.get(1).getName(), "Kolisey");
    }

    @Test
    public void correctDataList4()
    {
        assertEquals(buildings.get(1).getFilms().size(), 4);
    }
}
