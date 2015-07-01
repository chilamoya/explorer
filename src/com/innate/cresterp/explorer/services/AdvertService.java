/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.explorer.services;

import com.codename1.components.InfiniteProgress;
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
import com.innate.cresterp.explorer.db.DomainManager;
import com.innate.cresterp.explorer.db.User;
import com.innate.cresterp.explorer.db.UserManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Tafadzwa
 */
public class AdvertService {

    private final List<Advert> dailyAdverts = new ArrayList<Advert>();

    public List<Advert> findAdverts() {
        // convert the object to a JSON document
        dailyAdverts.clear();
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

                JSONParser parser = new JSONParser();
                Hashtable hm = parser.parse(new InputStreamReader(input));

                Vector vector = new Vector();
                vector = (Vector) hm.get("adverts");

                if (vector.size() > 0) {
                    for (int i = 0; i < vector.size(); i++) {

                        Hashtable h = (Hashtable) vector.get(i);
                        Advert advert = new Advert();
                        advert.setId(Integer.parseInt(h.get("id").toString()));
                        advert.setCompany(h.get("name").toString());
                        advert.setCompanyID(h.get("companyid").toString());
                        advert.setDescription(h.get("description").toString());
                        advert.setImage1(h.get("images").toString());
                        advert.setLatitude(h.get("latitude").toString());
                        advert.setLongitude(h.get("longitude").toString());
                        advert.setMobile(h.get("mobile").toString());
                        advert.setName(h.get("name").toString());
                        advert.setWebsite(h.get("website").toString());
                        dailyAdverts.add(advert);

                    }
                } else {
                    Advert advert = new Advert();
                    advert.setName("Sorry...");
                    advert.setDescription("No messages today");
                    dailyAdverts.add(advert);
                }
            }

            @Override
            protected void handleException(Exception err) {
                System.err.println(err);
                 Advert advert = new Advert();
                    advert.setName("Ooops...");
                    advert.setDescription("Please check your internet connection ");
                    dailyAdverts.add(advert);

            }

        };

        String url = new UrlManager().getDailyAdverts();
        request.setUrl(url);
        request.setPost(false);
         InfiniteProgress ip = new InfiniteProgress();
        
      /*  
        Dialog d = new Dialog();
        d.setDialogUIID("Dialog");
        d.setLayout(new BorderLayout());
        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.addComponent(new Label("Loading..."));
        cnt.addComponent(ip);
        d.addComponent(BorderLayout.CENTER, cnt);
        d.setTransitionInAnimator(CommonTransitions.createEmpty());
        d.setTransitionOutAnimator(CommonTransitions.createEmpty());
        d.showPacked(BorderLayout.CENTER, false);
        request.setDisposeOnCompletion(d);
         */
         
        networkManager.addToQueueAndWait(request);

        return dailyAdverts;
    }

    
    public void sendCallRequest(final Company company, final User user) {
        // convert the object to a JSON document
        
        NetworkManager networkManager = NetworkManager.getInstance();
        networkManager.start();
        networkManager.addErrorListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                NetworkEvent n = (NetworkEvent) evt;
                n.getError().printStackTrace();
                //System.out.println(n.getError());
            }
        });

        ConnectionRequest request;
        request = new ConnectionRequest() {

            int chr;
            StringBuffer sb = new StringBuffer();
            String response = "";

            @Override
            protected void readResponse(InputStream input) throws IOException {

                Display.getInstance().callSerially(new Runnable() {

                    public void run() {
                       Dialog.show("Call Request", "Thank you. "+company.getName()+" will get in touch as soon as possible",
                               "OK", "Cancel");
                    }
                });

            }

            @Override
            protected void handleException(Exception err) {
                //System.err.println(err);

            }

        };

         
        final String url = new UrlManager().getCallBack()+ "?company=" + company.getId()+"&name="
                +user.getName()+"&email="+user.getEmail()+"&mobile="+user.getMobile();
        
//        Display.getInstance().callSerially(new Runnable() {
//
//            public void run() {
//               
//                Dialog.show("Url formed", url, "OK", "Cancel");
//            }
//        });
        
         request.setUrl(new Utility(null, null).replaceAll(url, " ", "%20"));
        request.setPost(false);

        networkManager.addToQueueAndWait(request);

       
    }

    
}
