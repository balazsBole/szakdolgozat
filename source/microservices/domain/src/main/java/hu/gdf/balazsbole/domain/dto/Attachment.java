package hu.gdf.balazsbole.domain.dto;

import com.sun.xml.bind.v2.util.ByteArrayOutputStreamEx;
import hu.gdf.balazsbole.domain.entity.ContentEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Data
@ApiModel(description = "Full DTO for attachment")
public class Attachment {
    private ContentEntity contentEntity;
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
