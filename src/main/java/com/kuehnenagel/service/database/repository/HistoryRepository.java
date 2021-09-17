package com.kuehnenagel.service.database.repository;

import com.kuehnenagel.service.database.model.History;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface HistoryRepository extends ReactiveCrudRepository<History, Long> {
}
