package com.example.guanzhuli.icart.utils;

import com.example.guanzhuli.icart.service.CartService;
import com.example.guanzhuli.icart.service.CategoryService;
import com.example.guanzhuli.icart.service.ClientService;

public class API {
    public static final String URL = "http://192.168.56.1:8080/";

    public static ClientService getClientService(){
        return Client.getClient(URL).create(ClientService.class);
    }

    public static CategoryService getCategoryService(){
        return Client.getClient(URL).create(CategoryService.class);
    }

    public static CartService getCartService(){
        return  Client.getClient(URL).create(CartService.class);
    }

}
