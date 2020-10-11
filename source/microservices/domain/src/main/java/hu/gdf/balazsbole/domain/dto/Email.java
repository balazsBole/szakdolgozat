package hu.gdf.balazsbole.domain.dto;

import hu.gdf.balazsbole.domain.enumeration.Direction;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "Full DTO for email")
public class Email {

    private Email parent;
    private Emailthread emailthread;
    private Direction direction;
    private Header header;
    private Content content;
    private boolean read;
    private LocalDateTime processed;

}
