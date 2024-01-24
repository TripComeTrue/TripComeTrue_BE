package com.haejwo.tripcometrue.domain.member.facade;

import com.haejwo.tripcometrue.config.AbstractContainersSupport;
import com.haejwo.tripcometrue.config.DatabaseCleanUpAfterEach;
import com.haejwo.tripcometrue.domain.member.dto.response.MemberDetailListItemResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.MemberSearchResultWithContentResponseDto;
import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DatabaseCleanUpAfterEach
@Sql(scripts = "classpath:sql/test-data-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
class MemberReadSearchFacadeTest extends AbstractContainersSupport {

    @Autowired
    private MemberReadSearchFacade memberReadSearchFacade;

    @Test
    void searchByNicknameResultWithContent() {
        // given
        String query = "치 앙마이";

        // when
        MemberSearchResultWithContentResponseDto result = memberReadSearchFacade.searchByNicknameResultWithContent(query);

        // then
        assertThat(result.members()).hasSize(2);
        assertThat(result.tripRecords()).hasSize(3);
        assertThat(result.videos()).hasSize(5);
    }

    @Test
    void searchByNicknamePagination() {
        // given
        String query = "치 앙마이";
        int pageNum = 0;
        int pageSize = 1;
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "memberRating"));

        // when
        SliceResponseDto<MemberDetailListItemResponseDto> result = memberReadSearchFacade.searchByNicknamePagination(query, pageRequest);

        // then
        assertThat(result.currentPageNum()).isEqualTo(pageNum);
        assertThat(result.totalCount()).isEqualTo(pageSize);
        assertThat(result.last()).isFalse();
    }

    @Test
    void searchByNicknamePagination_emptyResult() {
        // given
        String query = "없습니다.";
        int pageNum = 0;
        int pageSize = 1;
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "memberRating"));

        // when
        SliceResponseDto<MemberDetailListItemResponseDto> result = memberReadSearchFacade.searchByNicknamePagination(query, pageRequest);

        // then
        assertThat(result.currentPageNum()).isEqualTo(pageNum);
        assertThat(result.totalCount()).isEqualTo(0);
        assertThat(result.last()).isTrue();
    }
}