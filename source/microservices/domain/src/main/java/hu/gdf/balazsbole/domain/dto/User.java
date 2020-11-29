package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@ApiModel(description = "Full DTO for users")
public class User {

    @ApiModelProperty(value = "Unique internal identifier", example = "06484c9f-6f59-4b9f-ad5e-aaaa0ec332cc")
    private UUID id;

    @ApiModelProperty(value = "Username.")
    @NotNull
    private String username;

    @ApiModelProperty(value = "The email address of the user.")
    @NotNull
    @Email
    private String email;

    @ApiModelProperty(value = "The user can operate on this queue.")
    @NotNull
    @Valid
    private Queue queue;

}
