package parser;

import gameObject.item.Item;
import com.fasterxml.jackson.databind.module.SimpleModule;


public class ItemModule extends SimpleModule {

    public ItemModule () {
        addKeyDeserializer(Item.class, new ItemDeserializer());
        addKeySerializer(Item.class, new ItemSerializer());
    }

}
