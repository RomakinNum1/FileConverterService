package Open;

import Open.FileExtensions.Json;
import Open.FileExtensions.Xml;

public final class MainOpen {
    public static OpenFile openFile(String path) {
        if (path.substring(path.lastIndexOf('.')).equals(".xml")) {
            return new Xml(path);
        }

        if (path.substring(path.lastIndexOf('.')).equals(".json")) {
            return new Json(path);
        }

        return null;
    }
}
