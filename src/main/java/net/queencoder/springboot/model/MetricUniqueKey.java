package net.queencoder.springboot.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"systemName", "metricName", "dateCreated"})
@Embeddable
public class MetricUniqueKey implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "System Name is mandatory")
	@Column(name = "system_name")
    private String systemName;
	
	@NotBlank(message = "Metrics Name is mandatory")
    @Column(name = "metric_name")
    private String metricName;
    
   @Column(name = "date_created")
    private Long dateCreated;
}
