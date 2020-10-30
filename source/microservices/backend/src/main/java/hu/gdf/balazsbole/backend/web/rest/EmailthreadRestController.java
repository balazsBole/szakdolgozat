package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.EmailthreadService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.domain.enumeration.Status;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Emailthread")
@RestController
@RequestMapping(value = EmailthreadRestController.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class EmailthreadRestController {

    public static final String ROOT_PATH = "/emailthread";

    private final EmailthreadService service;

    public EmailthreadRestController(EmailthreadService service) {
        this.service = service;
    }


    @GetMapping("/unassigned")
    @ApiOperation(nickname = "unassigned", value = "Get all the unassigned emailthreads.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return unassigned emailthreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<List<Emailthread>> unassigned(
            @ApiParam(value = "Pagination page", example = "1", allowableValues = "range[0, infinity]") @RequestParam(name = "page", defaultValue = "0") final int page,
            @ApiParam(value = "Pagination size", example = "1", allowableValues = "range[1, infinity]") @RequestParam(name = "size", defaultValue = "1") final int size
    ) {
        List<Emailthread> threads = service.getUnassignedEmailThreads();
        return ResponseEntity.ok(threads);
    }

    @GetMapping("/assignedToMe")
    @ApiOperation(nickname = "assignedToMeByStatus", value = "Get the emailthreads of the authenticated user with a specific status.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return found emailthreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<List<Emailthread>> assignedToMeByStatus(
            @ApiParam(value = "Emailthread status") @RequestHeader(value = "status", defaultValue = "OPEN") final String status
    ) {
        String keycloakUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Emailthread> threads = service.findAllByStatusAndKeycloakUser(UUID.fromString(keycloakUsername), Status.valueOf(status));
        return ResponseEntity.ok(threads);
    }

    @PatchMapping("/status/{emailThreadId}")
    @ApiOperation(nickname = "changeStatus", value = "Change the status of the emailthread.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return found emailthreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "Email with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<Emailthread> changeStatus(
            @ApiParam(value = "Id of the emailThread") @PathVariable("emailThreadId") final UUID emailThreadId,
            @ApiParam(value = "New status", required = true) @RequestBody final Status status) {
        return ResponseEntity.ok(service.updateStatus(emailThreadId, status));
    }

    @PatchMapping("/user/{emailThreadId}")
    @ApiOperation(nickname = "changeUser", value = "Change the owner of the emailthread.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return found emailthreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "Email with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<Emailthread> changeUser(
            @ApiParam(value = "Id of the emailThread") @PathVariable("emailThreadId") final UUID emailThreadId,
            @ApiParam(value = "New user's id", required = true) @RequestBody final User user) {
        return ResponseEntity.ok(service.updateUser(emailThreadId, user.getId()));
    }

}
