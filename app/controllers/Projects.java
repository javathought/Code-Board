package controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.Issue;
import models.Project;
import play.data.validation.Valid;
import play.db.jpa.JPABase;
import play.mvc.With;

public class Projects extends Admin {
	
	public static void list() {
		List<Project> projects = Project.find("order by created desc").fetch();
		render(projects);
	}

	public static void create() {
		List<Project> projects = Project.find("order by name").fetch();
		render("@edit", projects);
	}
	
	public static void edit(String id) {
		Project project = Project.find("byIdentifier", id).first();
		List<Project> projects = Project.findAll();
		render(project, projects);
	}
	
	public static void save(@Valid Project project) {
		if (Project.count("identifier = ? and id <> ?", project.identifier, project.id) > 0) {
			validation.addError("identifier", "validation.unique");
			List<Project> projects = Project.findAll();
			params.flash();
			render("@edit", project, projects); 
		}
        if (project.id == null) {
        	project.created = Calendar.getInstance().getTime();
        }
		project.save();
		list();
	}
	
	public static void delete(Long id) {
		Project project = Project.findById(id);
		Map summary = new HashMap();
		if ( Project.count("parent = ?", project) > 0) {

			summary.put(0, 0);
			summary.put(1, "It's a parent project");
			summary.put(2, project.id);			
			
		} else {
			Issue.delete("project", project);
			project.delete();
			summary.put(0, 1);
			summary.put(1, "project deleted");
			summary.put(2, project.id);			
		}
			
		renderJSON(summary);
	}
}
