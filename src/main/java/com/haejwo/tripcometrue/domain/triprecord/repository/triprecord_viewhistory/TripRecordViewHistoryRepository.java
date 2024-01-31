package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_viewhistory;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewHistory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRecordViewHistoryRepository
    extends JpaRepository<TripRecordViewHistory, Long>, TripRecordViewHistoryRepositoryCustom {

  Page<TripRecordViewHistory> findByMember(Member member, Pageable pageable);

  Optional<TripRecordViewHistory> findByMemberIdAndTripRecordId(Long memberId, Long tripRecordId);
}

