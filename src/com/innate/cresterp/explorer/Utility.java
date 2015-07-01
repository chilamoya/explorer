/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.explorer;

import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tafadzwa
 */
public class Utility {

    private final Form main;
    private final Component component;

    public Utility(Form main, Component component) {
        this.main = main;
        this.component = component;
    }

       public String createConfigFile(String listFile) {

        FileSystemStorage inst = FileSystemStorage.getInstance();
        String homePath = inst.getAppHomePath();
        final char sep = inst.getFileSystemSeparator();
        homePath = homePath + sep + "movertmessenger" + sep;

        listFile = homePath + listFile;

        if (!inst.exists(listFile)) {

            try {
                inst.mkdir(homePath);
                inst.openOutputStream(listFile);
            } catch (IOException ex) {
            }
        }

        return listFile;
    }

      public String readTextFile(String file, boolean external) {

        String response = null;
        if (!external) {
            try {
                InputStream in = Display.getInstance().getResourceAsStream(Form.class, file);
                // String result  = in.
                // DataInputStream dis = new DataInputStream(in);
                response = Util.readToString(in, "UTF-8");
                // response = Util.readUTF(dis);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (external) {

            try {
                InputStream in = FileSystemStorage.getInstance().openInputStream(file);
                //return getStringFromInputStream(in);
                byte[] b = Util.readInputStream(in);
                response = new String(b);

            } catch (IOException ex) {
                //   ex.printStackTrace();

            }
        }

        return response;

    }

    public  String convertDateToString (Date toConvert){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	String date = sdf.format(toConvert); 
	System.out.println(date);
        
        return date ;
    }
    
     
    
        public boolean writeToExternalFile(String file, String info) throws IOException {
        boolean success = false;
        try {
            FileSystemStorage.getInstance().openOutputStream(file).write(info.getBytes());
            success = true;
        } catch (IOException ex) {

            success = false;
        }
        return success;
    }


    public String replaceAll(String text, String searchString, String replacementString) {

        StringBuffer sBuffer = new StringBuffer();
        int pos = 0;
        while ((pos = text.indexOf(searchString)) != -1) {
            sBuffer.append(text.substring(0, pos) + replacementString);
            text = text.substring(pos + searchString.length());
        }
        sBuffer.append(text);
        return sBuffer.toString();

    }

    public void refreshScreen() {
        main.getContentPane().removeAll();
        main.addComponent(BorderLayout.CENTER, component);
        main.revalidate();

    }

    public void displayMessage(final String title, final String message) {
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                Dialog.show(title, message, "OK", null);
            }
        });
    }
}
