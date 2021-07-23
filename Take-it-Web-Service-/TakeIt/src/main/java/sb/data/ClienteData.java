package sb.data;

public class ClienteData {

	private Integer id;
	private String primeiro_nome;
	private String apelido;
	private String contacto;
	private String mail;
	private String password;	
	
	private MoradaData morada;
	private SessaoData sessao;
	

	public SessaoData getSessao() {
		return sessao;
	}

	public void setSessao(SessaoData sessao) {
		this.sessao = sessao;
	}
	
	public MoradaData getMorada() {
		return morada;
	}

	public void setMorada(MoradaData morada) {
		this.morada = morada;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPrimeiro_nome() {
		return primeiro_nome;
	}

	public void setPrimeiro_nome(String primeiro_nome) {
		this.primeiro_nome = primeiro_nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	public ClienteData() {		
		super();
	}	

	public ClienteData(Integer id, String primeiro_nome, String apelido, String contacto, String mail, String password) {
		super();
		this.id = id;
		this.primeiro_nome = primeiro_nome;
		this.apelido = apelido;
		this.contacto = contacto;
		this.mail = mail;
		this.password = password;
		
	}	

	public ClienteData(Integer id, String primeiro_nome, String apelido, String contacto, String mail, String password, MoradaData morada ,SessaoData sessao) {
		this(id,primeiro_nome,apelido,contacto,mail,password);
		this.morada = morada;
		this.sessao = sessao;
	}
	
	public ClienteData(Integer id, String primeiro_nome, String apelido, String contacto, String mail, String password, SessaoData sessao) {
		this(id,primeiro_nome,apelido,contacto,mail,password);
		this.sessao = sessao;
	}

	@Override
	public String toString() {
		return String.format("UserData idCliente=%s , primeiro_nome= %s + , apelido=%s + , contacto= %s, mail=%s, password=%s",id,primeiro_nome, apelido,contacto,mail,password);
	}
}
