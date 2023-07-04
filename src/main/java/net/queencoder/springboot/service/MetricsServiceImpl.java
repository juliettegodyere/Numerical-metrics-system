package net.queencoder.springboot.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.queencoder.springboot.dto.MetricsSummary;
import net.queencoder.springboot.dto.UpdateMetrics;
import net.queencoder.springboot.exception.ResourceNotFoundException;
import net.queencoder.springboot.model.Metrics;
import net.queencoder.springboot.repository.MetricSummaryProjection;
import net.queencoder.springboot.repository.MetricsRepository;

@Service
public class MetricsServiceImpl implements MetricsService{
	
	@Autowired private MetricsRepository repository;

	@Override
	public List<Metrics> getMetrics(String system_name, String metric_name, Long from, Long to) {
		return repository.findByParams(system_name, metric_name, from, to);
	}
	
	@Override
	public Metrics getMetricsById(long id) throws ResourceNotFoundException{
		Metrics metrics = repository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Metric not found for this id :: " + id));
			return metrics;
	}

	@Override
	public Metrics createMetric(Metrics metrics) {
		return repository.save(metrics);
	}

	@Override
	public Metrics updateMetric(UpdateMetrics new_update, long id) throws ResourceNotFoundException{
		Metrics metric = repository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Metric not found for this id :: " + id));
		
		metric.setMetricName(new_update.getMetricName());
		metric.setSystemName(new_update.getSystemName());
		metric.setDateCreated(new_update.getDateCreated());
		
		metric.setValueEstimated(new_update.getValueEstimated() == 0 ? metric.getValueEstimated()+1 : new_update.getValueEstimated());
		final Metrics updatedMetric = repository.save(metric);
		
		return updatedMetric;
	}

	@Override
	public List<MetricsSummary> getMetricsSumary(String system_name, String metric_name, Long from, Long to) {
		List<MetricSummaryProjection> map = repository.findBySummary(system_name, metric_name, from, to);
		List<MetricsSummary> summary = new ArrayList<>();
		MetricsSummary ms = new MetricsSummary();
		
		for(MetricSummaryProjection s:map) {
			ms.setSummedValue(s.getSummedValue());
			ms.setSystem_name(s.getSystemName());
			ms.setMetrics_name(s.getMetricName());
			summary.add(ms);
		}
		return summary;
	}

}
