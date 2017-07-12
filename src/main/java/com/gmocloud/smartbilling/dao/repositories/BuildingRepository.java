package com.gmocloud.smartbilling.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmocloud.smartbilling.dao.entities.BuildingEntity;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity, Integer> {

//    @Query("select b from building b where b.status='A' order by b.building_code")
//    List<BuildingEntity> getBuildingList();

//    @Query("select b from building b where b.status='A' and b.building_code = :id")
//    BuildingEntity findBuilding(@Param("id") String buildingCode );

}
