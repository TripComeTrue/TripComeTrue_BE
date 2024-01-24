package com.haejwo.tripcometrue.domain.member.controller;

import com.haejwo.tripcometrue.domain.member.dto.response.MemberCreatorInfoResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.MemberDetailListItemResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.MemberSearchResultWithContentResponseDto;
import com.haejwo.tripcometrue.domain.member.facade.MemberFacade;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1/members")
@RestController
public class MemberReadSearchController {

    private final MemberFacade memberFacade;

    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseDTO<MemberCreatorInfoResponseDto>> memberCreatorInfo(
        @PathVariable("memberId") Long memberId
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    memberFacade.getCreatorInfo(memberId)
                )
            );
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO<MemberSearchResultWithContentResponseDto>> searchByNicknameResultWithContent(
        @RequestParam("query") String query
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    memberFacade.searchByNicknameResultWithContent(query)
                )
            );
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<SliceResponseDto<MemberDetailListItemResponseDto>>> searchByNicknamePagination(
        @RequestParam("query") String query,
        @PageableDefault(sort = "rating", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    memberFacade.searchByNicknamePagination(query, pageable)
                )
            );
    }
}
