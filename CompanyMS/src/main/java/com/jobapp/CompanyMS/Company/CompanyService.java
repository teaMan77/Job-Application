package com.jobapp.CompanyMS.Company;

import com.jobapp.CompanyMS.Company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {

    public List<Company> getAllCompanies();

    public String addCompany(Company company);

    public Company getCompanyById(int id);

    public boolean updateCompanyById(int id, Company updatedCompany);

    public boolean deleteCompanyById(int id);

    public void updateCompanyRating(ReviewMessage reviewMessage);
}
