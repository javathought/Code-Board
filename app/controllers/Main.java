package controllers;

import models.Configuration;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class Main extends Controller {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byLogin", Security.connected()).first();
            renderArgs.put("username", user.login);
        }
    }
    
    @Before
    static void setTheme() {
    	String theme = Configuration.getTheme();
    	renderArgs.put("theme", theme);
    }

}
