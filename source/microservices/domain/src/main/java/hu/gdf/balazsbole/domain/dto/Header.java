package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "Full DTO for header")
public class Header {

    @ApiModelProperty(value = "The globally unique identifier (messageID) of the corresponding email. See rfc5322.")
    @NotNull
    private String messageId;

    @ApiModelProperty(value = "The messageID of the previous email. See rfc5322.")
    @NotNull
    private String inReplyTo;

    @ApiModelProperty(value = "The email received from this address.")
    @NotNull
    @Email
    private String from;

    @ApiModelProperty(value = "The email sent to this address.")
    @NotNull
    @Email
    private String to;

    @ApiModelProperty(value = "The subject of the mail.")
    @NotNull
    private String subject;

}
