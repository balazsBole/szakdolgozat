package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.EmailThreadService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.enumeration.Status;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Api(tags = "EmailThread")
@RestController
@RequestMapping(value = EmailThreadRestController.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class EmailThreadRestController {

    public static final String ROOT_PATH = "/email-thread";

    private final EmailThreadService service;

    public EmailThreadRestController(EmailThreadService service) {
        this.service = service;
    }

    @GetMapping("/{emailThreadId}")
    @ApiOperation(nickname = "details", value = "Get EmailThread by UUID.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Returns email."),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "EmailThread with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<EmailThread> details(
            @PathVariable("emailThreadId") final UUID emailThreadId) {
        Optional<EmailThread> emailThreadOptional = service.findById(emailThreadId);
        return ResponseEntity.ok(emailThreadOptional.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found")));
    }

    @GetMapping("/unassigned")
    @ApiOperation(nickname = "unassigned", value = "Get all the unassigned emailThreads.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return unassigned emailThreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<List<EmailThread>> unassigned(
            @ApiParam(value = "Pagination page", example = "1", allowableValues = "range[0, infinity]") @RequestParam(name = "page", defaultValue = "0") final int page,
            @ApiParam(value = "Pagination size", example = "1", allowableValues = "range[1, infinity]") @RequestParam(name = "size", defaultValue = "1") final int size
    ) {
        List<EmailThread> threads = service.getUnassignedEmailThreads();
        return ResponseEntity.ok(threads);
    }

    @GetMapping("/assigned-to-me")
    @ApiOperation(nickname = "assignedToMeByStatus", value = "Get the emailThreads of the authenticated user with a specific status.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return found emailThreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<List<EmailThread>> assignedToMeByStatus(
            @ApiParam(value = "EmailThread status") @RequestHeader(value = "status", defaultValue = "OPEN") final String status
    ) {
        String keycloakUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        List<EmailThread> threads = service.findAllByStatusAndKeycloakUser(UUID.fromString(keycloakUsername), Status.valueOf(status));
        return ResponseEntity.ok(threads);
    }

    @PatchMapping("/{emailThreadId}")
    @ApiOperation(nickname = "patch", value = "Change the owner, or the status of the emailThread.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "New fileds of the emailThread has been set"),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "EmailThread with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<Void> patch(
            @ApiParam(value = "Id of the emailThread") @PathVariable("emailThreadId") final UUID emailThreadId,
            @ApiParam(value = "New properties", required = true) @RequestBody Map<String, String> update) {
        String status = update.get("status");
        String userId = update.get("userId");
        if (StringUtils.isNotBlank(status)) {
            service.updateStatus(emailThreadId, Status.valueOf(status));
        }
        if (StringUtils.isNotBlank(userId))
            service.updateUser(emailThreadId, UUID.fromString(userId));

        return ResponseEntity.ok().build();
    }

}
