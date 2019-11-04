package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.FoodMenu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FoodMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {

}
