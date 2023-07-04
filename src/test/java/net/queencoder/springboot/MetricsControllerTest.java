package net.queencoder.springboot;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.queencoder.springboot.controller.MetricsController;
import net.queencoder.springboot.dto.UpdateMetrics;
import net.queencoder.springboot.model.Metrics;
import net.queencoder.springboot.service.MetricsService;

@WebMvcTest(MetricsController.class)
public class MetricsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MetricsService metricsService;

	@Autowired
	private ObjectMapper objectMapper;

	/*
	 * system: string (required) - filters the list of metrics to the specified
	 * system ▪ name: string - filters the list of metrics to the specified name ▪
	 * from: integer - filters the list of metrics to those starting equal to or
	 * after the specified date/time ▪ to: integer - filters the list of metrics to
	 * those ending before the specified date/time
	 */
	@Test
	public void givenListOfMetrics_whenGetAllMetrics_thenReturnMetricsList() throws Exception {
		List<Metrics> listOfMetrics = new ArrayList<>();
		String system = "Customer Satisfactory Metrics";
		String name = "Repeat Purchase Rate ";
		Long start = Long.valueOf("1688162142853");
		Long end = System.currentTimeMillis();

		listOfMetrics.add(Metrics.builder().systemName("Customer Satisfactory Metrics")
				.metricName("Repeat Purchase Rate ").dateCreated(Long.valueOf("1688162142853")).valueEstimated(1).build());
		given(metricsService.getMetrics(system, name, start, end)).willReturn(listOfMetrics);

		listOfMetrics.add(Metrics.builder().systemName("Sales performance metrics").metricName("Email reply rate")
				.dateCreated(Long.valueOf("1688161483696")).valueEstimated(1).build());
		given(metricsService.getMetrics(system, name, start, end)).willReturn(listOfMetrics);

		listOfMetrics.add(
				Metrics.builder().systemName("Customer Satisfactory Metrics").metricName("Customer Retention Rate ")
						.dateCreated(Long.valueOf("1688162142853")).valueEstimated(1).build());
		given(metricsService.getMetrics(system, name, start, end)).willReturn(listOfMetrics);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/metrics?system_name="+system)
				.param("system", system).param("name", name).param("from", start.toString())
				.param("to", end.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.error.systemName").doesNotExist())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

	}

	/*
	 * Retrieves a single metric specified by {id}.Responses:
	 */

	@Test
	public void givenMetricId_whenGetMetricById_thenReturnMetricObject() throws Exception {
		long metricId = 1L;
		Metrics metric = Metrics.builder().systemName("Customer Satisfactory Metrics")
				.metricName("Repeat Purchase Rate ").dateCreated(Long.valueOf("1688162142853")).valueEstimated(1).build();
		given(metricsService.getMetricsById(metricId)).willReturn(metric);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/metrics/{metricId}", metricId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.error.id").doesNotExist())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

	}

	/**
	 * Creates a new metric, with a default value of 1, and a default date of the
	 * current date/time. Request Body: application/json: Note: This test is designed to fail if the System name and metric name is 
	 * not provided.
	 **/

	@Test
	public void givenMetricObject_whenCreateMetric_thenReturnSavedMetric() throws Exception {

		Metrics metrics = Metrics.builder().systemName("Customer Satisfactory Metrics")
				.metricName("Repeat Purchase Rate").dateCreated(Long.valueOf(System.currentTimeMillis())).valueEstimated(1).build();
		
		Mockito.when(metricsService.createMetric(metrics)).thenReturn(metrics);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/metrics")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(metrics));

		mockMvc.perform(requestBuilder).andExpect(status()
				.isOk()).andDo(print())
				.andExpect(jsonPath("$.error.systemName").doesNotExist())
				.andExpect(jsonPath("$.error.metricName").doesNotExist());

	}

	/**
	 * Updates the specified metric with the supplied metric. If value is not
	 * supplied, the existing value is incremented by 1. Request Body:
	 * application/json: - positive scenario
	 */
	@Test
	public void givenUpdatedMetric_whenUpdateMetric_thenReturnUpdateMetricObject() throws Exception {
		long metricId = 1L;
		Metrics savedMetric = Metrics.builder().systemName("Customer Satisfactory Metrics")
				.metricName("Repeat Purchase Rate ").dateCreated(Long.valueOf("1688162142853")).valueEstimated(1).build();

		UpdateMetrics upDatedMetric = UpdateMetrics.builder().systemName("Customer Satisfactory Metrics")
				.metricName("Repeat Purchase Rate ").dateCreated(Long.valueOf("1688162142853")).valueEstimated(1).build();

		String jwtToken = "";

		Optional<Metrics> userOptional = Optional.of(savedMetric);

		Mockito.when(metricsService.getMetricsById(metricId)).thenReturn(savedMetric);
		Mockito.when(metricsService.updateMetric(upDatedMetric, metricId)).thenReturn(savedMetric);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/metrics/{metricId}", metricId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(upDatedMetric));

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.error.systemName").doesNotExist())
				.andExpect(jsonPath("$.error.metricName").doesNotExist())
				.andExpect(jsonPath("$.error.createdDate").doesNotExist())
				.andExpect(jsonPath("$.valueEstimated", is(upDatedMetric.getValueEstimated())));
	}

}
