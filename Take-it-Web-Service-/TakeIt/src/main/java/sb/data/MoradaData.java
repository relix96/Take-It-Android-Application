package sb.data;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="http://localhost:4200", allowedHeaders="*")

public class MoradaData {	

	private Integer id;
	private String morada_1;
	private String morada_2;
	private String cod_postal;
	private String localidade;
	private Integer idUser;
	
	
	

	
	public Integer getIdUser() {
		return idUser;
	}


	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}


	public Integer getId() {
		return id;
	}


	public void setIdMorada(Integer id) {
		this.id = id;
	}
	
	public String getMorada_1() {
		return morada_1;
	}


	public void setMorada_1(String morada_1) {
		this.morada_1 = morada_1;
	}
	


	public String getMorada_2() {
		return morada_2;
	}


	public void setMorada_2(String morada_2) {
		this.morada_2 = morada_2;
	}



	public String getCod_postal() {
		return cod_postal;
	}


	public void setCod_postal(String cod_postal) {
		this.cod_postal = cod_postal;
	}


	public String getLocalidade() {
		return localidade;
	}


	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	
	public MoradaData() {
		super();
	}


	public MoradaData(Integer id, String morada_1, String morada_2, String cod_postal, String localidade, Integer idUser) {
		super();
		this.id = id;	
		this.morada_1 = morada_1;
		this.morada_2 = morada_2;
		this.localidade = localidade;
		this.cod_postal = cod_postal;
		this.idUser = idUser;
		
	}

	@Override
	public String toString() {
		return String.format("AddressData id=%s ,morada1=%s, morada2 = %s ,cod_postal=%s, localidade=%s idUser= %s ",id,morada_1, morada_2 ,cod_postal, localidade, idUser);
		}

	
	
	
	

}
