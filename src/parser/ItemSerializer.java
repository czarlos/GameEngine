package parser;

import java.io.IOException;
import gameObject.item.Item;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializer;


public final class ItemSerializer extends StdKeySerializer {

    public ItemSerializer(){
        super();
    }
    
    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
        if (value instanceof Item) {
            String s = ((Item) value).getName() + " " + ((Item) value).getStats() + " " + ((Item) value).getActions();
            jgen.writeFieldName(s);
      //      jgen.writeObject(value);
   //         jgen.writeString(((Item) value).getName());
      //      provider.defaultSerializeValue(value, jgen);
        } else {
            jgen.writeFieldName(value.toString());
        }
    }
}
