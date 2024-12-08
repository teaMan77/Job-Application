package com.jobapp.JobMS.Job.impl;


import com.jobapp.JobMS.Job.Job;
import com.jobapp.JobMS.Job.JobRepository;
import com.jobapp.JobMS.Job.JobService;
import com.jobapp.JobMS.Job.clients.CompanyClient;
import com.jobapp.JobMS.Job.clients.ReviewClient;
import com.jobapp.JobMS.Job.dto.JobDTO;
import com.jobapp.JobMS.Job.external.Company;
import com.jobapp.JobMS.Job.external.Review;
import com.jobapp.JobMS.Job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
//    private List<Job> jobs = new ArrayList<Job>();
//    private int id = 1;

    int attempt = 0;

    JobRepository jobRepository;
    CompanyClient companyClient;
    ReviewClient reviewClient;

//    @Autowired
//    RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient,
                          ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyFallback")
//    @Retry(name = "companyBreaker", fallbackMethod = "companyFallback")
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt: " + ++attempt);
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String addJob(Job job) {
        jobRepository.save(job);
        return "Job added successfully!";
    }

    @Override
    public JobDTO findJobById(int id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDTO(job);
    }

    @Override
    public boolean deleteJobById(int id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJobById(int id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
                Job job = jobOptional.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setLocation(updatedJob.getLocation());
                jobRepository.save(job);
                return true;
        }
        return false;
    }

    private JobDTO convertToDTO(Job job) {
//        Company company = restTemplate.getForObject("http://localhost:8082/companies/" + job.getCompanyId(), Company.class);
//        Company company = restTemplate.getForObject("http://CompanyMS/companies/" + job.getCompanyId(),
//                Company.class);

        Company company = companyClient.getCompany(job.getCompanyId());

//        ResponseEntity<List<Review>> reviews = restTemplate.exchange(
//                "http://ReviewMS/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        
        return JobMapper.mapToJobWithCompanyDTO(job, company, reviews);
    }

    public List<String> companyFallback(Exception e) {
        List<String> list = new ArrayList<>();
        list.add("Company Service is down at the moment!");
        return list;
    }
}
