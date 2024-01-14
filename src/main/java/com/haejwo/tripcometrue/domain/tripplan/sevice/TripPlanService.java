package com.haejwo.tripcometrue.domain.tripplan.sevice;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.exception.PlaceNotFoundException;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.store.entity.TripRecordStore;
import com.haejwo.tripcometrue.domain.store.repository.TripRecordStoreRepository;
import com.haejwo.tripcometrue.domain.tripplan.dto.request.TripPlanRequestDto;
import com.haejwo.tripcometrue.domain.tripplan.dto.request.TripPlanScheduleRequestDto;
import com.haejwo.tripcometrue.domain.tripplan.dto.response.CopyTripPlanFromTripRecordResponseDto;
import com.haejwo.tripcometrue.domain.tripplan.dto.response.TripPlanDetailsResponseDto;
import com.haejwo.tripcometrue.domain.tripplan.dto.response.TripPlanScheduleResponseDto;
import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlan;
import com.haejwo.tripcometrue.domain.tripplan.exception.TripPlanNotFoundException;
import com.haejwo.tripcometrue.domain.tripplan.repository.TripPlanRepository;
import com.haejwo.tripcometrue.domain.tripplan.repository.TripPlanScheduleRepository;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule.TripRecordScheduleRepository;
import com.haejwo.tripcometrue.global.exception.PermissionDeniedException;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripPlanService {

    private final TripPlanRepository tripPlanRepository;
    private final TripPlanScheduleRepository tripPlanScheduleRepository;
    private final PlaceRepository placeRepository;
    private final TripRecordStoreRepository tripRecordStoreRepository;
    private final TripRecordRepository tripRecordRepository;
    private final TripRecordScheduleRepository tripRecordScheduleRepository;

    @Transactional
    public void addTripPlan(PrincipalDetails principalDetails, TripPlanRequestDto requestDto) {
        validatePlaceId(requestDto.tripPlanSchedules());
        TripPlan requestTripPlan = requestDto.toEntity(principalDetails.getMember());
        tripPlanRepository.save(requestTripPlan);

        requestDto.tripPlanSchedules().stream()
            .map(tripPlanScheduleRequestDto ->
                tripPlanScheduleRequestDto.toEntity(requestTripPlan))
            .forEach(tripPlanScheduleRepository::save);
    }

    public void deleteTripPlan(PrincipalDetails principalDetails, Long planId) {

        TripPlan tripPlan = tripPlanRepository.findById(planId)
            .orElseThrow(TripPlanNotFoundException::new);

        if (!tripPlan.getMember().getId().equals(principalDetails.getMember().getId())) {
            throw new PermissionDeniedException();
        }

        tripPlanRepository.delete(tripPlan);
    }

    @Transactional
    public void modifyTripPlan(PrincipalDetails principalDetails, Long planId,
        TripPlanRequestDto requestDto) {

        validatePlaceId(requestDto.tripPlanSchedules());
        TripPlan tripPlan = tripPlanRepository.findById(planId)
            .orElseThrow(TripPlanNotFoundException::new);

        if (!tripPlan.getMember().getId().equals(principalDetails.getMember().getId())) {
            throw new PermissionDeniedException();
        }

        tripPlan.update(requestDto);
        tripPlanRepository.save(tripPlan);

        tripPlanScheduleRepository.deleteAllByTripPlanId(tripPlan.getId());
        requestDto.tripPlanSchedules().stream()
            .map(tripPlanScheduleRequestDto ->
                tripPlanScheduleRequestDto.toEntity(tripPlan))
            .forEach(tripPlanScheduleRepository::save);
    }

    public TripPlanDetailsResponseDto getTripPlanDetails(Long planId) {

        TripPlan tripPlan = tripPlanRepository.findById(planId)
            .orElseThrow(TripPlanNotFoundException::new);

        List<TripPlanScheduleResponseDto> responseDtos = tripPlanScheduleRepository
            .findAllByTripPlanId(tripPlan.getId())
            .stream()
            .map(tripPlanSchedule -> {
                Place place = placeRepository.findById(tripPlanSchedule.getPlaceId())
                    .orElseThrow(PlaceNotFoundException::new);
                return TripPlanScheduleResponseDto.fromEntity(tripPlanSchedule, place);
            })
            .collect(Collectors.toList());

        return TripPlanDetailsResponseDto.fromEntity(tripPlan, responseDtos);
    }

    private void validatePlaceId(List<TripPlanScheduleRequestDto> tripPlanSchedules) {
        for (TripPlanScheduleRequestDto tripPlanSchedule : tripPlanSchedules) {
            placeRepository.findById(tripPlanSchedule.placeId())
                .orElseThrow(PlaceNotFoundException::new);
        }
    }

    public CopyTripPlanFromTripRecordResponseDto copyTripPlanFromTripRecord(
        Long tripRecordId,
        PrincipalDetails principalDetails) {

        TripRecordStore tripRecordStore = TripRecordStore
            .builder()
            .member(principalDetails.getMember())
            .tripRecord(tripRecordRepository.findById(tripRecordId)
                .orElseThrow(TripRecordNotFoundException::new))
            .build();

        tripRecordStoreRepository.save(tripRecordStore);

        List<TripPlanScheduleResponseDto> responseDtos = tripRecordScheduleRepository
            .findAllByTripRecordId(tripRecordId)
            .stream()
            .map(tripRecordSchedule -> {
                Place place = placeRepository.findById(tripRecordSchedule.getPlace().getId())
                    .orElseThrow(PlaceNotFoundException::new);
                return TripPlanScheduleResponseDto.fromEntity(tripRecordSchedule, place);
            })
            .toList();

        return CopyTripPlanFromTripRecordResponseDto.fromEntity(
            tripRecordStore.getTripRecord(),
            responseDtos);
    }
}
