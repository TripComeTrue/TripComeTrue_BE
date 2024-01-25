package com.haejwo.tripcometrue.domain.store.repository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.store.entity.TripRecordStore;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRecordStoreRepository extends JpaRepository <TripRecordStore, Long> {
  Optional<TripRecordStore> findByMemberAndTripRecord(Member member, TripRecord tripRecord);

  Optional<TripRecordStore> findByMemberIdAndTripRecordId(Long memberId, Long tripRecordId);

  Page<TripRecordStore> findByMember(Member member, Pageable pageable);

  Long countByTripRecord(TripRecord tripRecord);

  Boolean existsByMemberAndTripRecord(Member member, TripRecord tripRecord);

}
