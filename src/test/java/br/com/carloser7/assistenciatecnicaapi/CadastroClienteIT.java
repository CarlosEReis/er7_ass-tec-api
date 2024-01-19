package br.com.carloser7.assistenciatecnicaapi;

import br.com.carloser7.assistenciatecnicaapi.util.DatabaseCleaner;
import br.com.carloser7.assistenciatecnicaapi.util.ResourceUtils;
import br.com.carloser7.asstecnica.Application;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroClienteIT {

	@LocalServerPort
	private int port;
	private String token;
	private String jsonClienteCorreto;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/clientes";
		RestAssured.port = port;

		databaseCleaner.clearTables();

		jsonClienteCorreto = ResourceUtils.getContentFromResource("/json/correto/cliente.json");
		geraToken();
	}

	@Test
	void deveRetornar201_QuandoCadastrarCliente() {
		given()
			.header("Authorization", "Bearer ".concat(token))
			.body(jsonClienteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}


	private void geraToken() {
		AutenticacaoResponse autenticacaoResponse =
			given()
				.basePath("/auth/login")
				.body("{\n" +
						"    \"username\": \"admin@er7sistemas.com\",\n" +
						"    \"password\": \"admin123\"\n" +
						"}")
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
				.body()
				.as(AutenticacaoResponse.class);
		this.token = autenticacaoResponse.getToken();
	}
}

class AutenticacaoResponse {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}