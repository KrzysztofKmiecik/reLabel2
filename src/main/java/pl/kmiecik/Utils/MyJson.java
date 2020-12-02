package pl.kmiecik.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MyJson {

    public Optional<CrossRef> read(final String scannedAPN,final String path) {

        //  String str = "[{\"currentAPN\": \"28112233\",\"newAPN\": \"28114444\"},{\"currentAPN\": \"28222233\",\"newAPN\": \"28224444\"}]";

        Gson gson = new Gson();

        try ( Reader reader = Files.newBufferedReader(Paths.get(path));){

        //  CrossRef crossRef=gson.fromJson(str,CrossRef.class);
        Type crossRefListType = new TypeToken<ArrayList<CrossRef>>() {
        }.getType();

        List<CrossRef> crossRefList = gson.fromJson(reader, crossRefListType);

            return crossRefList.stream()
                    .filter(p -> p.getCurrentAPN().equals(scannedAPN))
                    .findFirst();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    }
