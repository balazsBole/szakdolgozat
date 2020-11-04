package hu.gdf.balazsbole.domain.dto;

import hu.gdf.balazsbole.domain.enumeration.ChangeType;
import hu.gdf.balazsbole.domain.enumeration.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "Full DTO for thread audit")
public class EmailThreadAudit {

    @ApiModelProperty(value = "Unique internal increasing index", example = "5")
    private int id;

    @ApiModelProperty(value = "Type of the change. ")
    private ChangeType type;

    @ApiModelProperty(value = "Username. ")
    private String user;

    @ApiModelProperty(value = "Queue name.")
    private String queue;

    @ApiModelProperty(value = "Description.")
    private String description;

    @ApiModelProperty(value = "Status.")
    @NotNull
    private Status status;

}
