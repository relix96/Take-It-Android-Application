package sb.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sb.data.CategoriaData;


@Component
@Transactional
public class CategoriaDAO {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int create(CategoriaData category) throws DataAccessException {
        return jdbcTemplate.update("insert into Categoria (nome_categoria, descricao) values (?,?)",category.getNome_categoria(), category.getDescricao());
    }
    
    public CategoriaData read(int idCategory) throws DataAccessException {
        return jdbcTemplate.queryForObject("select id,nome_categoria,descricao, imagem from Categoria where id = ?",
        		                           ((rs,rownumber) -> new CategoriaData(rs.getInt("id"),rs.getString("nome_categoria"),rs.getString("descricao"),rs.getString("imagem"))),
        		                           idCategory);
    }
    
    public int update(CategoriaData category) throws DataAccessException {
        return jdbcTemplate.update("update categoria set (nome_categoria, descricao) values (?,?) where id = ?",category.getNome_categoria(),category.getDescricao(), category.getId());
    }
    
    public int delete(int idCategory) throws DataAccessException {
        return jdbcTemplate.update("delete from Categoria where id = ?",idCategory);
    }

    public List<CategoriaData> findAllCategories() throws DataAccessException {
    	List<CategoriaData> result = new ArrayList<CategoriaData>();
        jdbcTemplate.query("select id,nome_categoria, descricao, imagem from Categoria",
                           (rs, rowNum) -> result.add(new CategoriaData(rs.getInt("id"),rs.getString("nome_categoria"),rs.getString("descricao"),rs.getString("imagem"))));
        return result;
    }

}
