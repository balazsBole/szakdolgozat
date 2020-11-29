package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.UserService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.User;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Api(tags = "User")
@RestController
@RequestMapping(value = UserRestController.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class UserRestController {

    public static final String ROOT_PATH = "/api/user";

    private final UserService service;

    public UserRestController(final UserService service) {
        this.service = service;
    }

    @GetMapping("/details")
    @ApiOperation(nickname = "authenticatedUserDetails", value = "Get details of the authenticated user.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return authenticated user details."),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "User with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<User> authenticatedUserDetails() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userUUID = UUID.fromString(userId);
        return ResponseEntity.ok(service.getUser(userUUID).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found")));
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

    @PatchMapping("/queue")
    @ApiOperation(nickname = "changeQueue", value = "Change ths active user's queue'.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "New queue of the user has been set"),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "Queue with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<Void> changeQueue(
            @ApiParam(value = "New property", required = true) @RequestBody Map<String, UUID> update) {
        UUID queueId = update.get("queueId");
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userUUID = UUID.fromString(userId);
        service.updateQueueFor(userUUID, queueId);
        return ResponseEntity.ok().build();
    }

}
