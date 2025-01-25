package com.xmlexample.jaxb5.adaptors;

import com.xmlexample.jaxb5.domain.Item;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.Map;
import java.util.stream.Collectors;

public class ItemsAdaptor extends XmlAdapter<ItemsWrapper, Map<String, Item>> {

    @Override
    public Map<String, Item> unmarshal(ItemsWrapper wrapper) throws Exception {
        return wrapper.getItems()
                .stream()
                .collect(Collectors.toMap(ItemValue::getProductCode, ItemValue::toItem));
    }

    @Override
    public ItemsWrapper marshal(Map<String, Item> map) throws Exception {
        // map to list
        return new ItemsWrapper(map.entrySet()
                .stream()
                .map(e -> new ItemValue(e.getKey(), e.getValue()))
                .collect(Collectors.toList()));
    }
}
