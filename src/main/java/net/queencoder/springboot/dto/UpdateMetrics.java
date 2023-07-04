package net.queencoder.springboot.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateMetrics {
	@NotBlank(message = "Invalid System: Empty System")
    @NotNull(message = "Invalid System: Name is System")
    @Size(min = 3)
    private String systemName;
    
	@NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3)
    private String metricName;
	
	@NotNull
	//@Size(min = 2, max = 2147483647, message = "Name must be between 2 and 2147483647 characters long")
	@Min(value = 1, message = "Invalid Date: Equals to zero or Less than zero")
    private Long dateCreated;
    
    private int valueEstimated;
}
