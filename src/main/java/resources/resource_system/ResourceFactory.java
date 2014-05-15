package resources.resource_system;

import resources.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ResourceFactory {

    public static final String ROOT = "data";

    private static ResourceFactory instance = null;
    private static VFS vfs;
    private static Logger logger = Logger.getLogger("ResourceFactoryLogger");
    private static Map<String, Resource> resources = new HashMap<>();

    private ResourceFactory() {

    }

    public static ResourceFactory getInstance() {
        if (instance == null) {
            vfs = VFS.INSTANCE;
            instance = new ResourceFactory();
            loadResources();
        }
        return instance;
    }

    public Resource get (String path) {
        try {
            String absolutePath = vfs.getAbsolutePath(vfs.concatenatePath(ROOT, path + ".xml"));
            return resources.get(absolutePath);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Resource " + path + " can't be reached.");
        }
    }

    private static Resource load(String text) {
        return SaxParser.parse(text);
    }

    private static void loadResources() {
        logger.info("Loading resources");

        try {
            for (String path : vfs.iterate(ROOT)) {
                if (vfs.isFile(path)) {
                    logger.info("Loading " + path);
                    Resource resource = load(vfs.getUTF8File(path));
                    resources.put(path, resource);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            logger.info("Loading finished");
        }
    }
}
