package org.example.consumer.model;

import lombok.Data;

@Data
public class IAGProvisioningDevice {
    private String item_id;
    private String manf_serial_no;
    private String prev_manf_serial_no;
    private String dev_esn;
    private String iccid;
}
