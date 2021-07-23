package com.example.guanzhuli.icart.service;

import com.example.guanzhuli.icart.data.ClientData;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientService {
    @POST("/user/login/cliente/{mail}")
    Call <ResponseBody> loginClient(@Body RequestBody pws, @Path("mail") String mail);

    @POST("/user/createUser")
    Call <ResponseBody> createClient(@Body ClientData client);

    @DELETE("/user/apagarSessao/{idSessao}")
    Call <ResponseBody> deleteSession(@Path("idSessao") String hashCode);

    @PUT("user/update/{idSessao}")
    Call <ResponseBody> updateCliente(@Body RequestBody client, @Path("idSessao") String hashCode);


}
