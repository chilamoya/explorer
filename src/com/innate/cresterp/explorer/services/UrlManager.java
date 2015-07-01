/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.explorer.services;

/**
 *
 * @author Tafadzwa
 */
public class UrlManager {

    private final String getLocalCompanies = "http://www.cresterp.com/movert/api/companies.php?";
    private final String advertURL  = "http://movert.cresterp.com/adverts/index.php?id=";
    private final String dailyAdverts = "http://movert.cresterp.com/api/advertisements.php?action=2";
    private final String callBack = "http://movert.cresterp.com/adverts/ad_requests.php";

    public String getCallBack() {
        return callBack;
    }

    public String getDailyAdverts() {
        return dailyAdverts;
    }

    public String getAdvertURL() {
        return advertURL;
    }

    public String getGetLocalCompanies() {
        return getLocalCompanies;
    }
   
    
    
 

}
