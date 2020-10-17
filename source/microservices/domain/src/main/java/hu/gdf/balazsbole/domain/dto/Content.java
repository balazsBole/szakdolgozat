package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Full DTO for content")
public class Content {

    @ApiModelProperty(value = "The body of the email.")
    @NotNull
    private String body;

    @ApiModelProperty(value = "The body should be read as an html document.")
    @NotNull
    private Boolean html;

    @ApiModelProperty(value = "All the attachment the content has.")
    private List<Attachment> attachments = new ArrayList<>();

}
