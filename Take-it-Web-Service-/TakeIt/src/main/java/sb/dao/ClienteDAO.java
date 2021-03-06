package sb.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import sb.SBApplication;
import sb.data.CarrinhoData;
import sb.data.ClienteData;
import sb.data.MoradaData;
import sb.data.SessaoData;
import sb.ws.ClienteService;

@Component
@Transactional
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ClienteDAO {

	private final static Logger logger = LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private MoradaDAO moradaDAO;

	@Autowired
	private SessaoDAO sessaoDAO;

	@Autowired
	private CarrinhoDAO carrinhoDAO;

	@PostMapping
	public Integer create(ClienteData cliente, String uniqueID, StringBuilder errorBuilder) throws DataAccessException {

		try {
			logger.info("Verificar Cliente");
			if (validaDadosCliente(cliente, errorBuilder, true)) {
				logger.info("O email" + cliente.getMail() + "não existe!");
				jdbcTemplate.update(
						"insert into cliente (id, primeiro_nome,apelido,contacto,email,password) values (?,?,?,?,?,?)",
						cliente.getId(), cliente.getPrimeiro_nome(), cliente.getApelido(), cliente.getContacto(),
						cliente.getMail(), cript(cliente.getPassword()));

				Integer newClienteId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() ",
						(rs, rownumber) -> new Integer(rs.getInt(1)));

				logger.info(">createUser:" + newClienteId);				
				cliente.getMorada().setIdUser(newClienteId);
				moradaDAO.create(cliente.getMorada());

				Date data = new Date(System.currentTimeMillis() + SessaoDAO.SESSION_LIMIT);

				logger.info(uniqueID + data.toString() + newClienteId);

				SessaoData sessao = new SessaoData();
				sessao.setData_fim(data);
				sessao.setIdCliente(newClienteId);
				sessao.setId(uniqueID);
				sessaoDAO.create(sessao);

				CarrinhoData carrinho = new CarrinhoData();
				carrinho.setIdCliente(newClienteId);
				carrinhoDAO.create(carrinho);

				return newClienteId;
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errorBuilder.append(SBApplication.ERROR_MSG);
			return null;

		}
	}

	public String loginCliente(String email, String password) throws DataAccessException, NoSuchAlgorithmException {
		logger.info("verificar cliente: " + email + password);
		String uniqueID = UUID.randomUUID().toString();
		if (validarLogin(email, password)) {
			Integer idCliente = this.getByEmail(email);
			SessaoData sessao = new SessaoData();
			Date data = new Date(System.currentTimeMillis() + SessaoDAO.SESSION_LIMIT);
			sessao.setId(uniqueID);
			sessao.setData_fim(data);
			sessao.setIdCliente(idCliente);
			sessaoDAO.create(sessao);
			return uniqueID;
		}
		return null;
	}

	public ClienteData readById(int id) throws DataAccessException {
		return jdbcTemplate.queryForObject(
				"select c.id, c.primeiro_Nome, c.apelido, c.contacto, c.email, c.password, m.id, m.morada_1, m.morada_2, m.cod_Postal, m.localidade, m.idCliente, s.id, s.data_fim, s.idCliente from cliente c, morada m, sessao s where m.idCliente = c.id and s.idCliente = c.id and c.id = ?",
				((rs, rownumber) -> new ClienteData(rs.getInt("id"), rs.getString("primeiro_nome"),
						rs.getString("apelido"), rs.getString("contacto"), rs.getString("email"),
						rs.getString("password"),
						new MoradaData(rs.getInt("m.id"), rs.getString("morada_1"), rs.getString("morada_2"),
								rs.getString("cod_Postal"), rs.getString("localidade"), rs.getInt("idCliente")),
						new SessaoData(rs.getString("s.id"), rs.getDate("data_fim"), rs.getInt("idCliente")))),
				id);
	}

	private boolean validaDadosCliente(ClienteData cliente, StringBuilder errorBuilder, boolean update) throws DataAccessException {
		logger.info(">validaDadosCliente(" + cliente + "#" + cliente.getMorada() + ")");
		try {
			// validar o input do Cliente
			isEmpty(cliente.getPrimeiro_nome(), "Primeiro Nome", errorBuilder);
			isEmpty(cliente.getApelido(), "Apelido", errorBuilder);
			isEmpty(cliente.getContacto(), "Contacto", errorBuilder);
			isEmpty(cliente.getMail(), "Email", errorBuilder);
			isEmpty(cliente.getPassword(), "Password", errorBuilder);
			isEmpty(cliente.getMorada().getMorada_1(), "Morada", errorBuilder);
			isEmpty(cliente.getMorada().getCod_postal(), "Código postal", errorBuilder);
			isEmpty(cliente.getMorada().getLocalidade(), "Localidade", errorBuilder);
			if (errorBuilder.toString().isEmpty()) {
				soTelefones(cliente.getContacto(), "Contacto", errorBuilder);
				soCodPostal(cliente.getMorada().getCod_postal(), "Código postal", errorBuilder);
				validarPassword(cliente.getPassword(), "Password", errorBuilder);
				validarEmail(cliente.getMail(), "Email", errorBuilder, update);
			}
			logger.info("errorBuilder:" + errorBuilder.toString());
			if (errorBuilder.toString().isEmpty()) {
				logger.info(">User com os dados todos(" + cliente + "#" + cliente.getMorada() + ")");
				return true;
			}
			return false;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public ClienteData getByCookie(String cookie) throws DataAccessException {
		return jdbcTemplate.queryForObject(
				"select c.id, c.primeiro_Nome, c.apelido, c.contacto, c.email, c.password, m.id, m.morada_1, m.morada_2, m.cod_Postal, m.localidade, m.idCliente, s.id, s.data_fim, s.idCliente from cliente c, morada m, sessao s where m.idCliente = c.id and s.idCliente = c.id and s.id = ?",
				((rs, rownumber) -> new ClienteData(rs.getInt("c.id"), rs.getString("c.primeiro_nome"),
						rs.getString("c.apelido"), rs.getString("c.contacto"), rs.getString("c.email"),
						rs.getString("c.password"),
						new MoradaData(rs.getInt("m.id"), rs.getString("m.morada_1"), rs.getString("m.morada_2"),
								rs.getString("m.cod_Postal"), rs.getString("m.localidade"), rs.getInt("m.idCliente")),
						new SessaoData(rs.getString("s.id"), rs.getDate("s.data_fim"), rs.getInt("s.idCliente")))),
				cookie);
	}

	public Integer getByEmail(String email) throws DataAccessException {
	
		Integer id = jdbcTemplate.queryForObject("SELECT id FROM cliente where email like ?;",
				(rs, rownumber) -> new Integer(rs.getInt(1)), email);

		return id;
	}
	
	public Integer getIdCliente(String idSessao) throws DataAccessException {
		
		Integer id = jdbcTemplate.queryForObject("select c.id from cliente c, sessao s where s.idCliente = c.id and s.id = ?",
				(rs, rownumber) -> new Integer(rs.getString(1)), idSessao);

		return id;
	}

	private boolean isEmpty(String campo, String nomeCampo, StringBuilder errorBuilder) {
		if (campo == null || campo.trim().length() == 0) {
			errorBuilder.append("O campo " + nomeCampo + " encontra-se vazio.\n");
			return false;
		}
		return true;
	}

	private boolean soTelefones(String campo, String nomeCampo, StringBuilder errorBuilder) {
		if (!campo.matches("[0-9]*") || campo.length() < 9) {
			errorBuilder.append("O campo " + nomeCampo + " inv�lido.\n");
			return false;
		}
		return true;
	}

	private boolean soCodPostal(String campo, String nomeCampo, StringBuilder errorBuilder) {
		if (!campo.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9]")) {
			errorBuilder.append("O campo " + nomeCampo + " inv�lido.\n");
			return false;
		}
		return true;
	}

	public boolean validarPassword(String password, String nomeCampo, StringBuilder errorBuilder)
			throws DataAccessException {
		if (password.length() >= 6) {
			return true;
		} else {
			errorBuilder.append("O campo " + nomeCampo + " inv�lido.\n");
			return false;
		}
	}

	public boolean validarLogin(String email, String password) throws DataAccessException, NoSuchAlgorithmException {
		logger.info("teste=> " + email + password);
		logger.info("login=>" + email + cript(password));
		boolean logon = jdbcTemplate.queryForObject(
				"SELECT exists(select 1 FROM cliente where email like ? and password like ?)",
				(rs, rownumber) -> new Boolean(rs.getBoolean(1)), email, cript(password));
		logger.info("logon:" + logon);
		return logon;
	}

	public boolean validarEmail(String email, String nomeCampo, StringBuilder errorBuilder, boolean isCreate) {
		if (isValid(email)) {
			try {
				if (isCreate && jdbcTemplate.queryForObject("SELECT true FROM cliente where email like ?;",
						(rs, rownumber) -> new Boolean(rs.getBoolean(1)), email)) {
					errorBuilder.append("Este email já existe.\n");
					return false;
				}
			} catch (EmptyResultDataAccessException e) {
				logger.error(e.getMessage(), e.getClass().getName());
				return false;
			}
		} else {
			errorBuilder.append("O campo " + nomeCampo + " est� inv�lido\n");
			return false;
		}
		return true;
	}

	public Integer updateCliente(ClienteData cliente, StringBuilder errorBuilder) throws DataAccessException {
		logger.info(cliente.toString());
		validaDadosCliente(cliente, errorBuilder, false);
		if (errorBuilder.toString().isEmpty()) {
			Integer updateCliente = jdbcTemplate.update(
					"update cliente set primeiro_nome = ? ,apelido = ?,contacto = ? ,email =?,password = ? where id = ?",
					cliente.getPrimeiro_nome(), cliente.getApelido(), cliente.getContacto(), cliente.getMail(),
					cliente.getPassword(), cliente.getId());

			if (updateCliente != null) {
				cliente.getMorada().setIdUser(cliente.getId());
				moradaDAO.update(cliente.getMorada());
			}

		} else
			return null;
		return null;

	}

	public String cript(String input) throws NoSuchAlgorithmException {
		MessageDigest pass = MessageDigest.getInstance("MD5");
		pass.reset();
		pass.update(input.getBytes());
		byte[] encryp = pass.digest();
		BigInteger bigInt = new BigInteger(1, encryp);
		String hashtext = bigInt.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;

	}

	public static boolean isValid(String email) {
		String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pat = Pattern.compile(emailRegex);
		logger.info("validar email #" + email + "=>" + pat.matcher(email).matches());
		if (email == null)
			return false;
		logger.info("validar email=>" + emailRegex, pat.matcher(email).matches());
		return pat.matcher(email).matches();
	}

	public static boolean CodPostal(String codPostal) {
		if (codPostal.length() != 8 && codPostal.charAt(4) != '-') {
			return false;
		} else {

			for (int idx = 0; codPostal.charAt(idx) < 8; idx++) {
				if (codPostal.charAt(idx) < '0' || codPostal.charAt(idx) > '9')
					return false;
			}
			return true;
		}
	}

	public boolean soNumeros(String validar) {
		if (validar.matches("[0-9]") && validar.length() == 9)
			return true;
		else
			return false;
	}

}
