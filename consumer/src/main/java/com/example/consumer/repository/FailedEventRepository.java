package com.example.consumer.repository;

import com.example.consumer.config.domain.FailedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedEventRepository extends JpaRepository<FailedEvent,Long> {
}
