package net.queencoder.springboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "metrics", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"system_name", "metric_name", "date_created"})
})
public class Metrics {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
//	@EmbeddedId
//    private MetricUniqueKey metricUniqueKey;
//    
	@NotBlank(message = "System Name is mandatory")
	@NotNull(message = "Invalid System: Name is System")
    @Size(min = 3, max = 100, message = "Invalid Name: Must be of 3 - 100 characters")
	@Column(name = "system_name")
    private String systemName;
	
	@NotBlank(message = "Metrics Name is mandatory")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 100, message = "Invalid Name: Must be of 3 - 100 characters")
    @Column(name = "metric_name")
    private String metricName;
    
   @Column(name = "date_created")
    private Long dateCreated;
   
   @Column(name = "value_estimated")
    private int valueEstimated;
   
}
