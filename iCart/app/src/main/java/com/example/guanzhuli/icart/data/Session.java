package com.example.guanzhuli.icart.data;


import java.util.Date;

public class Session {

	private String id;
	private Date data_fim;
	private Integer idCliente;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Session() {
		super();
	}

	public Session(String id, Date data_fim, Integer idCliente) {
		super();
		this.id = id;
		this.data_fim = data_fim;
		this.idCliente = idCliente;
		
	}
	
		public Date getData_fim() {
		return data_fim;
	}

	public void setData_fim(Date data_fim) {
		this.data_fim = data_fim;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public String toString() {
		return String.format("SessaoData [id=%s, data_fim=%s, idCliente=%s]", id, data_fim, idCliente);
	}

}
