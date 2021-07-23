package com.example.guanzhuli.icart.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class CompraLinhaData implements Serializable {



	private Integer idProduto;
	private String nomeProduto;
	private BigDecimal preco;
	private BigDecimal precoPack;
	private String porcao;
	private String porcaoPack;
	private Integer quantidade;
	private Integer quantidadePack;
	private Integer quantidadeMinima;
	private Integer versao;
	private String descricao;
	private Integer idCompra;

	public final static BigDecimal IVA = new BigDecimal(0.06);

	public Integer getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
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


	public Integer getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getQuantidadePack() {
		return quantidadePack;
	}

	public void setQuantidadePack(Integer quantidadePack) {
		this.quantidadePack = quantidadePack;
	}


	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public BigDecimal getPrecoPack() {
		return precoPack;
	}

	public void setPrecoPack(BigDecimal precoPack) {
		this.precoPack = precoPack;
	}

	public Integer getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}

	public BigDecimal getPrecoIVA() {
		return (getPreco() != null ? preco : BigDecimal.ZERO).multiply(BigDecimal.ONE.add(IVA));
	}

	public BigDecimal getPrecoPackIVA() {
		return (getPrecoPack() != null ? preco : BigDecimal.ZERO).multiply(BigDecimal.ONE.add(IVA));
	}

	public BigDecimal getPrecoTotal() {
		return (preco != null ? preco : BigDecimal.ZERO).multiply(quantidade != null ? new BigDecimal(quantidade) : BigDecimal.ZERO)
				.add((precoPack != null ? precoPack : BigDecimal.ZERO).multiply(quantidadePack != null ? new BigDecimal(quantidadePack) : BigDecimal.ZERO));
	}

	public BigDecimal getTaxaTotal() {
		return getPrecoTotal().multiply(IVA);
	}

	public BigDecimal getPrecoFinal() {
		return getPrecoTotal().multiply(BigDecimal.ONE.add(IVA));
	}




	public CompraLinhaData(Integer idProduto, Integer versao, String nomeProduto, Double preco, Double precoPack,
						   String porcao, String porcaoPack, Integer quantidadeMinima, Integer quantidade, Integer quantidadePack, String descricao) {
		super();
		this.idProduto = idProduto;
		this.versao = versao;
		this.nomeProduto = nomeProduto;
		this.preco = BigDecimal.valueOf( preco);
		this.precoPack = BigDecimal.valueOf(precoPack);
		this.porcao = porcao;
		this.porcaoPack = porcaoPack;
		this.quantidadeMinima = quantidadeMinima;
		this.quantidade = quantidade;
		this.quantidadePack = quantidadePack;
		this.descricao = descricao;
	}

	public CompraLinhaData(Integer idCarrinho, Integer idProduto, Integer quantidade, Integer quantidadePack) {
		super();

		this.idCompra = idCarrinho;
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.quantidadePack = quantidadePack;
		// TODO Auto-generated constructor stub
	}
	public CompraLinhaData(){
		super();
	}
	@Override
	public String toString() {
		return "LinhaData [precoFinal="+getPrecoFinal()+", idProduto=" + idProduto + ", nomeProduto=" + nomeProduto + ", preco" + preco + ", precoPack="
				+ precoPack + ", porcao=" + porcao + ", porcaoPack=" + porcaoPack + ", quantidade=" + quantidade
				+ ", quantidadePack=" + quantidadePack + ", QuantidadeMinima=" + quantidadeMinima + ", versao=" + versao + ", idCarrinho="
				+ idCompra + ", descricao=" + descricao + "]";
	}


}
