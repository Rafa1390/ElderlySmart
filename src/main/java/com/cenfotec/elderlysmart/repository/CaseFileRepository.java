package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.CaseFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CaseFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaseFileRepository extends JpaRepository<CaseFile, Long> {

}
