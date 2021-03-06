package sb.dao;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sb.data.SessaoData;
import sb.ws.ExpiredSessionException;


@Component
@Transactional
public class SessaoDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(SessaoDAO.class);
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public final static long SESSION_LIMIT = 15 * 60 * 1000;
    
    public int create(SessaoData sessao) {
        return jdbcTemplate.update("insert into sessao (id, data_fim,idCliente) values (?,?,?)",sessao.getId(), sessao.getData_fim(), sessao.getIdCliente());
    }
   
    public void validarSessao(String idSessao) throws ExpiredSessionException {
		logger.info(">validarSessao:"+idSessao);
    	Date agora = new Date();
    	Date limite = new Date(agora.getTime() + SessaoDAO.SESSION_LIMIT);
    	logger.info("Tempo agora:"+agora.getTime()+"#Tempo limite:"+limite.getTime());
    	if (1 != jdbcTemplate.update("update sessao set data_fim = ? where id = ? and data_fim >= ?",limite,idSessao,agora)) {
    		throw new ExpiredSessionException("Sessao Invalida");
    	}
    }    

	public void validarSessaoOpt(String idSessao) throws ExpiredSessionException {
		if (idSessao != null && idSessao.length() > 0 && !"NONE".equals(idSessao)) {
			logger.info(">validarSessaoOpt:"+idSessao);
			validarSessao(idSessao);
		}
	}

    public int delete(String id) {
    	return jdbcTemplate.update("delete from sessao where id = ?",id);
    }
    

}
