package com.jobapp.CompanyMS.Company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        return new ResponseEntity<>(companyService.addCompany(company), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable  int id) {
        Company company = companyService.getCompanyById(id);

        if (company != null)
            return new ResponseEntity<>(company, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompanyById(@PathVariable int id, @RequestBody Company updatedCompany) {
        boolean isUpdated = companyService.updateCompanyById(id, updatedCompany);

        if (isUpdated)
            return new ResponseEntity<>("Company Updated Successfully!", HttpStatus.OK);
        return new ResponseEntity<>("Company not found!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable int id) {
        boolean isDeleted = companyService.deleteCompanyById(id);

        if (isDeleted)
            return new ResponseEntity<>("Company deleted successfully!", HttpStatus.OK);
        return new ResponseEntity<>("Company not found!", HttpStatus.NOT_FOUND);
    }
}
