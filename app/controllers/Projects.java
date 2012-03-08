package controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Domain;
import models.Issue;
import models.Project;
import play.data.validation.Valid;
import play.mvc.After;
import play.Logger;

public class Projects extends Admin {
	
	public static void list() {
		List<Project> projects = Project.find("order by created desc").fetch();
		render(projects);
	}

    @After(only={"create", "edit"})
    private static void renderCombos() {
		List<Project> projects = Project.findAll();
		List<Domain> domains = Domain.findAll();
		render("@edit", projects, domains);
    }
	
	public static void create() {
		Project project = new Project("");
		render("@edit", project);
	}
	
	public static void edit(String id) {
		Project project = Project.find("byIdentifier", id).first();
		render(project);
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
