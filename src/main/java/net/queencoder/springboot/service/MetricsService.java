package net.queencoder.springboot.service;

import java.util.List;

import jakarta.validation.Valid;
import net.queencoder.springboot.dto.MetricsSummary;
import net.queencoder.springboot.dto.UpdateMetrics;
import net.queencoder.springboot.exception.ResourceNotFoundException;
import net.queencoder.springboot.model.Metrics;

public interface MetricsService {
	List<Metrics> getMetrics(String system_name, String metric_name, Long from, Long to);
	Metrics getMetricsById(long id)throws ResourceNotFoundException;
	Metrics createMetric(Metrics metrics);
	Metrics updateMetric(UpdateMetrics metrics, long id)throws ResourceNotFoundException;
	List<MetricsSummary> getMetricsSumary(String system_name, String metric_name, Long from, Long to);	
}
