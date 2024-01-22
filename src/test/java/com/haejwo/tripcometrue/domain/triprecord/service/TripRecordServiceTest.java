package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.config.AbstractContainersSupport;
import com.haejwo.tripcometrue.config.DatabaseCleanUpAfterEach;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordSearchParamAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListItemResponseDto;
import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Sql(scripts = "classpath:sql/test-data-insert.sql")
@DatabaseCleanUpAfterEach
@SpringBootTest
class TripRecordServiceTest extends AbstractContainersSupport {

    @Autowired
    private TripRecordService tripRecordService;

    @Test
    void findTripRecordList_bySearchParamAttribute_cityName() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 2,
            Sort.by(Sort.Order.desc("averageRating"), Sort.Order.desc("storeCount")));
        String cityName = "방콕";
        TripRecordSearchParamAttribute paramAttribute = new TripRecordSearchParamAttribute(cityName, null, null, null);

        // when
        SliceResponseDto<TripRecordListItemResponseDto> result = tripRecordService.findTripRecordList(paramAttribute, pageRequest);

        // then
        assertThat(result.content()).hasSize(1);
        assertThat(result.content().get(0).tripRecordId()).isEqualTo(1);
        assertThat(result.totalCount()).isEqualTo(1);
        assertThat(result.currentPageNum()).isEqualTo(0);
        assertThat(result.first()).isTrue();
        assertThat(result.last()).isTrue();
    }

    @Test
    void findTripRecordList_bySearchParamAttribute_cityIdTotalDays() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 2,
            Sort.by(Sort.Order.desc("storeCount"), Sort.Order.desc("averageRating")));
        Long cityId = 3L;
        String totalDays = "etc";
        TripRecordSearchParamAttribute paramAttribute = new TripRecordSearchParamAttribute(null, null, cityId, totalDays);

        // when
        SliceResponseDto<TripRecordListItemResponseDto> result = tripRecordService.findTripRecordList(paramAttribute, pageRequest);

        // then
        assertThat(result.content()).hasSize(1);
        assertThat(result.content().get(0).tripRecordId()).isEqualTo(3);
        assertThat(result.totalCount()).isEqualTo(1);
        assertThat(result.currentPageNum()).isEqualTo(0);
        assertThat(result.first()).isTrue();
        assertThat(result.last()).isTrue();
    }

    @Test
    void findTopTripRecordList_requestTypeDomestic() {
        // given
        String type = "domestic";

        // when
        List<TripRecordListItemResponseDto> result = tripRecordService.findTopTripRecordList(type);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void findTopTripRecordList_requestTypeOverseas() {
        // given
        String type = "overseas";

        // when
        List<TripRecordListItemResponseDto> result = tripRecordService.findTopTripRecordList(type);

        // then
        assertThat(result).hasSize(3);
    }
}