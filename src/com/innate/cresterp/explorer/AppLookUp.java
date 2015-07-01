/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.explorer;

import com.innate.cresterp.explorer.db.Company;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Tafadzwa
 */
public class AppLookUp {
    
    private Vector companiesVector ;
    private List <Company> companiesList ;
   

    public Vector getCompaniesVector() {
        return companiesVector;
    }

    public void setCompaniesVector(Vector companiesVector) {
        this.companiesVector = companiesVector;
    }

    public List<Company> getCompaniesList() {
        return companiesList;
    }

    public void setCompaniesList(List<Company> companiesList) {
        this.companiesList = companiesList;
    }
    
    
    
}
