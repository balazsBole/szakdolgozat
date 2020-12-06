package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.AuditService;
import hu.gdf.balazsbole.backend.service.EmailThreadService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.dto.EmailThreadVersion;
import hu.gdf.balazsbole.domain.enumeration.Status;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.HeaderParam;
import java.util.List;
import java.util.UUID;

@Api(tags = "EmailThread")
@RestController
@RequestMapping(value = EmailThreadRestController.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class EmailThreadRestController {

    public static final String ROOT_PATH = "/api/email-thread";

    private final EmailThreadService service;
    private final AuditService auditService;

    public EmailThreadRestController(EmailThreadService service, AuditService auditService) {
        this.service = service;
        this.auditService = auditService;
    }

    @GetMapping("/{emailThreadId}")
    @ApiOperation(nickname = "emailThreadDetails", value = "Get EmailThread by UUID.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Returns email.",
                    responseHeaders = {@ResponseHeader(name = "ETag", response = String.class, description = "The version of the EmailThread")}),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "EmailThread with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<EmailThreadVersion> details(@PathVariable("emailThreadId") final UUID emailThreadId) {
        EmailThreadVersion threadVersion = new EmailThreadVersion();
        threadVersion.setEmailThread(service.findById(emailThreadId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found")));
        threadVersion.setVersion(auditService.getLatestVersionFromEmailThread(emailThreadId));
        return ResponseEntity.ok().eTag(String.valueOf(threadVersion.getVersion())).body(threadVersion);
    }

    @GetMapping("/unassigned")
    @ApiOperation(nickname = "unassignedFromTheQueue", value = "Get all the unassigned emailThreads from the users queue.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return unassigned emailThreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<List<EmailThread>> unassigned() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<EmailThread> threads = service.getUnassignedEmailThreadsFor(UUID.fromString(userId));
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
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        List<EmailThread> threads = service.findAllByStatusAndUserId(UUID.fromString(userId), Status.valueOf(status));
        return ResponseEntity.ok(threads);
    }

    @GetMapping("/status")
    @ApiOperation(nickname = "searchByStatusInAssignedQueue", value = "Get the emailThreads of the authenticated user's queue with a specific status.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return found emailThreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<List<EmailThread>> searchByStatusInAssignedQueue(
            @ApiParam(value = "EmailThread status") @RequestHeader(value = "status", defaultValue = "CHANGE_QUEUE") final String status
    ) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<EmailThread> threads = service.findAllByStatusInTheQueueOf(Status.valueOf(status), UUID.fromString(userId));
        return ResponseEntity.ok(threads);
    }

    @PatchMapping("/{emailThreadId}")
    @ApiOperation(nickname = "patch", value = "Change the owner, or the status of the emailThread.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "New fileds of the emailThread has been set"),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "EmailThread with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
            @ApiResponse(code = DomainConstants.HttpStatus.CONFLICT, message = "EmailThread concurrently changed"),
    })
    public ResponseEntity<Void> patch(
            @ApiParam(value = "Id of the emailThread") @PathVariable("emailThreadId") final UUID emailThreadId,
            @ApiParam(value = "EmailThread containing the new properties", required = true) @RequestBody EmailThread emailThread,
            @ApiParam(value = "If-Match header", required = true) @HeaderParam("If-Match") String ifMatch) {

        String latestVersion = String.valueOf(auditService.getLatestVersionFromEmailThread(emailThreadId));
        if (!latestVersion.equals(ifMatch))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "resource concurrently changed");

        service.update(emailThreadId, emailThread);

        return ResponseEntity.ok().build();
    }

}
