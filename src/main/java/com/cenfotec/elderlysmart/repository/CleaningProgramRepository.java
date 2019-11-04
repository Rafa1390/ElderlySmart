package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.CleaningProgram;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CleaningProgram entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CleaningProgramRepository extends JpaRepository<CleaningProgram, Long> {

}
