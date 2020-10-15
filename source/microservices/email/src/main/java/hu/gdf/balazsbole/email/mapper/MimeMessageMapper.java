package hu.gdf.balazsbole.email.mapper;

import hu.gdf.balazsbole.domain.mapper.MappingException;
import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.util.MimeMessageParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AttachmentProtocolMapper.class})
public interface MimeMessageMapper {

    @Named("generated")
    @Mapping(source = "mimeMessage.messageID", target = "messageId")
    EmailProtocolKey mapKeyGenerated(MimeMessageParser parser) throws Exception;

    default EmailProtocolKey mapKey(MimeMessage message) {
        try {
            return mapKeyGenerated(new MimeMessageParser(message).parse());
        } catch (Exception e) {
            throw new MappingException(e);
        }
    }

    @Named("generated")
    @Mapping(source = "mimeMessage.messageID", target = "messageId")
    @Mapping(source = "mimeMessage.receivedDate", target = "processed")
    @Mapping(source = "parser", target = "html", qualifiedByName = "isHtml")
    @Mapping(source = "mimeMessage", target = "inReplyTo", qualifiedByName = "inReplyTo")
    @Mapping(source = "parser", target = "body", qualifiedByName = "bodyParser")
    @Mapping(source = "attachmentList", target = "attachments")
    EmailProtocolValue mapValueGenerated(MimeMessageParser parser) throws Exception;

    default EmailProtocolValue mapValue(MimeMessage message) {
        try {
            return mapValueGenerated(new MimeMessageParser(message).parse());
        } catch (Exception e) {
            throw new MappingException(e);
        }
    }

    @Named("bodyParser")
    default String parseBody(MimeMessageParser parser) {
        return parser.hasHtmlContent() ? parser.getHtmlContent() : parser.getPlainContent();
    }

    @Named("isHtml")
    default Boolean isHtml(MimeMessageParser parser) {
        return parser.hasHtmlContent();
    }

    @Named("inReplyTo")
    default String mapInReplyTo(MimeMessage mimeMessage) throws MessagingException {
        String[] header = mimeMessage.getHeader("In-Reply-To");
        return header == null ? null : header[0];
    }

    default String getFirstAddressAsString(List<Address> value) {
        return value.isEmpty() ? null : value.get(0).toString();
    }

    default long getTime(Date date) {
        return date.getTime();
    }

    default MimeMessage map(EmailProtocolValue avroValue, @MappingTarget MimeMessage message) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(avroValue.getTo());
            helper.setFrom(avroValue.getFrom());
            helper.setSubject(avroValue.getSubject());
            helper.setText(avroValue.getBody(), avroValue.getHtml());

            message.setHeader("In-Reply-To", avroValue.getInReplyTo());
            message.setHeader("Message-ID", avroValue.getMessageId());
            message.setSentDate(new Date(avroValue.getProcessed()));

            return message;
        } catch (MessagingException e) {
            throw new MappingException(e);
        }
    }

}
