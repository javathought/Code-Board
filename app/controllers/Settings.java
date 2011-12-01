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

	public static void settings() {
		Configuration setting = Configuration.find("byType", "General").first();
		List<String> themes = Configuration.getThemes();
		render(setting, themes);
	}
	
	public static void save(@Valid Configuration setting) {
		setting.save();
		List<String> themes = Configuration.getThemes();
		
		VirtualFile vf = VirtualFile.fromRelativePath("/app/views/tags/theme.html");
		File realFile = vf.getRealFile();
		try {
			FileWriter fr = new FileWriter(realFile);
			fr.write(
					"<link rel=\"stylesheet\" media=\"screen\" href=\"/public/themes/"
					+
					setting.look
					+
					"/stylesheets/application.css\">"
					);
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			Logger.info("file")
		}
		
		render("@settings", setting, themes);
	}
}
