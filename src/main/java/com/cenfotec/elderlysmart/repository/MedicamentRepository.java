package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Medicament;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Medicament entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

}
