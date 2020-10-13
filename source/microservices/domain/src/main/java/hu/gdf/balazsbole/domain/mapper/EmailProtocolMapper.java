package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import org.apache.commons.mail.util.MimeMessageParser;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.mail.Address;
import javax.mail.MessagingException;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AttachmentProtocolMapper.class})
public interface EmailProtocolMapper {

    @Mapping(source = "mimeMessage.messageID", target = "messageId")
    @Mapping(source = "mimeMessage.receivedDate", target = "processed")
    @Mapping(source = "htmlContent", target = "html")
    @Mapping(source = "attachmentList", target = "attachments")
    EmailProtocolValue mapValue(MimeMessageParser parser) throws Exception;

    @Mapping(source = "mimeMessage.messageID", target = "messageId")
    EmailProtocolKey mapKey(MimeMessageParser parser) throws Exception;

    @AfterMapping
    default void afterMapping(@MappingTarget EmailProtocolValue emailProtocolValue, MimeMessageParser parser) throws MessagingException {
        String emailtext = emailProtocolValue.getHtml() ? parser.getHtmlContent() : parser.getPlainContent();
        emailProtocolValue.setBody(emailtext);

        String[] header = parser.getMimeMessage().getHeader("In-Reply-To");
        String replyTo = header == null ? null : header[0];
        emailProtocolValue.setInReplyTo(replyTo);
    }


    default String getFirstAdressAsString(List<Address> value) {
        return value.isEmpty() ? null : value.get(0).toString();
    }

    default long getTime(java.util.Date date) {
        return date.getTime();
    }
}
