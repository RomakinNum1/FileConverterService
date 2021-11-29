package read;

import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import ru.itdt.fileconvert.constructions.Building;
import ru.itdt.fileconvert.reader.ReadFactory;
import ru.itdt.fileconvert.reader.FileReader;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class JsonTest {
    private static FileReader file;
    private static List<Building> buildings;

    @BeforeClass
    public static void openJson() throws IOException, XMLStreamException, ParseException {
        ClassLoader classLoader = JsonTest.class.getClassLoader();
        URL path = classLoader.getResource("test.json");

        if(path == null) throw new NullPointerException("Файл не найден");

        file = new ReadFactory().openFile(URLDecoder.decode(path.getPath(), StandardCharsets.UTF_8));
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
