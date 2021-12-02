package ru.itdt.fileconvert;

import ru.itdt.fileconvert.services.reader.ReadFactory;
import ru.itdt.fileconvert.services.reader.FileRead;
import ru.itdt.fileconvert.services.writer.WriteFactory;
import ru.itdt.fileconvert.services.writer.FileWrite;
import org.json.simple.parser.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 2){
                throw new IllegalArgumentException("Неверно указаны пути к файлам\nНужно указать: <путь к исходному файлу> <путь к новому файлу>");
            }

            FileRead reader = new ReadFactory().getExtention(args[0]);
            FileWrite writer = new WriteFactory().getExtention(args[1]);

            writer.write(reader.read(args[0]), args[1]);

            System.out.println("Файл " + args[1] + " создан");
        } catch (XMLStreamException | ParseException exeption) {
            throw new RuntimeException("Ошибка структуры содержания файла", exeption);
        } catch (FileNotFoundException exeption) {
            throw new RuntimeException("Файл не найден", exeption);
        } catch (IOException exeption) {
            throw new RuntimeException("Ошибка в чтении или записи файла", exeption);
        } catch (ParserConfigurationException | TransformerException exeption) {
            throw new RuntimeException("Ошибка при сохранении xml файла", exeption);
        }
    }
}
