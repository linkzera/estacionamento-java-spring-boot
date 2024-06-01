package br.com.douglasvinicius.estacionamento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.douglasvinicius.estacionamento.models.Establishment;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void createEstablishmentSuccess() {
		var establishment = new Establishment("Estacionamento do Douglas", "12345678901234", "Rua do Douglas", "1234567890", 10, 10, null);
		webTestClient.post().uri("/establishment").bodyValue(establishment).exchange().expectStatus().isCreated();
	}

	@Test
	void createEstablishmentFail() {
		var establishment = new Establishment("", "12345678901234", "Rua do Douglas", "1234567890", 10, 10, null);
		webTestClient.post().uri("/establishment").bodyValue(establishment).exchange().expectStatus().isBadRequest();
	}

	@Test
	void findAllEstablishment() {
		webTestClient.get().uri("/establishment").exchange().expectStatus().isOk().expectBody().jsonPath("$").isArray();
	}

}
