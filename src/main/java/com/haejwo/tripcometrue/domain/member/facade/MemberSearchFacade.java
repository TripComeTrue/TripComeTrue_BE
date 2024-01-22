package com.haejwo.tripcometrue.domain.member.facade;

import com.haejwo.tripcometrue.domain.member.dto.response.MemberDetailListItemResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.MemberListItemResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.MemberSearchResultWithContentResponseDto;
import com.haejwo.tripcometrue.domain.member.service.MemberSearchService;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListItemResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleVideoListItemResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordScheduleVideoService;
import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordService;
import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberSearchFacade {

    private final MemberSearchService memberSearchService;
    private final TripRecordScheduleVideoService tripRecordScheduleVideoService;
    private final TripRecordService tripRecordService;

    public MemberSearchResultWithContentResponseDto searchByNicknameResultWithContent(String nickname) {
        List<MemberListItemResponseDto> members = memberSearchService.searchByNickname(nickname);

        List<Long> memberIds = members
            .stream()
            .map(MemberListItemResponseDto::id)
            .toList();

        List<TripRecordScheduleVideoListItemResponseDto> videos = tripRecordScheduleVideoService.getVideosInMemberIds(memberIds);
        List<TripRecordListItemResponseDto> tripRecords = tripRecordService.findTripRecordsWihMemberInMemberIds(memberIds);

        return MemberSearchResultWithContentResponseDto.builder()
            .members(members)
            .videos(videos)
            .tripRecords(tripRecords)
            .build();
    }

    public SliceResponseDto<MemberDetailListItemResponseDto> searchByNicknamePagination(String query, Pageable pageable) {

        SliceResponseDto<MemberListItemResponseDto> sliceResult = memberSearchService.searchByNickname(query, pageable);

        List<Long> memberIds = sliceResult.content()
            .stream()
            .map(MemberListItemResponseDto::id)
            .toList();

        Map<Long, List<TripRecordListItemResponseDto>> tripRecordMap = getGroupByMemberIdTripRecordMap(memberIds);

        Map<Long, List<TripRecordScheduleVideoListItemResponseDto>> videoMap = getGroupByMemberIdVideoMap(memberIds);


        return SliceResponseDto.<MemberDetailListItemResponseDto>builder()
            .content(
                sliceResult.content()
                    .stream()
                    .map(dto -> {
                            int tripRecordTotal = (Objects.isNull(tripRecordMap.get(dto.id()))) ? 0 : tripRecordMap.get(dto.id()).size();
                            int videoTotal = (Objects.isNull(videoMap.get(dto.id()))) ? 0 : videoMap.get(dto.id()).size();

                            return MemberDetailListItemResponseDto.of(dto, tripRecordTotal, videoTotal);
                        }
                    )
                    .toList()
            )
            .totalCount(sliceResult.totalCount())
            .currentPageNum(sliceResult.currentPageNum())
            .pageSize(sliceResult.pageSize())
            .sort(sliceResult.sort())
            .first(sliceResult.first())
            .last(sliceResult.last())
            .build();
    }

    private Map<Long, List<TripRecordScheduleVideoListItemResponseDto>> getGroupByMemberIdVideoMap(List<Long> memberIds) {
        return tripRecordScheduleVideoService.getVideosInMemberIds(memberIds)
            .stream()
            .collect(Collectors.groupingBy(TripRecordScheduleVideoListItemResponseDto::memberId));
    }

    private Map<Long, List<TripRecordListItemResponseDto>> getGroupByMemberIdTripRecordMap(List<Long> memberIds) {
        return tripRecordService.findTripRecordsWihMemberInMemberIds(memberIds)
            .stream()
            .collect(Collectors.groupingBy(TripRecordListItemResponseDto::memberId));
    }
}
