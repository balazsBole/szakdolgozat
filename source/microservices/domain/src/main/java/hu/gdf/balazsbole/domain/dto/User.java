package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@ApiModel(description = "Full DTO for users")
public class User {

    @ApiModelProperty(value = "Username.")
    @NotNull
    private String username;

    @ApiModelProperty(value = "Unique keycloak id.")
    @NotNull
    private UUID keycloakID;

}
