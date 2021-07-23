package sb.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import sb.data.CompraData;
import sb.data.CompraLinhaData;
import sb.ws.CarrinhoService;

@Component
@Transactional
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CompraDAO {

	private final Logger logger = LoggerFactory.getLogger(CarrinhoService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Integer guardarCarrinho(CompraLinhaData linha) throws DataAccessException {

		return jdbcTemplate.update(
				"insert into compra_linha (idProduto_hist, idCompra, quatidade, quantidadePack) values (?,?,?,?)",
				linha.getIdProdutoHist(),linha.getIdCompra(), linha.getQuantidade(), linha.getQuantidadePack());
	}

	public int create(CompraData compra) throws DataAccessException {
		logger.info("Teste => "+compra.toString());
		if (1 == jdbcTemplate.update("insert into compra (id,idCliente, Data_Compra, idMorada_hist) values (?,?,?,?)",
				compra.getId(),compra.getIdCliente(), compra.getDataCompra(), compra.getIdMorada_hist())) {
			return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() ", (rs, rownumber) -> new Integer(rs.getInt(1)));
		} else {
			logger.error("Problem creating Compra");
        	throw new RuntimeException("Problem creating Compra");
        }
	}

	public void createLinha(Integer idProduto, Integer idCompra, Integer quantidade, Integer quantidadePack) {
		logger.info("Inserir na compra_linha =>"+ idProduto+","+idCompra+","+quantidade+","+quantidadePack);
		try{
			jdbcTemplate.update("insert into compra_linha (idProduto_hist, idCompra, quantidade, quantidadePack) values (?,?,?,?)",
				idProduto, idCompra,quantidade,quantidadePack);
			return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() ", (rs, rownumber) -> new Integer(rs.getInt(1)))));
		} catch (Exception e) 
			// TODO: handle exception
		{
			logger.error("Problem creating Compra Linha");
	    	throw new RuntimeException("Problem creating Compra Linha");
	    }
	}
}
