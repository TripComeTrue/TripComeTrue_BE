package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_image;

import com.haejwo.tripcometrue.config.TestQuerydslConfig;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.entity.CurrencyUnit;
import com.haejwo.tripcometrue.domain.city.repository.CityRepository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.triprecord.dto.query.TripRecordScheduleImageWithPlaceIdQueryDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Import(TestQuerydslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class TripRecordScheduleImageRepositoryTest {

    @Autowired
    private TripRecordScheduleImageRepository tripRecordScheduleImageRepository;

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
                    .name("오사카")
                    .language("일본어")
                    .timeDifference("1시간 느림")
                    .currency(CurrencyUnit.JPY)
                    .visa("visa")
                    .weatherRecommendation("11~12월이 여행하기 가장 좋은 시기입니다.")
                    .weatherDescription("일본어 날씨 설명")
                    .voltage("220V")
                    .country(Country.JAPAN)
                    .build()
            )
        );

        places = new ArrayList<>();

        places.add(
            placeRepository.save(
                Place.builder()
                    .name("방콕 왕궁")
                    .address("여행지 주소")
                    .description("여행지 설명")
                    .storedCount(100)
                    .city(cities.get(0))
                    .build()
            )
        );

        places.add(
            placeRepository.save(
                Place.builder()
                    .name("조드 페어")
                    .address("여행지 주소2")
                    .description("여행지 설명2")
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
                    .name("여행지3")
                    .address("여행지3 주소")
                    .description("여행지3 설명")
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
                .countries("프랑스,영국")
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
                .countries("일본")
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
                .countries("태국")
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

        TripRecordSchedule schedule1 = tripRecordScheduleRepository.save(
            TripRecordSchedule.builder()
                .tripRecord(tripRecord1)
                .place(places.get(0))
                .dayNumber(1)
                .ordering(1)
                .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                .tagUrl("tagUrl")
                .build()
        );

        TripRecordSchedule schedule2 = tripRecordScheduleRepository.save(
            TripRecordSchedule.builder()
                .tripRecord(tripRecord1)
                .place(places.get(1))
                .dayNumber(1)
                .ordering(1)
                .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                .tagUrl("tagUrl")
                .build()
        );

        TripRecordSchedule schedule3 = tripRecordScheduleRepository.save(
            TripRecordSchedule.builder()
                .tripRecord(tripRecord2)
                .place(places.get(2))
                .dayNumber(1)
                .ordering(1)
                .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                .tagUrl("tagUrl")
                .build()
        );

        TripRecordSchedule schedule4 = tripRecordScheduleRepository.save(
            TripRecordSchedule.builder()
                .tripRecord(tripRecord2)
                .place(places.get(3))
                .dayNumber(1)
                .ordering(1)
                .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                .tagUrl("tagUrl")
                .build()
        );

        TripRecordSchedule schedule5 = tripRecordScheduleRepository.save(
            TripRecordSchedule.builder()
                .tripRecord(tripRecord3)
                .place(places.get(0))
                .dayNumber(1)
                .ordering(1)
                .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                .tagUrl("tagUrl")
                .build()
        );

        TripRecordSchedule schedule6 = tripRecordScheduleRepository.save(
            TripRecordSchedule.builder()
                .tripRecord(tripRecord3)
                .place(places.get(1))
                .dayNumber(1)
                .ordering(1)
                .tagType(ExternalLinkTagType.ACCOMMODATION_RESERVATION)
                .tagUrl("tagUrl")
                .build()
        );

        tripRecordScheduleImageRepository.save(
            TripRecordScheduleImage.builder()
                .tripRecordSchedule(schedule1)
                .imageUrl("imageUrl")
                .build()
        );

        tripRecordScheduleImageRepository.save(
            TripRecordScheduleImage.builder()
                .tripRecordSchedule(schedule2)
                .imageUrl("imageUrl")
                .build()
        );

        tripRecordScheduleImageRepository.save(
            TripRecordScheduleImage.builder()
                .tripRecordSchedule(schedule2)
                .imageUrl("imageUrl")
                .build()
        );

        tripRecordScheduleImageRepository.save(
            TripRecordScheduleImage.builder()
                .tripRecordSchedule(schedule3)
                .imageUrl("imageUrl")
                .build()
        );

        tripRecordScheduleImageRepository.save(
            TripRecordScheduleImage.builder()
                .tripRecordSchedule(schedule4)
                .imageUrl("imageUrl")
                .build()
        );

        tripRecordScheduleImageRepository.save(
            TripRecordScheduleImage.builder()
                .tripRecordSchedule(schedule5)
                .imageUrl("imageUrl")
                .build()
        );

        tripRecordScheduleImageRepository.save(
            TripRecordScheduleImage.builder()
                .tripRecordSchedule(schedule6)
                .imageUrl("imageUrl")
                .build()
        );
    }

    @Test
    void findByCityId() {
        //given
        Long cityId = cities.get(0).getId();
        int size = 3;
        PageRequest pageRequest = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "storedCount"));

        //when
        Slice<TripRecordScheduleImage> result = tripRecordScheduleImageRepository.findByCityId(cityId, pageRequest);

        //then
        assertThat(result.getNumberOfElements()).isEqualTo(size);
        assertThat(result.getContent().get(1).getTripRecordSchedule().getTripRecord().getStoreCount())
            .isGreaterThanOrEqualTo(result.getContent().get(0).getTripRecordSchedule().getTripRecord().getStoreCount());
    }


    @Test
    void findByCityIdOrderByCreatedAtDescLimitSize() {
        //given
        Long cityId = cities.get(0).getId();
        int size = 2;

        //when
        List<TripRecordScheduleImage> result = tripRecordScheduleImageRepository.findByCityIdOrderByCreatedAtDescLimitSize(cityId, size);

        //then
        assertThat(result).hasSize(size);
    }


    @Test
    void findInPlaceIdsOrderByCreatedAtDesc() {
        // given
        List<Long> placeIds = places.stream().map(Place::getId).toList();

        // when
        List<TripRecordScheduleImageWithPlaceIdQueryDto> result = tripRecordScheduleImageRepository.findInPlaceIdsOrderByCreatedAtDesc(placeIds);

        // then
        assertThat(result).hasSize(7);
    }

    @AfterEach
    void cleanUp() {
        tripRecordScheduleImageRepository.deleteAll();
        tripRecordScheduleRepository.deleteAll();
        tripRecordRepository.deleteAll();
        memberRepository.deleteAll();
        placeRepository.deleteAll();
        cityRepository.deleteAll();
    }
}