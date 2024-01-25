package com.haejwo.tripcometrue.domain.place.repositroy;

import com.haejwo.tripcometrue.config.TestQuerydslConfig;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Sql(scripts = "classpath:sql/test-data-insert.sql")
@ActiveProfiles("test")
@Import(TestQuerydslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    void findPlacesByCityIdAndPlaceName() {
        // given
        Long cityId = 1L;
        String placeName = "여 행";
        int pageSize = 2;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        // when
        Slice<Place> result = placeRepository.findPlacesByCityIdAndPlaceName(cityId, placeName, pageRequest);

        // then
        assertThat(result.getNumberOfElements()).isEqualTo(pageSize);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();
    }

    @Test
    void findPlacesByCityIdAndPlaceName_nullPlaceName() {
        // given
        Long cityId = 1L;
        String placeName = null;
        int pageSize = 2;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        // when
        Slice<Place> result = placeRepository.findPlacesByCityIdAndPlaceName(cityId, placeName, pageRequest);

        // then
        assertThat(result.getNumberOfElements()).isEqualTo(pageSize);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();
    }

    @Test
    void findPlacesByCityIdAndPlaceName_blankPlaceName() {
        // given
        Long cityId = 1L;
        String placeName = "  ";
        int pageSize = 2;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        // when
        Slice<Place> result = placeRepository.findPlacesByCityIdAndPlaceName(cityId, placeName, pageRequest);

        // then
        assertThat(result.getNumberOfElements()).isEqualTo(pageSize);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();
    }

}