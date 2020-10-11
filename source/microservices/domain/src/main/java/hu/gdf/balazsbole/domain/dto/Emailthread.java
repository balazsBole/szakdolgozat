package hu.gdf.balazsbole.domain.dto;

import hu.gdf.balazsbole.domain.enumeration.Status;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Full DTO for thread")
public class Emailthread {
    private User user;
    private Status status;
    private List<Email> emails = new ArrayList<>();

}
