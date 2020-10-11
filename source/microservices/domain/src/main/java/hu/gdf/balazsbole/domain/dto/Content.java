package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Full DTO for content")
public class Content {
    private String body;
    private boolean html;
    private List<Attachment> attachments = new ArrayList<>();

}
