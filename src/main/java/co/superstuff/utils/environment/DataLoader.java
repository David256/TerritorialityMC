package co.superstuff.utils.environment;

import co.superstuff.saved.UserData;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class DataLoader<K, V> {
    protected final Yaml yaml;
    protected Map<K, V> dataMap;

    public DataLoader() {
        yaml = new Yaml();
    }

    public Map<K, V> get() {
        return dataMap;
    }

    public void update(Map<K, V> data) {
        dataMap = data;
    }

    public void load(InputStream is) {
        yaml.load(is);

        dataMap = yaml.load(is);

        if (dataMap == null) {
            System.err.println("Cannot load user data");
        } else if (dataMap.isEmpty()) {
            System.err.println("Warn: user data is empty. If it is the first time, ignore please");
        }
    }

    public void save(OutputStream os) throws IOException {
        String content = yaml.dump(dataMap);

        os.write(content.getBytes());
    }

    public void put(K k, V v) {
        dataMap.put(k, v);
    }
}
