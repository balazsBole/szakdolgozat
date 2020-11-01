package hu.gdf.balazsbole.domain.dto;

import hu.gdf.balazsbole.domain.enumeration.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ApiModel(description = "Full DTO for thread")
public class EmailThread {
    @ApiModelProperty(value = "Unique internal identifier", example = "06484c9f-6f59-4b9f-ad5e-aaaa0ec332cc")
    @NotNull
    private UUID id;

    @ApiModelProperty(value = "The user who is working on the emailThread.")
    @Valid
    private User user;

    @ApiModelProperty(value = "The queue of the emailThread.")
    @NotNull
    @Valid
    private Queue queue;

    @ApiModelProperty(value = "The status of the emailThread.")
    @NotNull
    private Status status;

    @ApiModelProperty(value = "The emails related to the emailThread.")
    @NotNull
    private List<Email> emails = new ArrayList<>();

}
