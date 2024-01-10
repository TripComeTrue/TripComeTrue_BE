package com.haejwo.tripcometrue.domain.store.repository;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.store.entity.CityStore;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityStoreRepository extends JpaRepository<CityStore, Long> {

  Optional<CityStore> findByMemberAndCity(Member member, City city);

  Optional<CityStore> findByMemberIdAndCityId(Long memberId, Long cityId);

  Page<CityStore> findByMember(Member member, Pageable pageable);
}
