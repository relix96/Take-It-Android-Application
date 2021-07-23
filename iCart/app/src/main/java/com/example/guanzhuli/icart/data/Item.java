package com.example.guanzhuli.icart.data;

import java.io.Serializable;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class Item implements Serializable {
    private Integer id;
    private String nomeProduto;
    private Double preco;
    private Double precoPack;
    private String porcao;
    private String porcaoPack;
    private Integer quantidadeMinima;
    private String descricao;
    private Integer idCategoria;
    private String mImageurl;

    public Item(Integer idProduto, String nomeProduto, Double preco,Double precoPack, String porcao, String porcaoPack, Integer unidade, String descricao, Integer idCategoria) {
        super();
        this.id = idProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.porcao = porcao;
        this.precoPack = precoPack;
        this.porcaoPack = porcaoPack;
        this.quantidadeMinima = unidade;
        this.descricao = descricao;
        this.idCategoria = idCategoria;

    }
    public Item(Integer idProduto, String nomeProduto, Double preco,Double precoPack, String porcao, String porcaoPack, Integer unidade, String descricao, Integer idCategoria, String mImageurl) {
        super();
        this.id = idProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.porcao = porcao;
        this.precoPack = precoPack;
        this.porcaoPack = porcaoPack;
        this.quantidadeMinima = unidade;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
        this.mImageurl = mImageurl;

    }


    public Item(){

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getPrecoPack() {
        return precoPack;
    }

    public void setPrecoPack(Double precoPack) {
        this.precoPack = precoPack;
    }

    public String getPorcao() {
        return porcao;
    }

    public void setPorcao(String porcao) {
        this.porcao = porcao;
    }

    public String getPorcaoPack() {
        return porcaoPack;
    }

    public void setPorcaoPack(String porcaoPack) {
        this.porcaoPack = porcaoPack;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public void setImageurl(String imageurl) {
        this.mImageurl = imageurl;
    }
    public String getImageurl() {
        return mImageurl;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

}
