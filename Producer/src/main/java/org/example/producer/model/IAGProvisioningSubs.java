package org.example.producer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class IAGProvisioningSubs {
    @NonNull
    private String hldr_cust_no;

    private String entr_no;
    private String aceno;
    private String chng_bfr_prod_no;
    private String prod_no;
}
