/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.explorer.services;

import com.codename1.db.Database;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import com.codename1.javascript.JSObject;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.innate.cresterp.explorer.Utility;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.json.JSONObject;

/**
 *
 * @author Tafadzwa
 */
public class MessageService {

    private final Database db;

    public MessageService(Database db) {

        this.db = db;

    }

    public JSONObject queryMessages(final String user) {
        // convert the object to a JSON document
        JSONObject object = new JSONObject();

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

                String s = Util.readToString(input, "UTF-8");

            }

            @Override
            protected void handleException(Exception err) {
                //System.err.println(err);

            }

        };

//        String url = new UrlManager().getUploadResponseURL() + "?id=" + user;
//        url = new Utility(null, null).replaceAll(url, " ", "%20");
//        request.setUrl(url);
//        request.setPost(false);

        networkManager.addToQueueAndWait(request);

        return object;
    }

}
