package com.example.guanzhuli.icart.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompraData {
	
	private Integer id;
	private Integer idCliente;
	private Date DataCompra;
	private Double precoTotal;
	private Double precoFinal;
	private Double taxaTotal;
	private List<CompraLinhaData> linhas = new ArrayList<CompraLinhaData>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Date getDataCompra() {
		return DataCompra;
	}
	public void setDataCompra(Date dataCompra) {
		DataCompra = dataCompra;
	}

	@Override
	public String toString() {
		return "CompraData [id=" + id + ", idCliente=" + idCliente + ", DataCompra=" + DataCompra +"]";
	}
	
	public CompraData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CompraData(Integer id, Integer idCliente, Date dataCompra, Integer IdMorada) {
		super();
		this.id = id;
		this.idCliente = idCliente;
		this.DataCompra = dataCompra;
	}
	
	public CompraData(Integer id, Integer idCliente) {
		super();
		this.id = id;
		this.idCliente = idCliente;
	}

	public Double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public Double getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(Double precoFinal) {
		this.precoFinal = precoFinal;
	}

	public Double getTaxaTotal() {
		return taxaTotal;
	}

	public void setTaxaTotal(Double taxaTotal) {
		this.taxaTotal = taxaTotal;
	}

	public List<CompraLinhaData> getLinhas() {
		return linhas;
	}
	public void setLinhas(List<CompraLinhaData> linhas) {
		this.linhas = linhas;
	}
}
