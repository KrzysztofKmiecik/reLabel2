package pl.kmiecik.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MyJsonParameters {

    public Optional<Parameters> read(final String path) {

        Parameters parameters=null;
        Gson gson = new Gson();

        try (Reader reader = Files.newBufferedReader(Paths.get(path));) {
            parameters = gson.fromJson(reader, Parameters.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(parameters);
    }

    public void save (final String path, Parameters parameters) {

        Gson gson = new Gson();
            String myJson= gson.toJson(parameters);
        try {
            Files.write(Paths.get(path),myJson.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
