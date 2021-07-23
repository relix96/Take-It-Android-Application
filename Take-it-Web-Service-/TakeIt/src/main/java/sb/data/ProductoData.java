package sb.data;

public class ProductoData {

	private Integer id;
	private String nomeProduto;
	private Double preco;
	private Double precoPack;
	private String porcao;
	private String porcaoPack;
	private String quantidadeMinima;
	private String descricao;
	private Integer idCategoria;


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

	public String getQuantidadeMinima() {
		return quantidadeMinima;
	}
	
	public void setQuantidadeMinima(String quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
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
	
	

	public ProductoData() {
		super();
	}

	public ProductoData(Integer idProduto, String nomeProduto, Double preco,Double precoPack, String porcao, String porcaoPack, String unidade, String descricao, Integer idCategoria) {
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

	@Override
	public String toString() {
		return String.format("ProductData [idProduto=%s, nomeProduto=%s, preco=%.2f, porcao=%s, precoPack=%.2f, porcaoPack=%s, unidade=%s, descricao=%s, idCategoria=%s]", id, nomeProduto, preco, porcao,precoPack,porcaoPack, quantidadeMinima, descricao ,idCategoria );
	}

}
