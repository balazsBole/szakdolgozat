package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.kafka.EmailKafkaProducer;
import hu.gdf.balazsbole.backend.service.EmailService;
import hu.gdf.balazsbole.domain.DomainConstants;
import hu.gdf.balazsbole.domain.dto.Email;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Api(tags = "Email")
@RestController
@RequestMapping(value = EmailRestController.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class EmailRestController {

    public static final String ROOT_PATH = "/email";

    private final EmailService service;
    private final EmailKafkaProducer kafkaProducer;


    public EmailRestController(EmailService service, EmailKafkaProducer kafkaProducer) {
        this.service = service;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/{emailId}")
    @ApiOperation(nickname = "details", value = "Get Email by UUID.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Returns email."),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "Email with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<Email> details(
            @PathVariable("emailId") final UUID emailId
    ) {
        Optional<Email> emailOptional = service.findById(emailId);
        return ResponseEntity.ok(emailOptional.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found")));
    }

    @PostMapping("/send")
    @ApiOperation(nickname = "send", value = "Send an Email.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "Returns email."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<Email> send(@ApiParam(value = "Email to send", required = true) @RequestBody final Email email) {
        Email prepared = service.prepareForSending(email);
        kafkaProducer.sendMessage(prepared);
        Email saved = service.storeNew(prepared);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{emailId}")
    @ApiOperation(nickname = "changeRead", value = "Change ths status of the email's read property'.")
    @ApiResponses({
            @ApiResponse(code = DomainConstants.HttpStatus.OK, message = "New status of the email has been set"),
            @ApiResponse(code = DomainConstants.HttpStatus.NOT_FOUND, message = "Email with the given ID does not exists."),
            @ApiResponse(code = DomainConstants.HttpStatus.FORBIDDEN, message = "User not authorized."),
    })
    public ResponseEntity<Void> changeRead(
            @PathVariable("emailId") final UUID emailId,
            @ApiParam(value = "Email has been read", required = true) @RequestBody final boolean read) {
        service.changeReadStatus(emailId, read);
        return ResponseEntity.ok().build();
    }


}
