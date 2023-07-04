package net.queencoder.springboot.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.queencoder.springboot.dto.MetricsSummary;
import net.queencoder.springboot.model.Metrics;

public interface MetricsRepository extends JpaRepository<Metrics, Long>{
	
	//@Query(value = "SELECT * FROM Metrics Where system_name = :system_name And (metric_name = :metric_name OR :metric_name IS NULL) And (date_created BETWEEN :from AND :to) OR (:from IS NULL AND :to IS NULL)", nativeQuery = true)
	//@Query(value = "SELECT * FROM Metrics WHERE system_name = :system_name AND (metric_name = :metric_name OR :metric_name IS NULL) AND date_created BETWEEN :from AND :to  OR (:from IS NULL AND :to IS NULL)", nativeQuery = true)
	@Query(value = "SELECT * FROM Metrics WHERE system_name = :system_name AND (metric_name = :metric_name OR :metric_name IS NULL) AND (date_created BETWEEN :from AND :to OR (:from IS NULL AND :to IS NULL))", nativeQuery = true)
    List<Metrics> findByParams(
    		@Param("system_name") String system_name, 
    		@Param("metric_name") String metric_name,
    		@Param("from") Long from,
    		@Param("to") Long to);
	
	@Query(value = "SELECT system_name as systemName, metric_name as metricName, sum(value_estimated) as summedValue FROM Metrics WHERE system_name = :system_name AND (metric_name = :metric_name OR :metric_name IS NULL) AND (date_created BETWEEN :from AND :to OR (:from IS NULL AND :to IS NULL)) GROUP BY system_name, metric_name", nativeQuery = true)
	//@Query(value = "SELECT system_name, metric_name, SUM(value_estimated) as summedValue FROM Metrics Where system_name = :system_name And (metric_name = :metric_name OR :metric_name IS NULL) And date_created BETWEEN :from AND :to GROUP BY system_name, metric_name", nativeQuery = true)
    List<MetricSummaryProjection> findBySummary(
    		@Param("system_name") String system_name, 
    		@Param("metric_name") String metric_name,
    		@Param("from") Long from,
    		@Param("to") Long to);
}


