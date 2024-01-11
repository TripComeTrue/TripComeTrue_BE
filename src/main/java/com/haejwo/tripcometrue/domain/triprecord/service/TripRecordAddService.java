package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.city.repository.CityRepository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.CreateSchedulePlaceRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordScheduleRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule.SearchScheduleTripResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleVideo;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordTag;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordImageRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordScheduleImageRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordScheduleRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordScheduleVideoRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordTagRepository;
import com.haejwo.tripcometrue.global.enums.Country;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripRecordAddService {

    private final TripRecordRepository tripRecordRepository;
    private final TripRecordImageRepository tripRecordImageRepository;
    private final TripRecordTagRepository tripRecordTagRepository;
    private final TripRecordScheduleRepository tripRecordScheduleRepository;
    private final TripRecordScheduleImageRepository tripRecordScheduleImageRepository;
    private final TripRecordScheduleVideoRepository tripRecordScheduleVideoRepository;
    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public void addTripRecord(PrincipalDetails principalDetails, TripRecordRequestDto requestDto) {
        TripRecord requestTripRecord = requestDto.toEntity(principalDetails.getMember());
        tripRecordRepository.save(requestTripRecord);

        saveTripRecordImages(requestDto, requestTripRecord);
        saveHashTags(requestDto, requestTripRecord);
        saveTripRecordSchedules(requestDto, requestTripRecord, principalDetails.getMember());
    }

    private void saveTripRecordImages(TripRecordRequestDto requestDto,
        TripRecord requestTripRecord) {
        requestDto.tripRecordImages().forEach(tripRecordImageRequestDto -> {
            TripRecordImage tripRecordImage = tripRecordImageRequestDto.toEntity(requestTripRecord);
            tripRecordImageRepository.save(tripRecordImage);
        });
    }

    private void saveHashTags(TripRecordRequestDto requestDto, TripRecord requestTripRecord) {
        requestDto.hashTags().forEach(hashTag -> {
            TripRecordTag tripRecordTag = TripRecordTag.builder().hashTagType(hashTag)
                .tripRecord(requestTripRecord).build();
            tripRecordTagRepository.save(tripRecordTag);
        });
    }

    private void saveTripRecordSchedules(TripRecordRequestDto requestDto,
        TripRecord requestTripRecord, Member member) {
        requestDto.tripRecordSchedules().forEach(tripRecordScheduleRequestDto -> {
            TripRecordSchedule tripRecordSchedule = tripRecordScheduleRequestDto.toEntity(
                requestTripRecord, member);
            tripRecordScheduleRepository.save(tripRecordSchedule);

            saveTripRecordScheduleImages(tripRecordScheduleRequestDto, tripRecordSchedule);
            saveTripRecordScheduleVideos(tripRecordScheduleRequestDto, tripRecordSchedule);
        });
    }

    private void saveTripRecordScheduleImages(TripRecordScheduleRequestDto requestDto,
        TripRecordSchedule tripRecordSchedule) {
        requestDto.tripRecordScheduleImages().forEach(tripRecordScheduleImageUrl -> {
            TripRecordScheduleImage tripRecordImage = TripRecordScheduleImage.builder()
                .imageUrl(tripRecordScheduleImageUrl).tripRecordSchedule(tripRecordSchedule)
                .build();
            tripRecordScheduleImageRepository.save(tripRecordImage);
        });
    }

    private void saveTripRecordScheduleVideos(TripRecordScheduleRequestDto requestDto,
        TripRecordSchedule tripRecordSchedule) {
        requestDto.tripRecordScheduleVideos().forEach(tripRecordScheduleVideoUrl -> {
            TripRecordScheduleVideo tripRecordScheduleVideo = TripRecordScheduleVideo.builder()
                .videoUrl(tripRecordScheduleVideoUrl).tripRecordSchedule(tripRecordSchedule)
                .build();
            tripRecordScheduleVideoRepository.save(tripRecordScheduleVideo);
        });
    }

    public List<SearchScheduleTripResponseDto> searchSchedulePlace(Country country, String city) {
        return cityRepository.findByNameAndCountry(city, country).map(
            foundCity -> placeRepository.findByCityId(foundCity.getId()) //TODO : 연관관계 추가되면 수정필요
                .stream().map(SearchScheduleTripResponseDto::fromEntity)
                .collect(Collectors.toList())).orElseGet(() -> new ArrayList<>());
    }

    public Long createSchedulePlace(CreateSchedulePlaceRequestDto createSchedulePlaceRequestDto) {
        return placeRepository.save(createSchedulePlaceRequestDto.toEntity()).getId();
    }
}
