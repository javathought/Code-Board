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
	Project project = issue.project;
	List<Tracker> trackers = Tracker.find("order by position asc").fetch();
	List<User> users = User.find("login <> 'root' order by login").fetch();
	List<State> states = State.find("order by position").fetch();
	List<Enumeration> priorities = Enumeration.find("byType", Enumeration.ISSUE_PRIORITY_TYPE).fetch();
	
	render(issue, project, trackers, users, states, priorities);
	}

	public static void issues(String identifier) {
	//    	List<Tracker> trackers = Tracker.find("order by position asc").fetch();
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
	
		render("@issue", trackers, project, users, states, priorities);
	}

	public static void saveIssue(Long projectId, Long trackerId, Long assigneeId, Long stateId, String priorityId, @Valid Issue issue) {
	        Project project = Project.findById(projectId);
	        if (Validation.hasErrors()) {
	        	List<Tracker> trackers = Tracker.find("order by position asc").fetch();
	    		List<User> users = User.find("login <> 'root' order by login").fetch();
	    		List<State> states = State.find("order by position").fetch();
	        	List<Enumeration> priorities = Enumeration.find("byType", "IssuePriority").fetch();
	            params.flash(); // add http parameters to the flash scope
	//            validation.keep(); // keep the errors for the next request
	            render("@issue", issue, project, trackers, users, states, priorities);            
	        }
	        Logger.info("assignee = %s", issue.assignee);
	        Tracker tracker = Tracker.findById(trackerId);
	        issue.project = project;
	        issue.tracker = tracker;
	        issue.state = State.findById(stateId);
	    	issue.priority = Enumeration.find("byName", priorityId).first();
	        if (assigneeId == null) {
	        	issue.assignee = null;
	        } else {
		        User assignee = User.findById(assigneeId);
		        issue.assignee = assignee;
	        }
	        issue.updated = Calendar.getInstance().getTime();
	        
	
	        
	        issue.save();
	        
	        issues(project.identifier);
	        
	    }

}
