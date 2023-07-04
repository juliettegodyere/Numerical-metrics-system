package net.queencoder.springboot.repository;

public interface MetricSummaryProjection {
	String getSystemName();
    String getMetricName();
    int getSummedValue();
}
