package hu.gdf.balazsbole.backend.rest;

import hu.gdf.balazsbole.backend.service.EmailthreadService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.enumeration.Status;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = EmailthreadRestController.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class EmailthreadRestController {

    public static final String ROOT_PATH = "/emailthread";

    private final EmailthreadService service;

    public EmailthreadRestController(EmailthreadService service) {
        this.service = service;
    }


    @GetMapping("/unassigned")
    @ApiOperation(nickname = "EmailthreadRestController#getUnassignedThreads", value = "Get all SSI the unassigned emailthreads.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return unassigned emailthreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<?> unassigned(
            @ApiParam(value = "Pagination page", example = "1", allowableValues = "range[0, infinity]") @RequestParam(name = "page", defaultValue = "0") final int page,
            @ApiParam(value = "Pagination size", example = "1", allowableValues = "range[1, infinity]") @RequestParam(name = "size", defaultValue = "1") final int size
    ) {
        List<Emailthread> threads = service.getUnassignedEmailThreads();
        return ResponseEntity.ok(threads);
    }

    @GetMapping("/{userid}}")
    @ApiOperation(nickname = "EmailthreadRestController#getUnassignedThreads", value = "Get all SSI the unassigned emailthreads.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Return unassigned emailthreads."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<?> findAllByStatusAndUser(
            @ApiParam(value = "User identifier", required = true) @PathVariable("userid") final UUID userId,
            @ApiParam(value = "Emailthread status") @RequestHeader(value = "status", defaultValue = "OPEN") final String status,
            @ApiParam(value = "Pagination page", example = "1", allowableValues = "range[0, infinity]") @RequestParam(name = "page", defaultValue = "0") final int page,
            @ApiParam(value = "Pagination size", example = "1", allowableValues = "range[1, infinity]") @RequestParam(name = "size", defaultValue = "1") final int size
    ) {
        List<Emailthread> threads = service.findAllByStatusAndUser(userId, Status.valueOf(status));
        return ResponseEntity.ok(threads);
    }

}