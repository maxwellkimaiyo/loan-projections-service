
package com.jia.loanprojections.infrastructure.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanProjectionResponse {

    @JsonProperty("loan_fee_projections")
    private List<LoanProjections> loanFeeProjections;

}
