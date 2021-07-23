package sb.ws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import sb.SBApplication;
import sb.dao.CategoriaDAO;
import sb.dao.SessaoDAO;
import sb.data.CategoriaData;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CategoriaService {

	private final static Logger logger = LoggerFactory.getLogger(CategoriaService.class);

	@Autowired
	private CategoriaDAO categoriaDAO;

	@Autowired
	private SessaoDAO sessaoDAO;

	@GetMapping("/category/findAll/{idSessao}")
	public ResponseEntity<?> listCategorias(@PathVariable("idSessao") String idSessao) {
		try {
			sessaoDAO.validarSessaoOpt(idSessao);
			logger.info(">findAll(Categories)");
			return new ResponseEntity<List<CategoriaData>>(categoriaDAO.findAllCategories(), HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}