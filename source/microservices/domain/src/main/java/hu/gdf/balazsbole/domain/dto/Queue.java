package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@ApiModel(description = "Full DTO for queue")
public class Queue {
    @ApiModelProperty(value = "Unique internal identifier", example = "06484c9f-6f59-4b9f-ad5e-aaaa0ec332cc")
    @NotNull
    private UUID id;

    @ApiModelProperty(value = "The unique name of the queue.")
    @NotNull
    private String name;

    @ApiModelProperty(value = "The description of the queue.")
    private String description;

    @ApiModelProperty(value = "The queue belongs to this address.")
    @NotNull
    @Email
    private String email;

}
