package com.haejwo.tripcometrue.global.springsecurity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationAuditAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication =
            SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null ||
            !authentication.isAuthenticated() ||
            authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        Member memberPrincipal = ((PrincipalDetails) authentication.getPrincipal()).getMember();
        return Optional.ofNullable(memberPrincipal.getId());
    }
}
