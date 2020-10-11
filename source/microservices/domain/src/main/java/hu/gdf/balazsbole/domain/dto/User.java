package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Full DTO for users")
public class User {
    private String username;
    private List<Email> emails = new ArrayList<>();

}
