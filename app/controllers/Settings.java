package controllers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.helpers.FileWatchdog;

import models.Configuration;
import play.data.validation.Valid;
import play.vfs.VirtualFile;

public class Settings extends Admin {

	public static void index() {
		render();
	}
	
	public static void settings() {
		Configuration setting = Configuration.find("byType", "General").first();
		List<String> themes = Configuration.getThemes();
		render(setting, themes);
	}
	
	public static void save(@Valid Configuration setting) {
		setting.save();
		List<String> themes = Configuration.getThemes();
		
		render("@settings", setting, themes);
	}
	
	
}
