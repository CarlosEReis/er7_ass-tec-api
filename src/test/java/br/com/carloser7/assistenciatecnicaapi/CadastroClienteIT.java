package br.com.carloser7.assistenciatecnicaapi;

import br.com.carloser7.assistenciatecnicaapi.util.AutenticacaoUtil;
import br.com.carloser7.assistenciatecnicaapi.util.DatabaseCleaner;
import br.com.carloser7.assistenciatecnicaapi.util.ResourceUtils;
import br.com.carloser7.asstecnica.Application;
import br.com.carloser7.asstecnica.domain.model.Roles;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
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

	@LocalServerPort private int port;
	@Autowired private DatabaseCleaner databaseCleaner;
	@Autowired private AutenticacaoUtil autenticacaoUtil;

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
		String tokenUserAdmin = autenticacaoUtil.geraTokenUsuarioComRole(Roles.ROLE_ADMIN);
		given()
			.header("Authorization", "Bearer ".concat(tokenUserAdmin))
			.body(jsonClienteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
		.statusCode(HttpStatus.CREATED.value());
	}

}

