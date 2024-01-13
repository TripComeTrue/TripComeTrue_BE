package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_store;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordStoreRepository extends
    JpaRepository<TripRecordStore, Long> {

}
