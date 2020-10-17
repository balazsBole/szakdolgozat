package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Full DTO for users")
public class User {

    @ApiModelProperty(value = "The unique username.")
    @NotNull
    private String username;

    @ApiModelProperty(value = "The user is working on these emailthreads.")
    private List<Emailthread> emailthreads = new ArrayList<>();

}
