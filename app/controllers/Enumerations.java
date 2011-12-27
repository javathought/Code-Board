package controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Fetch;

import play.Logger;
import play.data.validation.Valid;
import play.i18n.Messages;

import models.Enumeration;
import models.Enumeration;
import models.Enumeration;

public class Enumerations extends Admin {
	
	public static void create() {
		renderArgs.put("type", params.get("type"));
		render("@edit");
	}
	
	public static void edit(Long id) {
		Enumeration enumeration = Enumeration.findById(id);
		render(enumeration);
	}
	
	public static void save(boolean is_active, boolean is_default, @Valid Enumeration enumeration) {
		Logger.info("save enum %d - %s", enumeration.id, enumeration.name);
		if(enumeration.position == 0) {
			enumeration.position = Enumeration.count("type=?", enumeration.type) + 1;
		}
		enumeration.active = is_active;
		if (is_default) {
			List<Enumeration> deactive = Enumeration.find("type = ? and is_default = ?", enumeration.type, true).fetch();
			for (Iterator iterator = deactive.iterator(); iterator.hasNext();) {
				Enumeration active = (Enumeration) iterator.next();
				active.is_default = false;
					active.save();
			}
		}
		enumeration.is_default = is_default;
		enumeration.save();
		list();
	}

	public static void delete(Long id) {
		Enumeration enumeration_to_delete = Enumeration.findById(id);
		List<Enumeration> trackers = Enumeration.find("type = ? and position > ?", enumeration_to_delete.type, enumeration_to_delete.position).fetch();
		for (Iterator iterator = trackers.iterator(); iterator.hasNext();) {
			Enumeration tracker = (Enumeration) iterator.next();
			tracker.position = tracker.position - 1;
			tracker.save();			
		}
		enumeration_to_delete.delete();
		
		Map summary = new HashMap();
		summary.put(0, 1);
		summary.put(1, Messages.get("enumeration_deleted"));
		summary.put(2, enumeration_to_delete.id);
			
		renderJSON(summary);
	}

	public static void list() {
		List<Enumeration> priorities = Enumeration.find("type = ? order by position", Enumeration.ISSUE_PRIORITY_TYPE).fetch();
		render(priorities);
	}

	public static void move(Long id) {
		String to = params.get("move_to");
//		Logger.info("move enumeration %d to position %s", id, to);
		Enumeration enumeration_to_move = Enumeration.findById(id);
		
		if (to.equals("highest")) {
			List<Enumeration> enumerations = Enumeration.find("type = ? and position < ?", enumeration_to_move.type, enumeration_to_move.position).fetch();
			for (Iterator iterator = enumerations.iterator(); iterator.hasNext();) {
				Enumeration element = (Enumeration) iterator.next();
				element.position = element.position +1;
				element.save();
			}
			enumeration_to_move.position = 1;	
		} else if (to.equals("higher")) {
			Enumeration element_to_swap = Enumeration.find("type = ? and position = ?", enumeration_to_move.type, enumeration_to_move.position - 1).first();
			element_to_swap.position = enumeration_to_move.position;	
			enumeration_to_move.position = enumeration_to_move.position - 1;
			element_to_swap.save();
		} else if (to.equals("lower")) {
			Enumeration element_to_swap = Enumeration.find("type = ? and position = ?", enumeration_to_move.type, enumeration_to_move.position + 1).first();
			element_to_swap.position = enumeration_to_move.position;	
			enumeration_to_move.position = enumeration_to_move.position + 1;
			element_to_swap.save();
		} else if (to.equals("lowest")) {
			List<Enumeration> enumerations = Enumeration.find("type = ? and position > ?", enumeration_to_move.type, enumeration_to_move.position).fetch();
			for (Iterator iterator = enumerations.iterator(); iterator.hasNext();) {
				Enumeration element = (Enumeration) iterator.next();
				element.position = element.position - 1;
				element.save();			
			}
			enumeration_to_move.position = Enumeration.count("type=?", enumeration_to_move.type);	
		}
			
		enumeration_to_move.save();
		
		
		Map summary = new HashMap();
		summary.put(0, Messages.get("enumeration_moved"));
		summary.put(1, enumeration_to_move.id);
		summary.put(2, enumeration_to_move.position);
			
		renderJSON(summary);
	}


}
