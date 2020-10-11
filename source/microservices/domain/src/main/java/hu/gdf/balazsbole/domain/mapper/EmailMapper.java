package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.mail.Address;
import javax.mail.MessagingException;
import java.util.List;

@Mapper(componentModel = "spring", uses = {HeaderMapper.class, ContentMapper.class})
public interface EmailMapper {

    Email map(EmailEntity entity);

    EmailEntity map(Email entity);

    @Mapping(constant = "false", target = "read")
    @Mapping(constant = "IN", target = "direction")
    @Mapping(source = "mimeMessage.receivedDate", target = "processed")
    @Mapping(source = "parser", target = "content")
    @Mapping(source = "parser", target = "header")
    Email mapReceived(MimeMessageParser parser) throws MessagingException;


}
