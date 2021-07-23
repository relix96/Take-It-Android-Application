package sb.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompraData {
	
	private Integer id;
	private Integer idCliente;
	private Date DataCompra;
	private Integer idMoradaHist;
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
	
	public Integer getIdMorada_hist() {
		return idMoradaHist;
	}
	public void setIdMorada_hist(Integer idMorada_hist) {
		idMoradaHist = idMorada_hist;
	}
	
	@Override
	public String toString() {
		return "CompraData [id=" + id + ", idCliente=" + idCliente + ", DataCompra=" + DataCompra + "idMoradaHist="+ idMoradaHist+"]";
	}
	
	public CompraData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CompraData(Integer id, Integer idCliente, Date dataCompra, Integer IdMorada, Integer idMoradaHist) {
		super();
		this.id = id;
		this.idCliente = idCliente;
		this.DataCompra = dataCompra;
		this.idMoradaHist = idMoradaHist;
	}
	
	public CompraData(Integer id, Integer idCliente) {
		super();
		this.id = id;
		this.idCliente = idCliente;
	}
	
	
	public List<CompraLinhaData> getLinhas() {
		return linhas;
	}
	public void setLinhas(List<CompraLinhaData> linhas) {
		this.linhas = linhas;
	}
}
