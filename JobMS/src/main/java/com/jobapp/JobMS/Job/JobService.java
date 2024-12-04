package com.jobapp.JobMS.Job;

import com.jobapp.JobMS.Job.dto.JobDTO;

import java.util.List;

public interface JobService {

    public List<JobDTO> findAll();

    public String addJob(Job job);

    public JobDTO findJobById(int id);

    public boolean deleteJobById(int id);

    boolean updateJobById(int id, Job updatedJob);
}
