package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Data
@ApiModel(description = "Full DTO for attachment")
public class Attachment {

    @ApiModelProperty(value = "The attachment is part of this email content.")
    @NotNull
    private Content content;

    @ApiModelProperty(value = "The binary bytearray of the data.")
    @NotNull
    private byte[] data;

    @ApiModelProperty(value = "The content type of the data.")
    @NotNull
    private String contentType;

    @ApiModelProperty(value = "The name of the data.")
    @NotNull
    private String filename;

    public byte[] getData() {
        return data;
    }

    public ByteArrayOutputStream getDataAsStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length);
        baos.write(data, 0, data.length);
        return baos;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setDataFromStream(ByteArrayInputStream stream) {
        this.data = stream.readAllBytes();
    }

}
