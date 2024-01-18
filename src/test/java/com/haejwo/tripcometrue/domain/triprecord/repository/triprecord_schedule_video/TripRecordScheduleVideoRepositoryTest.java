package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_video;

import com.haejwo.tripcometrue.config.TestQuerydslConfig;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.entity.CurrencyUnit;
import com.haejwo.tripcometrue.domain.city.repository.CityRepository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.triprecord.dto.query.NewestTripRecordScheduleVideoQueryDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleVideo;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule.TripRecordScheduleRepository;
import com.haejwo.tripcometrue.global.enums.Country;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Import(TestQuerydslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class TripRecordScheduleVideoRepositoryTest {

    @Autowired
    private TripRecordScheduleVideoRepository tripRecordScheduleVideoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private TripRecordRepository tripRecordRepository;

    @Autowired
    private TripRecordScheduleRepository tripRecordScheduleRepository;

    private List<City> cities;
    private List<Place> places;

    @BeforeEach
    void setUp() {

        cities = new ArrayList<>();
        cities.add(
            cityRepository.save(
                City.builder()
                    .name("방콕")
                    .language("태국어")
                    .timeDifference("2시간 느림")
                    .currency(CurrencyUnit.THB)
                    .visa("visa")
                    .weatherRecommendation("11~12월이 여행하기 가장 좋은 시기입니다.")
                    .weatherDescription("방콕 날씨 설명")
                    .voltage("220V")
                    .country(Country.THAILAND)
                    .build()
            )
        );

        cities.add(
            cityRepository.save(
                City.builder()
                    .name("부산")
                    .language("한국어")
                    .timeDifference("시차없음")
                    .weatherRecommendation("11~12월이 여행하기 가장 좋은 시기입니다.")
                    .weatherDescription("일본어 날씨 설명")
                    .country(Country.KOREA)
                    .build()
            )
        );

        places = new ArrayList<>();

        places.add(
            placeRepository.save(
                Place.builder()
                    .name("여행지1")
                    .address("여행지1 주소")
                    .description("여행지 설명")
                    .storedCount(100)
                    .city(cities.get(0))
                    .build()
            )
        );

        places.add(
            placeRepository.save(
                Place.builder()
                    .name("여행지2")
                    .address("여행지2 주소")
                    .description("여행지2 설명")
                    .storedCount(150)
                    .city(cities.get(0))
                    .build()
            )
        );

        places.add(
            placeRepository.save(
                Place.builder()
                    .name("여행지3")
                    .address("여행지3 주소")
                    .description("여행지3 설명")
                    .storedCount(115)
                    .city(cities.get(1))
                    .build()
            )
        );

        places.add(
            placeRepository.save(
                Place.builder()
                    .name("여행지4")
                    .address("여행지4 주소")
                    .description("여행지4 설명")
                    .storedCount(200)
                    .city(cities.get(1))
                    .build()
            )
        );

        TripRecord tripRecord1 = tripRecordRepository.save(
            TripRecord.builder()
                .averageRating(4)
                .title("여행 후기 제목")
                .content("여행 후기")
                .countries("THAILAND")
                .storeCount(100)
                .expenseRangeType(ExpenseRangeType.ABOVE_300)
                .member(memberRepository.save(
                    Member.builder()
                        .authority("ROLE_USER")
                        .email("member@email.com")
                        .password("password")
                        .nickname("member")
                        .build()
                ))
                .build()
        );

        TripRecord tripRecord2 = tripRecordRepository.save(
            TripRecord.builder()
                .averageRating(4)
                .title("여행 후기 제목")
                .content("여행 후기")
                .countries("JAPAN")
                .storeCount(200)
                .expenseRangeType(ExpenseRangeType.BELOW_100)
                .member(memberRepository.save(
                    Member.builder()
                        .authority("ROLE_USER")
                        .email("member@email.com")
                        .password("password")
                        .nickname("member")
                        .build()
                ))
                .build()
        );

        TripRecord tripRecord3 = tripRecordRepository.save(
            TripRecord.builder()
                .averageRating(4)
                .title("여행 후기 제목")
                .content("여행 후기")
                .countries("KOREA")
                .storeCount(150)
                .expenseRangeType(ExpenseRangeType.BELOW_100)
                .member(memberRepository.save(
                    Member.builder()
                        .authority("ROLE_USER")
                        .email("member@email.com")
                        .password("password")
                        .nickname("member")
                        .build()
                ))
                .build()
        );

        List<TripRecordSchedule> schedules = new ArrayList<>();

        schedules.add(
            tripRecordScheduleRepository.save(
                TripRecordSchedule.builder()
                    .tripRecord(tripRecord1)
                    .place(places.get(0))
                    .dayNumber(1)
                    .ordering(1)
                    .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                    .tagUrl("tagUrl")
                    .build()
            )
        );

        schedules.add(
            tripRecordScheduleRepository.save(
                TripRecordSchedule.builder()
                    .tripRecord(tripRecord1)
                    .place(places.get(1))
                    .dayNumber(1)
                    .ordering(1)
                    .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                    .tagUrl("tagUrl")
                    .build()
            )
        );

        schedules.add(
            tripRecordScheduleRepository.save(
                TripRecordSchedule.builder()
                    .tripRecord(tripRecord2)
                    .place(places.get(2))
                    .dayNumber(1)
                    .ordering(1)
                    .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                    .tagUrl("tagUrl")
                    .build()
            )
        );

        schedules.add(
            tripRecordScheduleRepository.save(
                TripRecordSchedule.builder()
                    .tripRecord(tripRecord2)
                    .place(places.get(3))
                    .dayNumber(1)
                    .ordering(1)
                    .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                    .tagUrl("tagUrl")
                    .build()
            )
        );

        schedules.add(
            tripRecordScheduleRepository.save(
                TripRecordSchedule.builder()
                    .tripRecord(tripRecord3)
                    .place(places.get(0))
                    .dayNumber(1)
                    .ordering(1)
                    .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                    .tagUrl("tagUrl")
                    .build()
            )
        );

        schedules.add(
            tripRecordScheduleRepository.save(
                TripRecordSchedule.builder()
                    .tripRecord(tripRecord3)
                    .place(places.get(1))
                    .dayNumber(1)
                    .ordering(1)
                    .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                    .tagUrl("tagUrl")
                    .build()
            )
        );

        for (int i = 0; i < schedules.size(); i++) {
            tripRecordScheduleVideoRepository.save(
                TripRecordScheduleVideo.builder()
                    .tripRecordSchedule(schedules.get(i))
                    .thumbnailUrl("thumbnail" + i)
                    .videoUrl("videoUrl" + i)
                    .build()
            );
        }
    }

    @Test
    void findNewestVideoList() {
        //given
        int size = 5;

        //when
        List<NewestTripRecordScheduleVideoQueryDto> result = tripRecordScheduleVideoRepository.findNewestVideoList(size);

        //then
        assertThat(result).hasSize(size);
        assertThat(result.get(0).memberName()).isEqualTo("member");
        assertThat(result.get(0).thumbnailUrl()).isEqualTo("thumbnail5");
    }

    @Test
    void findNewestVideoListDomestic() {
        //given
        int size = 5;

        //when
        List<NewestTripRecordScheduleVideoQueryDto> result = tripRecordScheduleVideoRepository.findNewestVideoListDomestic(size);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    void findNewestVideoListOverseas() {
        //given
        int size = 5;

        //when
        List<NewestTripRecordScheduleVideoQueryDto> result = tripRecordScheduleVideoRepository.findNewestVideoListOverseas(size);

        //then
        assertThat(result).hasSize(4);
    }

    @AfterEach
    void cleanUp() {
        tripRecordScheduleVideoRepository.deleteAll();
        tripRecordScheduleRepository.deleteAll();
        tripRecordRepository.deleteAll();
        memberRepository.deleteAll();
        placeRepository.deleteAll();
        cityRepository.deleteAll();
    }
}