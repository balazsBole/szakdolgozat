package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Content;
import hu.gdf.balazsbole.domain.dto.Header;
import org.apache.commons.mail.util.MimeMessageParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.mail.Address;
import javax.mail.MessagingException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HeaderMapper {

    @Mapping(source = "mimeMessage.messageID", target = "messageId")
    Header map(MimeMessageParser parser) throws Exception;

    default String map(List<Address> value) {
        return value.isEmpty() ? null : value.get(0).toString();
    }

    default void afterMapping(@MappingTarget Header header, MimeMessageParser parser) throws MessagingException {
        String[] headerArray = parser.getMimeMessage().getHeader("In-Reply-To");
        String replyTo = headerArray == null ? null: headerArray[0];
        header.setInReplyTo(replyTo);
    }



}
