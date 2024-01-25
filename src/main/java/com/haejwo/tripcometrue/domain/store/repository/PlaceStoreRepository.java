package com.haejwo.tripcometrue.domain.store.repository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.store.entity.PlaceStore;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceStoreRepository extends JpaRepository<PlaceStore, Long> {

  Optional<PlaceStore> findByMemberAndPlace(Member member, Place place);

  Optional<PlaceStore> findByMemberIdAndPlaceId(Long memberId, Long placeId);

  Page<PlaceStore> findByMember(Member member, Pageable pageable);

  Long countByPlace(Place place);

  Boolean existsByMemberAndPlace(Member member, Place place);

}
