package resources.resource_system;

import java.io.File;

public enum VFS {
    INSTANCE;

    private String root = ".";

    public boolean exists (String path) {
        return new File(root + File.separator + path).exists();
    }

    public boolean isDirectory (String path) {
        return new File(root + File.separator + path).isDirectory();
    }
}
