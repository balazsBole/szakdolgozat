package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "EmailThread with version number")
public class EmailThreadVersion {

    @ApiModelProperty(value = "Version number of the EmailThread", example = "35")
    @NotNull
    private int version;

    @ApiModelProperty(value = "The emailThread.")
    @Valid
    @NotNull
    private EmailThread emailThread;

}
