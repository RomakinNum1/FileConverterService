package ru.itdt.fileconvert;

import ru.itdt.fileconvert.reader.ReadFactory;
import ru.itdt.fileconvert.reader.FileReader;
import ru.itdt.fileconvert.writer.WriteFactory;
import ru.itdt.fileconvert.writer.FileWriter;
import org.json.simple.parser.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 2) throw new IllegalArgumentException("Неверно указаны пути к файлам\nНужно указать: <путь к исходному файлу> <путь к новому файлу>");

            FileReader reader = new ReadFactory().openFile(args[0]);
            FileWriter writer = new WriteFactory().saveFile(args[1]);

            writer.write(reader.read());

            System.out.println("Файл " + args[1] + " создан");
        } catch (XMLStreamException | ParseException e) {
            System.out.println("Ошибка структуры содержания файла");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка в чтении или записи файла");
            e.printStackTrace();
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Ошибка при сохранении xml файла");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
