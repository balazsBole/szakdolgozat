package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.UserService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "User")
@RestController
@RequestMapping(value = UserRestController.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class UserRestController {

    public static final String ROOT_PATH = "/user";

    private final UserService service;

    public UserRestController(final UserService service) {
        this.service = service;
    }


    @GetMapping("/details")
    @ApiOperation(nickname = "authenticatedUserDetails", value = "Get details of the authenticated user.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return authenticated user details."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<User> authenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = service.getUserFrom(authentication);
        return ResponseEntity.ok(user);
    }

}