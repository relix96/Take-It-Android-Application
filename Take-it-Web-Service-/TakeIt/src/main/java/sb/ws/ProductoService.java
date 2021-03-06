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
import sb.dao.ProductoDAO;
import sb.dao.SessaoDAO;
import sb.data.ProductoData;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ProductoService {

	private final static Logger logger = LoggerFactory.getLogger(ProductoService.class);

	@Autowired
	ProductoDAO productoDAO;

	@Autowired
	SessaoDAO sessaoDAO;

	@GetMapping("/product/category/{nome_categoria}/{idSessao}")
	public ResponseEntity<?> listProductsByCategory(@PathVariable("nome_categoria") String nome_categoria,
			@PathVariable("idSessao") String idSessao) {
		try {
			sessaoDAO.validarSessaoOpt(idSessao);
			logger.info(">findAllByCategory(ProductsByCategory)");
			return new ResponseEntity<List<ProductoData>>(productoDAO.findAllProductsByCategory(nome_categoria),HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/product/category/destaques/{idSessao}")
	public ResponseEntity<?> findDestaques(@PathVariable("idSessao") String idSessao) {
		try {
			sessaoDAO.validarSessaoOpt(idSessao);
			logger.info(">findAllByCategory(ProductsByCategory)");
			logger.info("Teste");
			return new ResponseEntity<List<ProductoData>>(productoDAO.findDestaques(),
					HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}