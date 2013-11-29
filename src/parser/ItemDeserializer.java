package parser;

import gameObject.item.Item;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;


public class ItemDeserializer extends StdKeyDeserializer {

    protected ItemDeserializer () {
        super(Item.class);
    }

    @Override
    protected Object _parse (String arg0, DeserializationContext arg1) throws Exception {

        String[] args = arg0.split(" ");
        Item i = new Item();
        i.setName(args[0]);
        /*
         * i.setStats(arg1.args[1]);
         * i.setActions(args[2]);
         */
        return i;
    }

}
