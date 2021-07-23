package sb.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import sb.data.CarrinhoData;
import sb.data.CompraData;
import sb.data.LinhaData;
import sb.data.MoradaData;
import sb.ws.CarrinhoService;
import sb.dao.CompraDAO;

@Component
@Transactional
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CarrinhoDAO {

	private final Logger logger = LoggerFactory.getLogger(CarrinhoService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CompraDAO compraDAO;
	
	@Autowired
	private ProductoDAO produtoDAO;
	
	@Autowired
	private MoradaDAO moradaDAO;
		
	// procurar o carrinho pelo o id da sessão



	public CarrinhoData procurarCarrinho(String idSessao) throws DataAccessException {

		CarrinhoData result = jdbcTemplate.queryForObject("select c.id,c.idCliente from carrinho c, sessao s where s.id = ? and s.idCliente = c.idCliente",
				((rs, rownumber) -> new CarrinhoData(rs.getInt("c.id"), rs.getInt("c.idCliente"))), idSessao);

		List<LinhaData> linhas = result.getLinhas();
		jdbcTemplate
				.query("select p.id, p.versao, p.nomeProduto, p.preco, p.porcao,"
						+ "p.precoPack, p.porcaoPack,p.quantidadeMinima," + "p.descricao,l.quantidade, l.quantidadePack "
						+ "from produto p, carrinho_linha l where p.id = l.idProduto and l.idCarrinho = ?",
						(rs,rowNum) -> linhas.add(new LinhaData(rs.getInt("p.id"), rs.getInt("p.versao"),
										rs.getString("p.nomeProduto"), rs.getBigDecimal("p.preco"),
										rs.getBigDecimal("p.precoPack"), rs.getString("p.porcao"),
										rs.getString("p.porcaoPack"), rs.getInt("p.quantidadeMinima"), rs.getInt("l.quantidade"),
										rs.getInt("l.quantidadePack"), rs.getString("p.descricao"))),
						result.getId());
		logger.info("result:" + result);
		
		
		return result;
	}
	
	public LinhaData findVersao(LinhaData linha) throws DataAccessException{
		
		return null;
	}
	
	public Integer countProducts(String idSessao){
		
		String countProducts = "select count(carrinho_linha.idProduto) from carrinho_linha "
				+ "inner join carrinho on carrinho.id = carrinho_linha.idCarrinho "
				+ "inner join sessao on sessao.idCliente = carrinho.idCliente where sessao.id = ? ";
				
		int count = jdbcTemplate.queryForObject(countProducts, new Object[] { idSessao }, Integer.class);
		logger.info("Nº de Items no carrinho:" + count);
		
		return count;
	}

	public int create(CarrinhoData carrinho) throws DataAccessException {
		return jdbcTemplate.update("insert into carrinho (precoTotal,precoTaxa,precoFinal,idCliente) values (?,?,?,?)",
				carrinho.getPrecoTotal(), carrinho.getTaxaTotal(), carrinho.getPrecoFinal(), carrinho.getIdCliente());
	}

	private int getCarrinhoIdBySessionId(String idSessao) {
		return jdbcTemplate.queryForObject(
				"select car.id from carrinho car, sessao s where car.idCliente = s.idCliente and s.id = ?;",
				(rs, rownumber) -> new Integer(rs.getInt(1)), idSessao);
	}

	public int adicionarLinha(LinhaData linha, String idSessao) {
		// TODO Auto-generated method stub
		try {
			
			Integer idCarrinho = getCarrinhoIdBySessionId(idSessao);

			linha.setIdCarrinho(idCarrinho);
			logger.info("Adicionar linha ao carrinho: " + idCarrinho);

			//validar as qtds se estao a zero
			
			LinhaData carLinha = jdbcTemplate
					.queryForObject(
							"select idCarrinho, idProduto, quantidade, quantidadePack from carrinho_linha where idCarrinho = ? and idProduto = ?;",
							(rs, rownumber) -> new LinhaData(rs.getInt("idCarrinho"), rs.getInt("idProduto"),
									rs.getInt("quantidade"), rs.getInt("quantidadePack")),
							idCarrinho, linha.getIdProduto());
			logger.info("Teste="+carLinha.toString());

			// verificar se o carrinho e o produto sao iguais
			
			if (carLinha.getIdCarrinho() == linha.getIdCarrinho() && carLinha.getIdProduto() == linha.getIdProduto()) {
				// atualizar quantidade			
				linha.setQuantidade(linha.getQuantidade() + carLinha.getQuantidade());
				linha.setQuantidadePack(linha.getQuantidadePack() + carLinha.getQuantidadePack());
				atualizarLinha(linha, idSessao);
			} 

		} catch (EmptyResultDataAccessException e) {
			Integer idCarrinhoLinha = getCarrinhoIdBySessionId(idSessao);
			linha.setIdCarrinho(idCarrinhoLinha);

			return jdbcTemplate.update(
					"insert into carrinho_linha (idCarrinho,idProduto,quantidade,quantidadePack,versao) values (?,?,?,?,?)",
					linha.getIdCarrinho(), linha.getIdProduto(), linha.getQuantidade(), linha.getQuantidadePack(),
					linha.getVersao());
		}
		return 0;
		

	}

	public CarrinhoData apagarLinha(String idSessao, Integer idProduto) {

		Integer idCarrinho = getCarrinhoIdBySessionId(idSessao);

		jdbcTemplate.update("delete from carrinho_linha where idCarrinho = ? and idProduto = ?", idCarrinho,
				idProduto);
		
		return procurarCarrinho(idSessao);

	}

	public CarrinhoData atualizarLinha(LinhaData linha, String idSessao) {

		// TODO Auto-generated method stub
		int idCarrinho = getCarrinhoIdBySessionId(idSessao);
		logger.info("Atualizar linha do carrinho: " + idCarrinho);

		Integer idCarrinhoLinha = getCarrinhoIdBySessionId(idSessao);

		linha.setIdCarrinho(idCarrinhoLinha);

		logger.info(linha.toString());
		jdbcTemplate.update(
				"update carrinho_linha set quantidade = ?, quantidadePack = ? where idCarrinho = ? and idProduto = ?",
				linha.getQuantidade(), linha.getQuantidadePack(), linha.getIdCarrinho(), linha.getIdProduto());

		return procurarCarrinho(idSessao);
	}

	public CarrinhoData atualizarCarrinho(CarrinhoData carrinho, String idSessao) {
		// TODO Auto-generated method stub
		for(LinhaData car : carrinho.getLinhas()){
			atualizarLinha(car, idSessao);			
		}		
		return 	procurarCarrinho(idSessao);
	}
	
	
	
	public CarrinhoData comprarCarrinho(CarrinhoData carrinho, String idSessao) throws DataAccessException {	
		
		MoradaData moradaHist = moradaDAO.getMoradaByIdCliente(carrinho.getIdCliente());
		Integer newMoradaId = moradaDAO.createHist(moradaHist);
		logger.info(moradaHist.toString());
		logger.info("MoradaHist => "+newMoradaId.toString());
		
		Date dataCompra = new Date(System.currentTimeMillis());
		CompraData compra = new CompraData();
		compra.setDataCompra(dataCompra);
		compra.setIdCliente(carrinho.getIdCliente());		
		compra.setIdMorada_hist(newMoradaId);
		Integer newCompraId = compraDAO.create(compra);
		logger.info("Dados da compra: "+compra.toString());
		logger.info("newCompraId => "+newCompraId.toString());
		compra.setId(newCompraId);
		
		CarrinhoData result = inserirCompra(carrinho,compra.getId(), idSessao);
		
		return result;
	}
	
	public CarrinhoData inserirCompra(CarrinhoData carrinho,Integer idCompra, String idSessao) {
		logger.info("inserirCompra => "+carrinho);
		// TODO Auto-generated method stub
		
		for(LinhaData car : carrinho.getLinhas()){
			Integer idProduto = produtoDAO.createHist(car);
			compraDAO.createLinha(idProduto, idCompra, car.getQuantidade(), car.getQuantidadePack());
		}			
		this.apagarLinhasCarrinho(idSessao, carrinho.getId());
		return procurarCarrinho(idSessao);
	}

	private void apagarLinhasCarrinho(String idSessao, Integer id) {
		logger.info("apagarCarrinho:"+id);
		jdbcTemplate.update(
				"delete from carrinho_linha where idCarrinho = ?", id);
		
	}
}
