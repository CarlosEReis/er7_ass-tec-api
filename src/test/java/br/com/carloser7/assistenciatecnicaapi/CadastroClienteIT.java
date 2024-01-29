package br.com.carloser7.assistenciatecnicaapi;

import br.com.carloser7.assistenciatecnicaapi.util.AutenticacaoUtil;
import br.com.carloser7.assistenciatecnicaapi.util.DatabaseCleaner;
import br.com.carloser7.assistenciatecnicaapi.util.ResourceUtils;
import br.com.carloser7.asstecnica.Application;
import br.com.carloser7.asstecnica.domain.model.Cliente;
import br.com.carloser7.asstecnica.domain.model.RoleType;
import br.com.carloser7.asstecnica.domain.repository.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@SpringBootTest(
		classes = {
			Application.class,
			AutenticacaoUtil.class,
			DatabaseCleaner.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroClienteIT {

	private static final Integer ID_CLIENTE_INEXISTENTE = 99999;

	@LocalServerPort private int port;
	@Autowired private DatabaseCleaner databaseCleaner;
	@Autowired private AutenticacaoUtil autenticacaoUtil;
	@Autowired private ClienteRepository clienteRepository;

	private final String jsonClienteCorreto;

	CadastroClienteIT() {
		jsonClienteCorreto = ResourceUtils.getContentFromResource("/json/correto/cliente.json");
	}

	@BeforeEach
	void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/clientes";
		RestAssured.port = port;

		databaseCleaner.clearTables();
		autenticacaoUtil.criaUsuarios();
	}

	@Test
	void deveRetornar201_QuandoCadastrarClienteCorretoComoAdmin() {
		String tokenUserAdmin = autenticacaoUtil.geraTokenUsuarioComRole(RoleType.ROLE_ADMIN);
		given()
			.header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUserAdmin))
			.body(jsonClienteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
		.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornar201_QuandoCadastrarClienteCorretoComoGestor() {
		String tokenUserGestor = autenticacaoUtil.geraTokenUsuarioComRole(RoleType.ROLE_GESTOR);
		given()
			.header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUserGestor))
			.body(jsonClienteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornar403_QuandoCadastrarClienteCorretoComoTecnico() {
		String tokenUserTecnico = autenticacaoUtil.geraTokenUsuarioComRole(RoleType.ROLE_TECNICO);
		given()
			.header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUserTecnico))
			.body(jsonClienteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@Test
	public void deveRetornar404_QuandoDeletarClienteInexistente() {
		String tokenUserAdmin = autenticacaoUtil.geraTokenUsuarioComRole(RoleType.ROLE_ADMIN);
		given()
			.header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(tokenUserAdmin))
			.pathParams("clienteID", ID_CLIENTE_INEXISTENTE)
		.when()
			.delete("/{clienteID}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornar409_QuandoDeletarClienteEmUso() {
		Cliente cliente = getClienteComContato();

		String tokenUserAdmin = autenticacaoUtil.geraTokenUsuarioComRole(RoleType.ROLE_ADMIN);
		given()
			.header(HttpHeaders.AUTHORIZATION, tokenUserAdmin)
			.pathParams("clienteID", cliente.getId())
		.when()
			.delete("/{clienteID}")
		.then()
			.statusCode(HttpStatus.CONFLICT.value());
	}

	private Cliente getClienteComContato() {
		Cliente cliente = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			cliente = objectMapper.readValue(jsonClienteCorreto, Cliente.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		Cliente finalCliente = cliente;
		cliente.getContatos().forEach(contato -> contato.setCliente(finalCliente));
		cliente = clienteRepository.save(finalCliente);
		return cliente;
	}
}

