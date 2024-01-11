package com.haejwo.tripcometrue.domain.city.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExchangeRateApiDto(
    @JsonProperty("result")
    Integer result,
    @JsonProperty("cur_unit")
    String curUnit,
    @JsonProperty("cur_nm")
    String curName,
    @JsonProperty("deal_bas_r")
    String dealBaseRate
) {
}
