package org.example.producer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.util.Objects;

@Data
public class IAGProvisioningReqeuestJSON {
    @NonNull
    private IAGProvisioningSubs subs;
    @NonNull
    private IAGProvisioningDevice[] devices;
    @NonNull
    private String event_code;
    @NonNull
    private String iag_event_code;

}
