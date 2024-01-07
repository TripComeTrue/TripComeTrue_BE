package com.haejwo.tripcometrue.domain.member.repository;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberBaseEmail(String email);
    Optional<Member> findByMemberBaseEmailAndProvider(String email,String provider);

}
