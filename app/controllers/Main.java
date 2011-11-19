package controllers;

import models.User;
import play.mvc.*;

public class Main extends Controller {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byLogin", Security.connected()).first();
            renderArgs.put("username", user.login);
        }
    }

}
