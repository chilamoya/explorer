/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.explorer.db;

import com.codename1.db.Database;

/**
 *
 * @author Tafadzwa
 */
public class DomainManager {

    Database db;

    public DomainManager() {
        createDatabase();
    }

    private void createDatabase() {

        try {
            boolean created = Database.exists("movertmessenger.db");
            db = Database.openOrCreate("movertmessenger.db");
            if (db == null) {
                System.out.println("SQLite is not supported on this platform");
                return;
            }
            if (!created) {
                //Create all the needed tables in database 
                db.execute("create table registration (id INTEGER PRIMARY KEY,name text,"
                        + "mobile text, email text, dob text, gender text, chanels text);");

            }
        } catch (Exception ex) {

        }
    }

    public Database getDb() {
        return db;
    }

}
