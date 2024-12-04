package com.jobapp.CompanyMS.Company.impl;

import com.jobapp.CompanyMS.Company.Company;
import com.jobapp.CompanyMS.Company.CompanyRepository;
import com.jobapp.CompanyMS.Company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public String addCompany(Company company) {
        companyRepository.save(company);
        return "Company added successfully!";
    }

    @Override
    public Company getCompanyById(int id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateCompanyById(int id, Company updatedCompany) {
        Optional<Company> companyToUpdate = companyRepository.findById(id);
        if (companyToUpdate.isPresent()) {
            Company company = companyToUpdate.get();
            company.setDescription(updatedCompany.getDescription());
            company.setName(updatedCompany.getName());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCompanyById(int id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
