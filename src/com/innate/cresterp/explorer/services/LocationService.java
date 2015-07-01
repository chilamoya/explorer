/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.explorer.services;

import com.codename1.db.Database;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.innate.cresterp.explorer.Utility;
import com.innate.cresterp.explorer.db.Company;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import org.json.JSONObject;

/**
 *
 * @author Tafadzwa
 */
public class LocationService {

    private final Database db;
    private final List<Company> companies = new ArrayList<Company>();
   

    public LocationService(Database db) {

        this.db = db;

    }

    public List<Company> queryLocalCompanies(final String latitude, final String longitude, final String user, final String radius) {

        NetworkManager networkManager = NetworkManager.getInstance();
        networkManager.start();
        networkManager.addErrorListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                NetworkEvent n = (NetworkEvent) evt;
                n.getError().printStackTrace();
                System.out.println(n.getError());
            }
        });

        ConnectionRequest request;
        request = new ConnectionRequest() {

            int chr;
            StringBuffer sb = new StringBuffer();
            String response = "";

            @Override
            protected void readResponse(InputStream input) throws IOException {

                Vector vector = new Vector();
                JSONParser parser = new JSONParser();
                Hashtable hm = parser.parse(new InputStreamReader(input));

                vector = (Vector) hm.get("companies");
                if (vector == null) {
                    Company company = new Company();

                    company.setDescription("Searching within 1KM radius...");
                    company.setName("Nothing found yet");
                    company.setAddress("...");
                    company.setCategory("...");
                    company.setEmail("...");
                    company.setWebsite("...");
                    company.setMobile("....");
                    
                    if (!companies.contains(company)) {
                        companies.add(company);
                    }
                } else {
                    for (int i = 0; i < vector.size(); i++) {

                        Hashtable h = (Hashtable) vector.get(i);
                        Company company = new Company();

                        company.setAddress(h.get("address").toString());
                        company.setCategory(h.get("category").toString());
                        company.setDescription(h.get("description").toString());
                        company.setEmail(h.get("email").toString());
                        company.setId(h.get("id").toString());
                        company.setLatitude(h.get("latitude").toString());
                        company.setLongitude(h.get("longitude").toString());
                        company.setMobile(h.get("mobile").toString());
                        company.setName(h.get("name").toString());
                        company.setWebsite(h.get("website").toString());

                        if (!companies.contains(company)) {
                            companies.add(company);
                            
                        }
                    }
                }

            }

            @Override
            protected void handleException(Exception err) {
              
               /* Display.getInstance().callSerially(new Runnable() {

                    public void run() {
                        Dialog.show("Oops", "Please make sure you still have data", "Ok", "Cancel");
                    }
                });*/

            }

        };

        String url = new UrlManager().getGetLocalCompanies() + "action=2&userlat=" + latitude 
                + "&userlong=" + longitude + "&distance="+radius;
        url = new Utility(null, null).replaceAll(url, " ", "%20");
        request.setUrl(url);
        request.setPost(false);

        
        
        networkManager.addToQueueAndWait(request);

        return companies;
    }

}
