package sb.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sb.SBApplication;
import sb.dao.CarrinhoDAO;
import sb.dao.SessaoDAO;
import sb.data.CarrinhoData;
import sb.data.LinhaData;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CarrinhoService {

	private final static Logger logger = LoggerFactory.getLogger(CarrinhoService.class);
	@Autowired
	CarrinhoDAO carrinhoDAO;

	@Autowired
	SessaoDAO sessaoDAO;
	
	@PostMapping("/carrinho/comprarCarrinho/{idSessao}")
	public ResponseEntity<?> comprarCarrinho(@RequestBody CarrinhoData carrinho, @PathVariable("idSessao") String idSessao){
		try{
			sessaoDAO.validarSessao(idSessao);
			CarrinhoData result = carrinhoDAO.comprarCarrinho(carrinho, idSessao);
			if(result != null){
				return new ResponseEntity<CarrinhoData>(result, HttpStatus.OK);
			}
			else{
				return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
			}
				
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}	
		
	}

	@GetMapping("/carrinho/findCarrinho/{idSessao}")
	public ResponseEntity<?> procurarCarrinho(@PathVariable("idSessao") String idSessao) {
		try {
			sessaoDAO.validarSessao(idSessao);
			CarrinhoData result = carrinhoDAO.procurarCarrinho(idSessao);
			return new ResponseEntity<CarrinhoData>(result, HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/carrinho/findCarrinho/countProducts/{idSessao}")
	public ResponseEntity<?> countProductsCarrinho(@PathVariable("idSessao") String idSessao) {
		try {
			sessaoDAO.validarSessao(idSessao);
			Integer result = carrinhoDAO.countProducts(idSessao);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

	@PostMapping("/carrinho/adicionar/{idSessao}")
	public ResponseEntity<?> adicionarLinha(@RequestBody LinhaData linha, @PathVariable String idSessao) {
		try {
			sessaoDAO.validarSessao(idSessao);
			logger.info(linha.toString());
			Integer result = carrinhoDAO.adicionarLinha(linha, idSessao);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/carrinho/gravarCarrinho/{idSessao}")
	public ResponseEntity<?> gravarCarrinho(@PathVariable String idSessao,@RequestBody CarrinhoData carrinho) {
		try {
			sessaoDAO.validarSessao(idSessao);
			logger.info("Guardar o carrinho:"+carrinho.toString());
			CarrinhoData result = carrinhoDAO.atualizarCarrinho(carrinho, idSessao);
			return new ResponseEntity<CarrinhoData>(result, HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@PutMapping("/carrinho/gravarLinha/{idSessao}")
	public ResponseEntity<?> gravarLinha(@PathVariable String idSessao,@RequestBody LinhaData linha) {
		try {
			sessaoDAO.validarSessao(idSessao);
			logger.info("Guardar o carrinho:"+linha.toString());
			CarrinhoData result = carrinhoDAO.atualizarLinha(linha, idSessao);
			return new ResponseEntity<CarrinhoData>(result, HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	


	@DeleteMapping("/carrinho/remover/{idSessao}/{idProduto}")
	public ResponseEntity<?> removerLinha(@PathVariable("idSessao") String idSessao, @PathVariable("idProduto") Integer idProduto) {
		try {
			sessaoDAO.validarSessao(idSessao);
			CarrinhoData result = carrinhoDAO.apagarLinha(idSessao,idProduto);
			return new ResponseEntity<CarrinhoData>(result, HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}