package ext;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.util.Map;

import models.Configuration;
import play.templates.GroovyTemplate.ExecutableTemplate;
import play.templates.JavaExtensions;

public class ThemeExtension extends play.templates.FastTags {

	public static void _theme(Map<?, ?> args, Closure body, PrintWriter out, 
			   ExecutableTemplate template, int fromLine) {
	    	String theme = Configuration.getTheme();

			   out.println(theme);
			}
	
}
