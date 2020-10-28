package hu.gdf.balazsbole.domain.dto;

import hu.gdf.balazsbole.domain.enumeration.Direction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ApiModel(description = "Full DTO for email")
public class Email {

    @ApiModelProperty(value = "Unique internal identifier", example = "06484c9f-6f59-4b9f-ad5e-aaaa0ec332cc")
    @NotNull
    private UUID id;

    @ApiModelProperty(value = "Reply to this email.")
    private UUID parentId;

    @ApiModelProperty(value = "The emailthread which contains this email.")
    @NotNull
    private Emailthread emailthread;

    @ApiModelProperty(value = "The direction of the email.")
    private Direction direction;

    @ApiModelProperty(value = "The header of the email.")
    @Valid
    private Header header;

    @ApiModelProperty(value = "The content of the email.")
    @Valid
    private Content content;

    @ApiModelProperty(value = "The email has been read.")
    @Valid
    private boolean read;

    @ApiModelProperty(value = "Processed at.")
    private LocalDateTime processed;

}
