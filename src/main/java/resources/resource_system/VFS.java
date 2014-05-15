package resources.resource_system;

import java.io.*;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public enum VFS {
    INSTANCE;

    private String root = "";

    public String concatenatePath(String path1, String path2) {
        return Paths.get(path1).resolve(path2).toString();
    }
    public boolean exists (String path) {
        return new File(concatenatePath(root, path)).exists();
    }
    public boolean isDirectory (String path) {
        return new File(concatenatePath(root, path)).isDirectory();
    }
    public boolean isFile (String path) {
        return !isDirectory(path);
    }

    public String getAbsolutePath (String path) throws FileNotFoundException {
        try {
            if (exists(path)) {
                return new File(concatenatePath(root, path)).getCanonicalFile().getAbsolutePath();
            }
            else {
               throw new FileNotFoundException();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DataInputStream getDataInputStream (String path) throws IOException {
        String absolutePath = getAbsolutePath(path);
        FileInputStream fis = new FileInputStream(absolutePath);
        return(new DataInputStream(fis));
    }

    public byte[] getByteFile (String path) throws IOException {
        DataInputStream dis = getDataInputStream(path);

        byte[] bytes = new byte[dis.available()];
        int readBytes = dis.read(bytes);
        dis.close();

        return bytes;
    }

    public String getUTF8File (String path) throws IOException {
        DataInputStream dis = getDataInputStream(path);
        InputStreamReader isr = new InputStreamReader(dis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        StringBuilder stringBuilder = new StringBuilder();
        String strLine;

        while ((strLine = br.readLine()) != null)   {
            stringBuilder.append(strLine);
        }
        br.close();

        return stringBuilder.toString();
    }

    public Iterable<String> iterate(String from) {
        return new VFSIterator(from);
    }

    public class VFSIterator implements Iterable<String>, Iterator<String> {
        private Queue<File> fileQueue = new LinkedList<>();

        public VFSIterator (String path) {
            fileQueue.add(new File(concatenatePath(root, path)));
        }

        public String next() {
            File file = fileQueue.peek();

            if (file.isDirectory()) {
                File[] allFiles = file.listFiles();
                if (allFiles != null) {
                    Collections.addAll(fileQueue, allFiles);
                }
            }
            return fileQueue.poll().getAbsolutePath();
        }
        public boolean hasNext() {
            return !fileQueue.isEmpty();
        }

        public Iterator<String> iterator() {
            return this;
        }
    }
}
