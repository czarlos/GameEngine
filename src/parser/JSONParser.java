package parser;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONParser {
    ObjectMapper myMapper;

    public JSONParser () {
        myMapper = new ObjectMapper();
        myMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public void createJSON (String filename, Object object) {
        try {
            myMapper.writerWithDefaultPrettyPrinter().writeValue(new File("JSONs/" + filename +
                                                                          ".json"), object);
        }
        catch (JsonGenerationException e) {
            e.printStackTrace();
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T createObject (String filename, Class<T> c) {
        try {
            return myMapper.readValue(new File("JSONs/" + filename + ".json"),
                                      new TypeReference<T>() {
                                      });
        }
        catch (JsonParseException e) {
            e.printStackTrace();
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
