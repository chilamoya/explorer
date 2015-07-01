package com.innate.cresterp.explorer;

import com.codename1.db.Database;
import com.codename1.googlemaps.MapContainer;
import com.codename1.maps.Coord;
import com.codename1.maps.MapListener;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.innate.cresterp.explorer.db.Company;
import com.innate.cresterp.explorer.db.DomainManager;
import com.innate.cresterp.explorer.db.User;
import com.innate.cresterp.explorer.db.UserManager;
import com.innate.cresterp.explorer.services.Advert;
import com.innate.cresterp.explorer.services.AdvertService;
import com.innate.cresterp.explorer.services.LocationService;
import com.innate.cresterp.explorer.services.UrlManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class Explorer {

    private Form current;
    LocationService locationService;
    java.util.List<Company> companies = new ArrayList();
    java.util.List<Company> mappedCompanys;
    private DomainManager domainManager;
    private UserManager userManager;
    private Database db;
    private AppLookUp lookUp;
    private boolean isMapOpen = false;

    Form detailsForm;
    private AdvertService advertService;
    java.util.List<Advert> adverts;
    private Form messageForm;
    Form main;
    User user;

    private void postInit() {
        domainManager = new DomainManager();
        db = domainManager.getDb();
        userManager = new UserManager(db);
        locationService = new LocationService(db);
        locationService = new LocationService(db);
        mappedCompanys = new ArrayList();
        detailsForm = new Form("User Details");

    }

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/theme2");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
//            Display.getInstance().setCommandBehavior(Display.COMMAND_BEHAVIOR_SIDE_NAVIGATION);
//            UIManager.getInstance().getLookAndFeel().setMenuBarClass(SideMenuBar.class);
            postInit();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Pro users - uncomment this code to get crash reports sent to you automatically
        /*Display.getInstance().addEdtErrorHandler(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
         evt.consume();
         Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
         Log.p("OS " + Display.getInstance().getPlatformName());
         Log.p("Error " + evt.getSource());
         Log.p("Current Form " + Display.getInstance().getCurrent().getName());
         Log.e((Throwable)evt.getSource());
         Log.sendLog();
         }
         });*/
    }

    public void start() {
        /*if (current != null) {
         current.show();
         return;
         }
         if (current != null) {
         current.show();
         return;
         }*/
        showInitial(showMainContainer());
    }

    private Vector createAdverts() {
        Vector vector = new Vector();

        for (Advert a : adverts) {
            Hashtable h = new Hashtable();
            h.put("Line1", a.getName());
            h.put("Line2", a.getDescription());

            vector.add(h);
        }
        return vector;
    }

    private void showRegistration(final Company company) {
        final Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        DomainManager dm = new DomainManager();
        final UserManager userManager = new UserManager(dm.getDb());
        user = new User();
        try {
            
            user = userManager.findAgents().get(0);
        } catch (Exception ex) {

        }
        final TextField name = new TextField();
        name.setHint("Enter name ");
        name.setText(user.getName());

        final TextField mobile = new TextField();
        mobile.setHint("Enter mobile number");
        mobile.setText(user.getMobile());

        final TextField email = new TextField();
        email.setHint("Enter email");
        email.setText(user.getEmail());

        cnt.addComponent(name);
        cnt.addComponent(email);
        cnt.addComponent(mobile);

        Container btnCnt = new Container(new BorderLayout());
        Button btn = new Button("Send");
        btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                user.setName(name.getText());
                user.setEmail(email.getText());
                user.setMobile(mobile.getText());
                
                try {
                    if (userManager.findAgents().isEmpty()) {
                        userManager.create(user);
                       
                    }else {
                        userManager.update(user);
                    }
                    
                    
                     advertService.sendCallRequest(company, user);
                        displayMapOnScreen();
                } catch (Exception ex) {

                }

              //  Dialog.show("Test", "Test", "OK", "Cancel");
            }
        });
        btnCnt.addComponent(BorderLayout.CENTER, btn);

        cnt.addComponent(btnCnt);

        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                showMap();
            }
        };

        detailsForm.setBackCommand(back);
        detailsForm.addComponent(cnt);
        detailsForm.show();

    }

    private Container showMainContainer() {
        final MultiList inboxList = new MultiList();
        final Container mainContainer = new Container(new BorderLayout());
        isMapOpen = true;

        advertService = new AdvertService();
        adverts = new ArrayList<Advert>();

        adverts = advertService.findAdverts();

        inboxList.setModel(new DefaultListModel(createAdverts()));
        inboxList.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                //Invoke Message page reading ind
                if (adverts.get(0).getName().indexOf("Ooops") < 0) {
                    creatingMessageForm(adverts.get(inboxList.getSelectedIndex()));
                }

            }
        });

        Container c = new Container(new BorderLayout());
        c.setUIID("Button");

        Button map = new Button("Map");
        InputStream in = Display.getInstance().getResourceAsStream(Form.class, "/1435070945_map-marker.png");

        try {
            map.setIcon(EncodedImage.create(in));
        } catch (Exception ex) {

        }
        map.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                displayMapOnScreen();
                /*
                 if (!isMapOpen) {
                 showMap();
                 } else {
                 mapForm.show();
                 mapForm.rev
                 }*/
            }
        });
        Button refresh = null;
        try {
            refresh = new Button(
                    EncodedImage.create(Display.getInstance()
                            .getResourceAsStream(Form.class, "/1435071406_refresh.png")));
        } catch (IOException ex) {

        }
        Container btns = new Container();
        btns.addComponent(map);
        btns.addComponent(refresh);
        refresh.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                adverts = advertService.findAdverts();
                inboxList.setModel(new DefaultListModel(createAdverts()));

            }
        });

        c.addComponent(BorderLayout.CENTER, btns);

        mainContainer.addComponent(BorderLayout.NORTH, c);
        mainContainer.addComponent(BorderLayout.CENTER, inboxList);

        return mainContainer;

    }

    private void displayMapOnScreen() {
        final Form mapForm = new Form();

        mapForm.getTitleArea().setUIID("Container");
        mapForm.setLayout(new BorderLayout());

        isMapOpen = true;
        mapForm.addComponent(BorderLayout.CENTER, showMap());
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                main.show();
                main.animate();
            }
        };

        mapForm.setBackCommand(back);

        mapForm.revalidate();
        mapForm.show();

    }

    private void showInitial(Container cnt) {

        if (main == null) {
            main = new Form("Movert Messenger");
            main.setLayout(new BorderLayout());

            Command back = new Command("Back") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (isMapOpen) {
                        showInitial(showMainContainer());
                        isMapOpen = false;
                    }
                }
            };

            main.setBackCommand(back);

        }
        main.removeAll();
        main.addComponent(BorderLayout.CENTER, cnt);

        main.revalidate();
        main.show();

    }

    private final UrlManager urlManager = new UrlManager();

    private void creatingMessageForm(Advert advert) {

        messageForm = new Form("Message");
        Container cnt = new Container(new BorderLayout());

        messageForm.setLayout(new BorderLayout());

        BrowserComponent web = new BrowserComponent();
        //  web.setPage(selectedAdvert.createHTML(), "http://localhost");
        web.setURL(urlManager.getAdvertURL() + advert.getId());
        web.setPinchToZoomEnabled(true);

        messageForm.addComponent(BorderLayout.CENTER, web);
        messageForm.addComponent(BorderLayout.SOUTH, cnt);

        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                main.showBack();
            }
        };

        messageForm.setBackCommand(back);
        messageForm.revalidate();
        messageForm.show();

        // messageForm.setBackCommand(new Command);
    }

    private MapContainer showMap() {

        final MapContainer cnt = new MapContainer();

        cnt.setShowMyLocation(true);
        //cnt.setSmoothScrolling(true);

        cnt.addMapListener(new MapListener() {
            public void mapPositionUpdated(Component source, int zoom, Coord center) {

                try {
                    addMarkers(center, cnt);
                    //Add markers here and see if there is any new messages 
                } catch (Exception ex) {

                }
            }
        });

        return cnt;
    }
    /*   
     hi.addCommand(new Command("Move Camera") {
     public void actionPerformed(ActionEvent ev) {
     cnt.setCameraPosition(new Coord(-33.867, 151.206));
     }
     });
    
     hi.addCommand(new Command("Add Marker") {
     public void actionPerformed(ActionEvent ev) {
     try {
     cnt.setCameraPosition(new Coord(41.889, -87.622));
     cnt.addMarker(EncodedImage.create("/maps-pin.png"), new Coord(41.889, -87.622), "Hi marker", "Optional long description", new ActionListener() {
     public void actionPerformed(ActionEvent evt) {
     Dialog.show("Marker Clicked!", "You clicked the marker", "OK", null);
     }
     });
     } catch (IOException err) {
     // since the image is iin the jar this is unlikely
     err.printStackTrace();
     }
     }
     });

     hi.addCommand(new Command("Add Marker Here") {
     public void actionPerformed(ActionEvent ev) {
     try {
     cnt.addMarker(EncodedImage.create("/maps-pin.png"), cnt.getCameraPosition(), "Marker At", "Lat: " + cnt.getCameraPosition().getLatitude() + ", " + cnt.getCameraPosition().getLongitude(), new ActionListener() {
     public void actionPerformed(ActionEvent evt) {
     Dialog.show("Marker Clicked!", "You clicked the marker", "OK", null);
     }
     });
     } catch (IOException err) {
     // since the image is iin the jar this is unlikely
     err.printStackTrace();
     }
     }
     });
     hi.addCommand(new Command("Add Path") {
     public void actionPerformed(ActionEvent ev) {
     cnt.setCameraPosition(new Coord(-18.142, 178.431));
     cnt.addPath(new Coord(-33.866, 151.195), // Sydney
     new Coord(-18.142, 178.431), // Fiji
     new Coord(21.291, -157.821), // Hawaii
     new Coord(37.423, -122.091) // Mountain View
     );
     }
     });
     hi.addCommand(new Command("Clear All") {
     public void actionPerformed(ActionEvent ev) {
     cnt.clearMapLayers();
     }
     });
     */

    private Container markerDetail(final Company c) {
        final Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        TextArea ta = new TextArea();
        String s = "Name: " + c.getName() + "\n";
        s = s + "About: " + c.getDescription();

        ta.setText(s);
        ta.setEditable(false);
        cnt.addComponent(ta);

        return cnt;
    }

    private void addMarkers(Coord center, MapContainer cnt) {
        companies = locationService.queryLocalCompanies(center.getLatitude() + "",
                center.getLongitude() + "", "", 0.5 + "");
        for (final Company c : companies) {
            //   if (!mappedCompanys.contains(c)) {
            //       mappedCompanys.add(c);
            Coord coord = new Coord(Double.parseDouble(c.getLatitude()),
                    Double.parseDouble(c.getLongitude()));
            cnt.addMarker(null, coord,
                    c.getName(), c.getDescription(),
                    new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {
                            //Display the information in a dialog.
                            final Dialog dlg = new Dialog("Business Details");
                            dlg.setLayout(new BorderLayout());

                            dlg.addComponent(BorderLayout.CENTER, markerDetail(c));
                            Button calls = new Button("Request Call");
                            calls.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent evt) {
                                    showRegistration(c);
                                }
                            });
                            Button close = new Button("Close");

                            Container btnCnt = new Container();
                            btnCnt.addComponent(calls);
                            btnCnt.addComponent(close);
                            close.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent evt) {
                                    dlg.dispose();
                                }
                            });

                            dlg.addComponent(BorderLayout.SOUTH, btnCnt);
                            dlg.showModeless();
                            //  dlg.show();

                        }
                    }
            );

            //   }
        }
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }

}
