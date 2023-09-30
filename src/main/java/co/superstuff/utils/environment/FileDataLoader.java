package co.superstuff.utils.environment;

import java.io.*;

public class FileDataLoader<K, V>  {
    protected File file;
    protected DataLoader<K, V> dataLoader;
    public FileDataLoader(File file) {
        super();
        this.file = file;
        this.dataLoader = new DataLoader<>();
    }

    public void reload() {
        try {
            InputStream is = new FileInputStream(file);
            dataLoader.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            OutputStream os = new FileOutputStream(this.file);
            dataLoader.save(os);
            os.close();
            System.out.println("File" + this.file.getPath() + "updated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
