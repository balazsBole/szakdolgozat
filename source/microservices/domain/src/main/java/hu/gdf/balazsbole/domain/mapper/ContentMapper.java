package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Content;
import org.apache.commons.mail.util.MimeMessageParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.mail.MessagingException;

@Mapper(componentModel = "spring", uses = {AtachmentMapper.class})
public interface ContentMapper {

    @Mapping(source = "attachmentList", target = "attachments")
    @Mapping(source = "htmlContent", target = "html")
    Content mapReceived(MimeMessageParser parser) throws MessagingException;

    default void afterMapping(@MappingTarget Content content, MimeMessageParser parser) {
        String emailtext = content.isHtml() ? parser.getHtmlContent() : parser.getPlainContent();
        content.setBody(emailtext);
    }

}
