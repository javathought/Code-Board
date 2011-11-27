package controllers;

import java.util.List;

import play.data.validation.Valid;

import models.Configuration;
import models.User;

public class Settings extends Admin {

	public static void settings() {
		Configuration setting = Configuration.find("byType", "General").first();
		List<String> themes = Configuration.getThemes();
		render(setting, themes);
	}
	
	public static void save(@Valid Configuration setting) {
		setting.save();
		List<String> themes = Configuration.getThemes();
		render("@settings", setting, themes);
//		Application.index();
	}
}
