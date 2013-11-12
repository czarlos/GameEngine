package grid;

import java.util.ArrayList;
import parser.JSONParser;


public class FromJSONFactory {

    JSONParser p;

    public FromJSONFactory () {
        p = new parser.JSONParser();
    }

    public <T> T make (String type, int ID) {
        ArrayList<T> list = p.createObject("defaults/" + type, ArrayList.class);
        if (ID < list.size() & ID > -1) { return list.get(ID); }
        System.out.println(type + " ID " + ID + " doesn't exist.");
        return null;
    }

}
