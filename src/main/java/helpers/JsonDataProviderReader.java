package helpers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonDataProviderReader {
    public JSONObject readJsonData(String jsonPath) {
        JSONParser parser = new JSONParser();
        JSONObject data = null;

        try {
            data = (JSONObject) parser.parse(new FileReader(jsonPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        return data;
    }
}
