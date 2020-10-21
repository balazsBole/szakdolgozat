package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
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

    @ApiModelProperty(value = "The user is working on these emailthreads.")
    private List<Emailthread> emailthreads = new ArrayList<>();

}
