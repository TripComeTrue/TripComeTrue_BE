package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TripRecordCustomRepository {

    List<TripRecordListResponseDto> finTripRecordWithFilter(Pageable pageable, TripRecordListRequestAttribute request);

    List<TripRecord> findTopTripRecordListDomestic(int size);

    List<TripRecord> findTopTripRecordListOverseas(int size);
}
