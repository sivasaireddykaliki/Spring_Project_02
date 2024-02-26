package com.example.springproject2.helper;

import com.example.springproject2.entity.Product;

public class TestHelper {

    public Product getDummyProduct(long id, String name, String description, String manufacturer, boolean availability)
    {
        Product product= Product.builder()
                          .id(id)
                          .name(name)
                .description(description)
                .manufacturer(manufacturer)
                .availability(availability)
                .build();
        return product;

    }

    public String getUrl(String id)
    {
        String url="http://localhost:8080/api/products";
        if(id==null)
        {
            return url;
        }
        else {
            return url+id;
        }
    }
}
