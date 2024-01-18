package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.haejwo.tripcometrue.global.enums.Continent;
import com.haejwo.tripcometrue.global.enums.Country;
import java.util.List;

public record GetCountryResponseDto(
    Continent continent,
    Country country,
    String countryName,
    String countryImageUrl,
    List<GetCountryCityResponseDto> cityList
) {

}
