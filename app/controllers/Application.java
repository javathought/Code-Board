package controllers;

import play.*;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.*;

import java.io.File;
import java.util.*;

import models.*;

public class Application extends Main {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byLogin", Security.connected()).first();
            renderArgs.put("username", user.login);
        }
    }
	
    public static void index() {
        List<Project> projects = Project.all().fetch();
        render(projects);	

//        render();
    }

	public static void project (String identifier) {
		Project project = Project.find("identifier", identifier).first();
		
		render(project);
	}
	
    public static void issues(String identifier) {
    	Logger.info("Project %s", identifier);
//    	List<Tracker> trackers = Tracker.find("order by position asc").fetch();
		Project project = Project.find("identifier", identifier).first();
		List<Issue> issues = Issue.find("byProject", project).fetch();
    	Logger.info("method issues : Project id %d", project.id);
    	render(issues, project);
    }
    public static void newIssue(String identifier) {
    	List<Tracker> trackers = Tracker.find("order by position asc").fetch();
		Project project = Project.find("identifier", identifier).first();
    	render("@issue", trackers, project);
    }
    
    public static void saveIssue(Long projectId, Long trackerId, @Valid Issue issue) {
        Project project = Project.findById(projectId);
    	List<Tracker> trackers = Tracker.find("order by position asc").fetch();
        if (Validation.hasErrors()) {
            render("@issue", issue, project, trackers);            
        }
        Tracker tracker = Tracker.findById(trackerId);
        issue.project = project;
        issue.tracker = tracker;
        issue.updated = Calendar.getInstance().getTime();
        
        issue.save();
        
        issues(project.identifier);
        
    }
    
    public static void issue(Long id) {
    	Issue issue = Issue.findById(id);
    	Project project = issue.project;
    	List<Tracker> trackers = Tracker.find("order by position asc").fetch();
    	render(issue, project, trackers);
    	}
    
    
}