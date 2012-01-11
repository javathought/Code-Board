package controllers;

import java.util.Calendar;
import java.util.List;

import models.Enumeration;
import models.Issue;
import models.Project;
import models.State;
import models.Tracker;
import models.User;
import play.Logger;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.*;

public class Issues extends Controller {

    public static void index() {
        render();
    }

	public static void issue(Long id) {
		Issue issue = Issue.findById(id);
		List<Tracker> trackers = Tracker.find("order by position asc").fetch();
		List<User> users = User.find("login <> 'root' order by login").fetch();
		List<State> states = State.find("order by position").fetch();
		List<Enumeration> priorities = Enumeration.find("byType", Enumeration.ISSUE_PRIORITY_TYPE).fetch();
		State state = issue.state;
		
		render(issue, trackers, users, states, state, priorities);
	}

	public static void issues(String identifier) {
			Project project = Project.find("identifier", identifier).first();
			List<Issue> issues = Issue.find("byProject", project).fetch();
	    	render(issues, project);
	    }

	public static void myIssues() {
		List<Issue> issues = Issue.find("assignee.login = ? ", Security.connected()).fetch();

		render("@issues", issues);
	}

	public static void newIssue(String identifier) {
		List<Tracker> trackers = Tracker.find("order by position asc").fetch();
		Project project = Project.find("identifier", identifier).first();
		List<User> users = User.find("login <> 'root' order by login").fetch();
		List<State> states = State.find("order by position").fetch();
		List<Enumeration> priorities = Enumeration.find("byType", "IssuePriority").fetch();
		Issue issue = new Issue(project);
	
		render("@issue", issue, trackers, users, states, priorities);
	}

	public static void saveIssue(@Valid Issue issue) {
        if (Validation.hasErrors()) {
        	List<Tracker> trackers = Tracker.find("order by position asc").fetch();
    		List<User> users = User.find("login <> 'root' order by login").fetch();
    		List<State> states = State.find("order by position").fetch();
        	List<Enumeration> priorities = Enumeration.find("byType", "IssuePriority").fetch();
            params.flash(); // add http parameters to the flash scope
            render("@issue", issue, trackers, users, states, priorities);            
        }
        issue.updated = Calendar.getInstance().getTime();        
        issue.save();

        issues(issue.project.identifier);
        
    }

}
