package com.example.guanzhuli.icart.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class CustomerCart {
	
	private Integer id;
	private Integer idCliente;	
	private List<LinhaData> linhas = new ArrayList<LinhaData>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public CustomerCart() {
		super();
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	
	public List<LinhaData> getLinhas() {
		if (linhas == null) this.linhas = new ArrayList<LinhaData>();
		return linhas;
	}
	
	public void setLinhas(List<LinhaData> linhas) {
		this.linhas = linhas;
	}

	public CustomerCart inserirProducto(LinhaData linha) {
		//adiciona produto a lista e recalcula		
		this.linhas.add(linha.getIdProduto(),linha);
		return this;
	} 
	
	
	public BigDecimal getPrecoTotal() {
		BigDecimal result = new BigDecimal(0);
		for (LinhaData linha : linhas) {
			result = result.add(linha.getPrecoTotal());
			
		}
		return result;
	}
	
	
	public BigDecimal getTaxaTotal() {
		BigDecimal result = new BigDecimal(0);
		for (LinhaData linha : linhas) {
			result = result.add(linha.getTaxaTotal());
		}
		return result;
	}
	
	
	public BigDecimal getPrecoFinal() {
		BigDecimal result = new BigDecimal(0);
		for (LinhaData linha : linhas) {
			result = result.add(linha.getPrecoFinal());
		}
		return result;
	}
	


	public CustomerCart(Integer id, Integer idCliente) {
		super();
		this.id = id;
		this.idCliente = idCliente;
	}

	@Override
	public String toString() {
		return "CarrinhoData [Preço do carrinho:"+getPrecoFinal() + ", id=" + id + ", idCliente=" + idCliente + ", linhas=" + linhas + "]";
	}

}
