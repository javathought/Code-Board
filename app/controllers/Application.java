package controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.Configuration;
import models.Enumeration;
import models.Issue;
import models.Project;
import models.State;
import models.Tracker;
import models.User;
import play.Logger;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Before;
import ext.MarkdownExtensions;
import ext.TextileExtension;

public class Application extends Main {
	
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()){
            User user = User.find("byLogin",Security.connected()).first();
            renderArgs.put("username", user);
            // TODO : examine the path of k33g.org
//            renderArgs.put("security",Security.connected());
//        } else {
//            renderArgs.put("username", new User("","","John Doe"));
        }
    }
	
    public static void index() {
    	User user = null;
    	// *** IMPORTANT NOTE ***
    	// Test if a user is connected to handle PostgreSQL ERROR : operator does not exist: character varying = bytea Indice ...
    	// if passing null to the query
    	if (Security.isConnected()) {
        	user = User.find("byLogin",Security.connected()).first();    		
    	}
        List<Project> projects = Project.findVisibleBy(user, true).fetch(5);
		Configuration setting = Configuration.find("byType", "General").first();
        render(projects, setting);	

    }

	public static void project (String identifier) {
		Project project = Project.find("identifier", identifier).first();
		List<Tracker> trackers = Tracker.findAll();
		Tracker tracker;
		Map summary = new HashMap();
		Map opened = new HashMap();
		
		Iterator trackerCursor = trackers.iterator();		
		while (trackerCursor.hasNext()) {
			tracker = (Tracker) trackerCursor.next();
			long i = Issue.count("tracker = ? and project = ?", tracker, project);
			long j = Issue.count("tracker = ? and project = ? and state.closed = ? ", tracker, project, false);
			summary.put(tracker.id, i);
			opened.put(tracker.id, j);
		}
		
		render(project, trackers, summary, opened);

	}
	    
//    public static void textile(String data) {
//    	renderText(TextileExtension.textile(data));
//    	
//    }
//
//    public static void markdown(String data) {
//    	renderText(MarkdownExtensions.markdown(data));
//    	
//    }

    
}