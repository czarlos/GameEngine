package parser;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class to wrap Jackson JSON parser. Handles serialization and 
 * deserialization of objects to and from JSON, both to strings and
 * files. Class can also perform deep clones.
 *
 */
public class JSONParser {
    ObjectMapper myMapper;

    public JSONParser () {
        myMapper = new ObjectMapper();
        myMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        myMapper.setVisibilityChecker(myMapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withGetterVisibility(Visibility.NONE)
                .withSetterVisibility(Visibility.NONE)
                .withFieldVisibility(Visibility.NONE)
                .withCreatorVisibility(Visibility.NONE));
        myMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                           false);
    }

    /**
     * Create a Json file representation of an object.
     * @param filename Path to file to save JSON
     * @param object Object to serialize to JSON
     */
    public void createJSON (String filename, Object object) {
        try {
            myMapper.writerWithDefaultPrettyPrinter().writeValue(
                                                                 new File("JSONs/" + filename +
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
    
    /**
     * Make a Json representation of an object.
     * @param object Object to serialize as Json
     * @return Object's Json string.
     */
    public String createJSONString(Object object){
        try {
            return myMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Create an object from a JSON file.
     * @param filename File containing JSON
     * @param c Object represented by JSON
     * @return New object
     */
    public <T> T createObjectFromFile (String filename, Class<T> c) {
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

    /**
     * Create an object from a JSON string
     * @param json JSON to make object from
     * @param c Class of object to create
     * @return New object
     */
    public <T> T createObject (String json, Class<T> c) {

        try {
            return myMapper.readValue(json, new TypeReference<T>() {
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * Create a deep clone of an object using JSON Parser
     * @param toClone Object to Clone
     * @param c Class of object to clone
     * @return Deep cloned object
     */
    public <T> T deepClone(Object toClone, Class<T> c){
        return createObject(createJSONString(toClone),c);
    }
}
