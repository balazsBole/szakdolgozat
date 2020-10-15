package hu.gdf.balazsbole.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Data
@ApiModel(description = "Full DTO for attachment")
public class Attachment {
    private Content content;
    private byte[] data;
    private String contentType;
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
