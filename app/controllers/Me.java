package controllers;

import models.User;

public class Me extends Main {

	public static void show() {
		User user = User.find("byLogin", Security.connected()).first();
		renderTemplate("/Users/show.html",user);
	}


}
