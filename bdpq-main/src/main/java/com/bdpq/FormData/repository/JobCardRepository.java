package com.bdpq.FormData.repository;

import com.bdpq.FormData.model.JobCard;
import com.bdpq.FormData.model.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCardRepository extends JpaRepository<JobCard, Long> {
    @Query("SELECT j FROM JobCard j JOIN j.farmers f WHERE f.id = :farmerId")
    List<JobCard> getJobCard(@Param("farmerId") Long farmerId);

    @Query("SELECT jc FROM JobCard jc WHERE jc.jobStatus = :statusId")
    List<JobCard> getJobCardsByStatus(@Param("statusId") JobStatus statusId);

    @Query("SELECT j FROM JobCard j JOIN j.farmers f WHERE f.id = :farmerId and j.jobStatus = :statusId")
    List<JobCard> getJobCard(@Param("farmerId") Long farmerId,@Param("statusId") JobStatus statusId);
}
