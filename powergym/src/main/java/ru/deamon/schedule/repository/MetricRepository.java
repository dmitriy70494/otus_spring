package ru.deamon.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.deamon.schedule.model.Metric;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
}
