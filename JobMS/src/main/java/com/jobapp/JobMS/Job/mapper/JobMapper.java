package com.jobapp.JobMS.Job.mapper;

import com.jobapp.JobMS.Job.Job;
import com.jobapp.JobMS.Job.dto.JobDTO;
import com.jobapp.JobMS.Job.external.Company;
import com.jobapp.JobMS.Job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO mapToJobWithCompanyDTO(Job job, Company company, List<Review> reviews) {
        JobDTO jobDTO = new JobDTO();

        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}
