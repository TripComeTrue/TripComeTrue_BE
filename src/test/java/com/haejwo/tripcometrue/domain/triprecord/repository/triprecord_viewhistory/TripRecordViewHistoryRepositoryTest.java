package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_viewhistory;

import com.haejwo.tripcometrue.config.TestQuerydslConfig;
import com.haejwo.tripcometrue.domain.triprecord.dto.query.TripRecordViewHistoryGroupByQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Sql(scripts = "classpath:sql/test-data-insert.sql")
@ActiveProfiles("test")
@Import(TestQuerydslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class TripRecordViewHistoryRepositoryTest {

    @Autowired
    private TripRecordViewHistoryRepository tripRecordViewHistoryRepository;


    @Test
    void findTopViewedMembers() {
        // given
        LocalDateTime start = LocalDateTime.of(2024, 1, 21, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 23, 23, 59, 59);
        int size = 5;

        // when
        List<TripRecordViewHistoryGroupByQueryDto> result = tripRecordViewHistoryRepository.findTopListMembers(start, end, size);

        // then
        for (TripRecordViewHistoryGroupByQueryDto tripRecordViewHistoryGroupByQueryDto : result) {
            log.info("{}", tripRecordViewHistoryGroupByQueryDto);
        }
        assertThat(result).hasSize(2);
        assertThat(result.get(0).memberId()).isEqualTo(1);
    }
}