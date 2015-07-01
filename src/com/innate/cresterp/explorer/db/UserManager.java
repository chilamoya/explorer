/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.explorer.db;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Tafadzwa
 */
public class UserManager {

    private final Database db;

    public UserManager(Database db) {
        this.db = db;
    }

    public void create(User agent) {
        try {

            db.execute("insert into registration (name,mobile, email, dob, gender"
                    + ", chanels) values (?,?,?,?,?,?);",
                    new String[]{agent.getName(), agent.getMobile(), agent.getEmail(),
                        agent.getDob(), agent.getGender(), agent.getChannel()});

        } catch (Exception ex) {

        }

    }
    
    public void update (User user){
        try {
            
             db.execute("UPDATE registration SET name = '"+user.getName()
                     +" mobile ='"+user.getMobile()+"', "
                     +" email = '"+user.getEmail()+"' "
                    +"' WHERE id = '"+user.getId()+"' ");
            
        }catch (Exception ex){
            
        }
    }

    public List<User> findAgents() throws IOException {
        Cursor c = db.executeQuery("select * from registration");
        List<User> agents = new ArrayList<User>();

        while (c.next()) {
            //name,mobile, email, dob, gender, chanels

            Row r = c.getRow();
            User agent = new User();
            agent.setId(r.getInteger(0));
            agent.setDob(r.getString(4));
            agent.setEmail(r.getString(3));
            agent.setGender(r.getString(5));
            agent.setChannel(r.getString(6));
            agent.setMobile(r.getString(2));
            agent.setName(r.getString(1));
            agents.add(agent);
        }
        return agents;
    }

    public Vector agentsVector() {

        Vector vector = new Vector();
        try {

            java.util.List<User> agents = findAgents();
            for (User agent : agents) {

                vector.add(agent.getName());
            }
        } catch (Exception ex) {

        }
        return vector;
    }

}
