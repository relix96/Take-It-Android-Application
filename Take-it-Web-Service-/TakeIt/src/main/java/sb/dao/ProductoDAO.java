package sb.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sb.data.LinhaData;
import sb.data.ProductoData;


@Component
@Transactional
public class ProductoDAO {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final Logger logger = LoggerFactory.getLogger(ProductoDAO.class);

    public int create(ProductoData producto) throws DataAccessException {
         jdbcTemplate.update("insert into produto (nomeProduto, preco,precoPack,porcao,porcaoPack, quantidadeMinima,descricao, idCategoria) values (?,?,?,?,?,?,?,?)",producto.getNomeProduto(),producto.getPreco(),producto.getPrecoPack(),producto.getPorcao(),producto.getPorcaoPack(),producto.getQuantidadeMinima(),producto.getDescricao(),producto.getIdCategoria());
        Integer newProductoHistId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() ",
				(rs, rownumber) -> new Integer(rs.getInt(1)));
        return newProductoHistId;
    }
    
    public int createHist(LinhaData productoHist) throws DataAccessException {
    	logger.info("Inserir produto Histórico: "+productoHist.toString());
        if (1 == jdbcTemplate.update("insert into produto_historico(nomeProduto, preco,precoPack,porcao,porcaoPack, quantidadeMinima,descricao) values (?,?,?,?,?,?,?)",productoHist.getNomeProduto(),productoHist.getPreco(),productoHist.getPrecoPack(),productoHist.getPorcao(),productoHist.getPorcaoPack(),productoHist.getQuantidadeMinima(),productoHist.getDescricao())) {
			return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() ", (rs, rownumber) -> new Integer(rs.getInt(1)));
		} else {
			logger.error("Problem creating produto Histórico");
	    	throw new RuntimeException("Problem creating produto Histórico");
	    }
    }
    
    public ProductoData read(int idProduto) throws DataAccessException {
        return jdbcTemplate.queryForObject("select id,nomeProduto, preco,precoPack,porcao,porcaoPack, unidade,descricao, idCategoria from Produto where id = ?",
        		                           ((rs,rownumber) -> new ProductoData(rs.getInt("id"),rs.getString("nomeProduto"),rs.getDouble("preco"),rs.getDouble("precoPack"),rs.getString("porcao"),rs.getString("porcaoPack"),rs.getString("unidade"),rs.getString("descricao"),rs.getInt("idCategoria"))),        		                           
        		                           idProduto);
    }
    
    public ProductoData readProduct(int idCategoria) throws DataAccessException {
        return jdbcTemplate.queryForObject("nomeProduto, preco,precoPack,porcao,porcaoPack, unidade,descricao, idCategoria from Produto where idCategoria = ?",
        		                           ((rs,rownumber) -> new ProductoData(rs.getInt("id"),rs.getString("nomeProduto"),rs.getDouble("preco"),rs.getDouble("precoPack"),rs.getString("porcao"),rs.getString("porcaoPack"),rs.getString("unidade"),rs.getString("descricao"),rs.getInt("idCategoria"))),
        		                           idCategoria);
    }
    
    public int delete(int idProduct) throws DataAccessException {
        return jdbcTemplate.update("delete from Produto where id = ?",idProduct);
    }

    public List<ProductoData> findAllProducts() throws DataAccessException {
    	List<ProductoData> result = new ArrayList<ProductoData>();
        jdbcTemplate.query("select id, nomeProduto, preco,precoPack,porcao,porcaoPack, unidade,descricao, idCategoria from Produto",
                           (rs, rowNum) -> result.add(new ProductoData(rs.getInt("id"),rs.getString("nomeProduto"),rs.getDouble("preco"),rs.getDouble("precoPack"),rs.getString("porcao"),rs.getString("porcaoPack"),rs.getString("unidade"),rs.getString("descricao"),rs.getInt("idCategoria"))));
        return result;
    }
    
    public List<ProductoData> findDestaques() throws DataAccessException {
    	List<ProductoData> result = new ArrayList<ProductoData>();
        jdbcTemplate.query("select produto.id, nomeProduto, preco,precoPack,porcao,porcaoPack, quantidadeMinima, produto.descricao, produto.idCategoria from Produto where destaque = 1",
                           (rs, rowNum) -> result.add(new ProductoData(rs.getInt("id"),rs.getString("nomeProduto"),rs.getDouble("preco"),rs.getDouble("precoPack"),rs.getString("porcao"),rs.getString("porcaoPack"),rs.getString("quantidadeMinima"),rs.getString("descricao"),rs.getInt("idCategoria"))));
        
        return result;
    }
    
    public List<ProductoData> findAllProductsByCategory(String nome_categoria) throws DataAccessException {
    	List<ProductoData> result = new ArrayList<ProductoData>();
        jdbcTemplate.query("select produto.id, nomeProduto, preco,precoPack,porcao,porcaoPack, quantidadeMinima, produto.descricao, produto.idCategoria from Produto inner join categoria on produto.idcategoria = categoria.id where categoria.nome_categoria =  ?",
                           (rs, rowNum) -> result.add(new ProductoData(rs.getInt("id"),rs.getString("nomeProduto"),rs.getDouble("preco"),rs.getDouble("precoPack"),rs.getString("porcao"),rs.getString("porcaoPack"),rs.getString("quantidadeMinima"),rs.getString("descricao"),rs.getInt("idCategoria"))),nome_categoria);
        return result;
    }
    
    

}
