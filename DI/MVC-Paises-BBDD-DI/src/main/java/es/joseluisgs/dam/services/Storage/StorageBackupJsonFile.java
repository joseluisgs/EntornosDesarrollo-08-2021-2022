package es.joseluisgs.dam.services.Storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import es.joseluisgs.dam.models.Backup;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageBackupJsonFile implements IStorageBackup {
    private static StorageBackupJsonFile instance;

    private final Path currentRelativePath = Paths.get("");
    private final String ruta = currentRelativePath.toAbsolutePath().toString();
    private final String dir = ruta + File.separator + "data";
    private final String backupFile = dir + File.separator + "backup.json";


    private StorageBackupJsonFile() {
        init();
    }

    public static StorageBackupJsonFile getInstance() {
        if (instance == null) {
            instance = new StorageBackupJsonFile();
        }
        return instance;
    }

    private void init() {
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean save(Backup backup) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        boolean result = false;
        PrintWriter f = null;
        try {
            f = new PrintWriter(new FileWriter(backupFile));
            f.println(gson.toJson(backup));
            result = true;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            result = false;
        } finally {
            if (f != null) {
                f.close();
            }
        }
        return result;
    }

    @Override
    public Backup load() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Backup backup = null;
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(backupFile));
            backup = gson.fromJson(reader, new TypeToken<Backup>() {
            }.getType());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        return backup;
    }

    @Override
    public String getStoragePath() {
        return backupFile;
    }
}
