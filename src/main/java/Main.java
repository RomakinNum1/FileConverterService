import Constructions.Building;
import Open.MainOpen;
import Open.OpenFile;
import Save.MainSave;

import java.util.ArrayList;

public final class Main {
    public static void main(String[] args) {
        OpenFile file = MainOpen.openFile(args[0]);

        ArrayList<Building> buildings = new ArrayList<>(file.open());

        MainSave.saveFile(args[1]).save(buildings);

        System.out.println("Файл " + args[1] + " создан");
    }
}
