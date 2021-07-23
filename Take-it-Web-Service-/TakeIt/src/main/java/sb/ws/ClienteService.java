package sb.ws;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import sb.SBApplication;
import sb.dao.ClienteDAO;
import sb.dao.SessaoDAO;
import sb.data.ClienteData;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ClienteService {

	private final static Logger logger = LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	private SessaoDAO sessaoDAO;

	@PostMapping("/user/login/cliente/{mail}")
	public ResponseEntity<?> loginCliente(@PathVariable("mail") String mail, @RequestBody String password) {
		try {
			logger.info(">loginCliente(" + mail + "#" + password + ")");
			String idSession = clienteDAO.loginCliente(mail, password);
			if (idSession != null) {
				logger.info("<findUser:" + idSession);
				return new ResponseEntity<String>(idSession, HttpStatus.OK);
			} else {
				logger.info("login inválido");
				return new ResponseEntity<String>("Login inválido.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/user/findByCookie/{idSessao}")
	public ResponseEntity<?> findByCookie(@PathVariable("idSessao") String idSessao) {
		try {
			sessaoDAO.validarSessao(idSessao);
			logger.info(">findUser(" + idSessao + ")");
			ClienteData result = clienteDAO.getByCookie(idSessao);
			logger.info("<findUser:" + result);
			return new ResponseEntity<ClienteData>(result, HttpStatus.OK);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/user/createUser")
	public ResponseEntity<?> createUser(@RequestBody ClienteData cliente, UriComponentsBuilder ucBuilder) {
		try {
			logger.info(">createUser(" + cliente + "#" + cliente.getMorada() + ")");
			StringBuilder errorBuilder = new StringBuilder();
			String uniqueID = UUID.randomUUID().toString();
			Integer newUserId = clienteDAO.create(cliente, uniqueID, errorBuilder);
			if (newUserId != null) {
				return new ResponseEntity<String>(uniqueID, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(errorBuilder.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/user/apagarSessao/{idSessao}")
	public ResponseEntity<?> apagarSessao(@PathVariable("idSessao") String idSessao) {
		try {
			logger.info("teste cookie=>" + idSessao);
			sessaoDAO.delete(idSessao);
			logger.info("Sessao terminada");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("user/update/{idSessao}")
	public ResponseEntity<?> updateCliente(@RequestBody ClienteData cliente,@PathVariable("idSessao") String idSessao) {
		try {
			logger.info(">updateUser(" + cliente + "#" + cliente.getMorada() + ")");
			sessaoDAO.validarSessao(idSessao);
			StringBuilder errorBuilder = new StringBuilder();
			clienteDAO.updateCliente(cliente, errorBuilder);
			if(errorBuilder.toString().isEmpty()){
			return new ResponseEntity<String>("Os seus dados foram atualizados com sucesso!",HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>(errorBuilder.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExpiredSessionException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.EXPIRED_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(SBApplication.ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}