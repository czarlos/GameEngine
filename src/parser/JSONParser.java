package parser;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONParser {
    ObjectMapper myMapper;

    public JSONParser () {
        myMapper = new ObjectMapper();
    }

    public void createJSON (String filename, Object object) {
        try {
            myMapper.writeValue(new File(filename + ".json"), object);
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object createObjects (String filename, Class c) {
        try {
            Object object = myMapper.readValue(new File(filename + ".json"), c);
            return object;
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
