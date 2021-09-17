package com.kuehnenagel.service;

import com.kuehnenagel.service.api.model.Request;
import com.kuehnenagel.service.api.model.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceApplicationIT {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void process_Success() {
		int id = new Random().nextInt();
		webTestClient
				.post().uri("/process")
				.bodyValue(new Request(id, "success"))
				.exchange()
				.expectStatus().isOk()
				.expectBody(Response.class)
				.value(response -> assertThat(response.getMessageId()).isEqualTo(id));
	}

	@Test
	void process_Failed() {
		int id = new Random().nextInt();
		webTestClient
				.post().uri("/process")
				.bodyValue(new Request(id, "failed"))
				.exchange()
				.expectStatus().is5xxServerError();
	}

	@Test
	void process_WithoutId() {
		webTestClient
				.post().uri("/process")
				.bodyValue(new Request(null, "success"))
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	void process_EmptyMessage() {
		int id = new Random().nextInt();
		webTestClient
				.post().uri("/process")
				.bodyValue(new Request(id, ""))
				.exchange()
				.expectStatus().isBadRequest();
	}
}
