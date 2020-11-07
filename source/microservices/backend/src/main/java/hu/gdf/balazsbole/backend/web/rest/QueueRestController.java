package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.QueueService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.Queue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Queue")
@RestController
@RequestMapping(value = QueueRestController.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class QueueRestController {

    public static final String ROOT_PATH = "/api/queue";

    private final QueueService service;

    public QueueRestController(QueueService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @ApiOperation(nickname = "getAll", value = "Get all queue.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Returns queues."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<List<Queue>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
