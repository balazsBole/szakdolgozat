package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.UserService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.User;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping(value = "/search/autocomplete")
    @ApiOperation(nickname = "autocomplete", value = "AutoComplete search for User. Searches for username with like.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "First (size) count values about BIC field."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
            @ApiResponse(code = DomainConstants.HttpStatus.UNPROCESSABLE_ENTITY, message = "Operation not permitted."),
    })
    public List<User> searchAutoComplete(
            @ApiParam(value = "Username, min length 1", example = "testUser") @RequestParam(name = "username", defaultValue = "") final String username,
            @ApiParam(value = "Queue id", example = "06484c9f-6f59-4b9f-ad5e-aaaa0ec332cc") @RequestParam(name = "queueId") final UUID queueId) {
        return service.searchAutoComplete(queueId, username);
    }
}
