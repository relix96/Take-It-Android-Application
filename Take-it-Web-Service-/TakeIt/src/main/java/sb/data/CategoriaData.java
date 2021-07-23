package sb.data;

public class CategoriaData {

	private Integer id;
	private String nome_categoria;
	private String descricao;
	private String imagem;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome_categoria() {
		return nome_categoria;
	}

	public void setNome_Categoria(String nome_categoria) {
		this.nome_categoria = nome_categoria;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao; 
	}

	public String getImagem() {
		return imagem;
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	

	public CategoriaData() {
		super();
	}

	public CategoriaData(Integer id, String nome_categoria, String descricao, String imagem) {
		super();
		this.id = id;
		this.nome_categoria = nome_categoria;
		this.descricao = descricao;
		this.imagem = imagem;
	}

	@Override
	public String toString() {
		return String.format("CategoyData [ id=%s ,nome_produto=%s, descricao=%s, imagem = %s]", id, nome_categoria, descricao, imagem);
	}

}
