package sb.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import sb.data.MoradaData;



@Component
@Transactional
@CrossOrigin(origins="http://localhost:4200", allowedHeaders="*")
public class MoradaDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(MoradaDAO.class);
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
        
    
    public int create(MoradaData morada) throws DataAccessException {
    	logger.info(">create("+morada+")");
        return jdbcTemplate.update("insert into morada (morada_1,morada_2,cod_postal,localidade, idCliente) values (?,?,?,?,?)",morada.getMorada_1(),morada.getMorada_2(),morada.getCod_postal(), morada.getLocalidade(), morada.getIdUser());
        
    }

    public int createHist(MoradaData moradaHist) throws DataAccessException {
    	logger.info(">create("+moradaHist+")");
        if (1 == jdbcTemplate.update("insert into morada_historico (morada_1,morada_2,cod_postal,localidade, idCliente) values (?,?,?,?,?)",moradaHist.getMorada_1(),moradaHist.getMorada_2(),moradaHist.getCod_postal(), moradaHist.getLocalidade(), moradaHist.getIdUser())) {
        	return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() ", (rs, rownumber) -> new Integer(rs.getInt(1)));
        } else {
        	throw new RuntimeException("Problem creating MoradaHist");
        }
    }
    
    
    public MoradaData getMoradaByIdCliente(int id) throws DataAccessException {
        return jdbcTemplate.queryForObject("select id, morada_1,morada_2,cod_postal,localidade, idCliente from morada where idCliente = ?",
        		                           ((rs,rownumber) -> new MoradaData(rs.getInt("id"),rs.getString("morada_1"),rs.getString("morada_2"), rs.getString("cod_postal"),rs.getString("localidade"), rs.getInt("idCliente"))),
        		                           id);
    }
    
    public int update(MoradaData address) throws DataAccessException {
        return jdbcTemplate.update("update morada set morada_1 = ?, morada_2 = ?,cod_postal = ?,localidade = ?, idCliente = ?  where id = ?",address.getMorada_1(),address.getMorada_2(),address.getCod_postal(), address.getLocalidade(),address.getIdUser(), address.getId());
    }

   

}
