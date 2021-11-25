package Save;

import Save.FileExtensions.Json;
import Save.FileExtensions.Xml;

public final class MainSave {
    public static SaveFile saveFile(String path) {
        if (path.substring(path.lastIndexOf('.')).equals(".xml")) {
            return new Xml(path);
        }

        if (path.substring(path.lastIndexOf('.')).equals(".json")) {
            return new Json(path);
        }

        return null;
    }
}
