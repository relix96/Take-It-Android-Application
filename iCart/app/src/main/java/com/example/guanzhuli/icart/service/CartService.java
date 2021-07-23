package com.example.guanzhuli.icart.service;

import com.example.guanzhuli.icart.data.CompraLinhaData;
import com.example.guanzhuli.icart.data.Item;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CartService {
    @POST("/carrinho/comprarCarrinho/{idSessao}")
    Call <ResponseBody> buyCart(@Body RequestBody cart, @Path("idSessao") String idSessao);

    @GET("/carrinho/findCarrinho/{idSessao}")
    Call <ResponseBody> findCart(@Path("idSessao") String idSessao);

    @GET("/carrinho/findCarrinho/countProducts/{idSessao}")
    Call <ResponseBody> countProductsCart(@Path("idSessao") String idSessao);

    @POST("/carrinho/adicionar/{idSessao}")
    Call <ResponseBody> addCart(@Body CompraLinhaData product, @Path("idSessao") String idSessao);

    @PUT("/carrinho/gravarCarrinho/{idSessao}")
    Call <ResponseBody> saveCart(@Body RequestBody products, @Path("idSessao") String idSessao);

    @PUT("/carrinho/gravarLinha/{idSessao}")
    Call <ResponseBody> saveProduct(@Body RequestBody product, @Path("idSessao") String idSessao);

    @DELETE("/carrinho/remover/{idSessao}/{idProduto}")
    Call <ResponseBody> deleteProduct(@Path("idSessao") String idSessao,@Path("idProduto") String idProduct);
}
