package org.example.producer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IAGProvisioningDevice {
    @JsonProperty("item_id")
    private String itemId;

    @JsonProperty("manf_serial_no")
    private String manfSerialNo;

    @JsonProperty("prev_manf_serial_no")
    private String prevManfSerialNo;

    @JsonProperty("dev_esn")
    private String devEsn;

    @JsonProperty("iccid")
    private String iccid;
}
