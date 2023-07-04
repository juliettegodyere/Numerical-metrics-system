package net.queencoder.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import net.queencoder.springboot.dto.MetricsSummary;
import net.queencoder.springboot.dto.UpdateMetrics;
import net.queencoder.springboot.exception.ResourceNotFoundException;
import net.queencoder.springboot.model.Metrics;
import net.queencoder.springboot.service.MetricsService;

@RestController
public class MetricsController {
	
	@Autowired private MetricsService service;
	
	@GetMapping("/metrics")
	 public ResponseEntity<List<Metrics>> getMetricsBySystem(
			 @Valid 
			 @RequestParam String system_name, 
			 @RequestParam(required=false) String metric_name,
			 @RequestParam(required=false) Long from,
			 @RequestParam(required=false) Long to){
			System.out.println(system_name);
			System.out.println(metric_name);
		return ResponseEntity.ok().body(service.getMetrics(system_name, metric_name, from, to));
	}
	
	@GetMapping("/metrics/{id}")
    public ResponseEntity<Metrics> getMetricsById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
        return ResponseEntity.ok().body(service.getMetricsById(id));
    }
	
	@PostMapping("/metrics")
	public ResponseEntity<Metrics> createMetrics(@Valid @RequestBody Metrics metrics,
			@RequestParam(value = "value", required = false) Integer value,
    		@RequestParam(value = "date", required = false) Long date
			){
		int defaultValue = 1;
		long defaultDate = System.currentTimeMillis();
        
        int val = value != null ? value : defaultValue;
        Long dt = value != null ? date : defaultDate;
        
        //MetricUniqueKey key = metrics.getMetricUniqueKey();
        //key.setDateCreated(dt);
        metrics.setValueEstimated(val);
        metrics.setDateCreated(dt);
        
        return new ResponseEntity<Metrics>(service.createMetric(metrics), HttpStatus.OK);
	}
	@PutMapping("/metrics/{id}")
    public ResponseEntity<Metrics> updateMetric(@Valid @PathVariable(value = "id") Long id,
         @Valid @RequestBody UpdateMetrics metrics) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.updateMetric(metrics, id));
    }
	
	@GetMapping("/metricsummary")
	 public ResponseEntity<List<MetricsSummary>> getMetricsSummary(
			 @Valid 
			 @RequestParam String system_name, 
			 @RequestParam(required=false) String metric_name,
			 @RequestParam(required=false) Long from,
			 @RequestParam(required=false) Long to){
			System.out.println(system_name);
			System.out.println(metric_name);
		return ResponseEntity.ok().body(service.getMetricsSumary(system_name, metric_name, from, to));
	}
}
