
package com.jia.loanprojectionsservice.infrastructure.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.util.List;

@Data
public class LoanProjectionResponse {

    @JsonProperty("loan_fee_projections")
    private List<LoanProjections> loanFeeProjections;

    @Data
    public static class LoanProjections {

        private String date;
        private long amount;


    }

}
