package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.AuditService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.dto.EmailThreadAudit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Audit")
@RestController
@RequestMapping(value = AuditRestController.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class AuditRestController {

    public static final String ROOT_PATH = "/audit";

    private final AuditService service;

    public AuditRestController(AuditService service) {
        this.service = service;

    }

    @GetMapping("/email-thread/")
    @ApiOperation(nickname = "emailThreadsRelatedToUser", value = "Get the email threads that is related to the user.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Returns the current state of the email threads, that are related to the user."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<List<EmailThread>> emailThreadsRelatedToUser() {
        String keycloakUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(service.emailThreadsRelatedToUser(UUID.fromString(keycloakUserId)));
    }


    @GetMapping("/email-thread/{emailThreadId}")
    @ApiOperation(nickname = "emailThread", value = "Get history of an email thread by UUID.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Returns history of the email thread."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<List<EmailThreadAudit>> emailThread(
            @PathVariable("emailThreadId") final UUID emailThreadId) {
        return ResponseEntity.ok(service.historyOfEmailThreadBy(emailThreadId));
    }


}
