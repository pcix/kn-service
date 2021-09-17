package com.kuehnenagel.service;

import com.kuehnenagel.service.api.model.Request;
import com.kuehnenagel.service.api.model.Response;
import com.kuehnenagel.service.database.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceApplicationIT {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private HistoryRepository historyRepository;

	@BeforeEach
	void cleanup() {
		historyRepository.deleteAll().block();
	}

	@Test
	void process_Success() {
		int id = new Random().nextInt();
		webTestClient
				.post().uri("/process")
				.bodyValue(new Request(id, "success"))
				.exchange()
				.expectStatus().isOk()
				.expectBody(Response.class)
				.value(response -> assertEquals(id, response.getMessageId()));
		assertEquals(1, historyRepository.count().block());
	}

	@Test
	void process_Failed() {
		int id = new Random().nextInt();
		webTestClient
				.post().uri("/process")
				.bodyValue(new Request(id, "failed"))
				.exchange()
				.expectStatus().is5xxServerError();
		assertEquals(0, historyRepository.count().block());
	}

	@Test
	void process_WithoutId() {
		webTestClient
				.post().uri("/process")
				.bodyValue(new Request(null, "success"))
				.exchange()
				.expectStatus().isBadRequest();
		assertEquals(0, historyRepository.count().block());
	}

	@Test
	void process_EmptyMessage() {
		int id = new Random().nextInt();
		webTestClient
				.post().uri("/process")
				.bodyValue(new Request(id, ""))
				.exchange()
				.expectStatus().isBadRequest();
		assertEquals(0, historyRepository.count().block());
	}
}
