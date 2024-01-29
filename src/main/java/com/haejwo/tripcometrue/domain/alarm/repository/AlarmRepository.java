package com.haejwo.tripcometrue.domain.alarm.repository;

import com.haejwo.tripcometrue.domain.alarm.entity.Alarm;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

  Page<Alarm> findAllByToMember(Member member, Pageable pageable);
}
