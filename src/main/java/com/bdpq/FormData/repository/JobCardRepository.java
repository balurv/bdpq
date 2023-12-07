package com.bdpq.FormData.repository;

import com.bdpq.FormData.model.JobCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCardRepository extends JpaRepository<JobCard, Long> {

    @Query("SELECT jc FROM JobCard jc WHERE jc.jobStatus = 0")
    List<JobCard> getOpenJobCards();
    @Query("SELECT j FROM JobCard j JOIN j.farmers f WHERE f.id = :farmerId")
    List<JobCard> getJobCard(@Param("farmerId") Long farmerId);

}
