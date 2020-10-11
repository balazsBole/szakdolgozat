package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "Full DTO for header")
public class Header {
    private String messageId;
    private String inReplyTo;
    private String from;
    private String to;
    private String subject;

}
