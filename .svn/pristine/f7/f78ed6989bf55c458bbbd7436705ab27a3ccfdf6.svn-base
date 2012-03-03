package controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.IterateBlock;

import models.Enumeration;
import models.State;
import models.Tracker;
import models.User;
import play.Logger;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.i18n.Messages;
import play.mvc.With;

public class Trackers extends Admin {

	public static void list() {
		List<Tracker> trackers = Tracker.find("order by position asc").fetch();
		render(trackers);
	}
	
	public static void create() {
		render("@edit");
	}
	
	public static void edit(Long id) {
		Tracker tracker = Tracker.findById(id);
		render(tracker);
	}
	
	public static void save(@Valid Tracker tracker) {
        if (Validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
//            validation.keep(); // keep the errors for the next request
            render("@edit", tracker);            
        }

		if(tracker.position == 0) {
			tracker.position = Tracker.count() + 1;
		}
		tracker.save();
		list();
	}

	public static void delete(Long id) {
		Tracker tracker_to_delete = Tracker.findById(id);
		List<Tracker> trackers = Tracker.find("position > ?", tracker_to_delete.position).fetch();
		for (Iterator iterator = trackers.iterator(); iterator.hasNext();) {
			Tracker tracker = (Tracker) iterator.next();
			tracker.position = tracker.position - 1;
			tracker.save();			
		}
		tracker_to_delete.delete();
		
		Map summary = new HashMap();
		summary.put(0, 1);
		summary.put(1, Messages.get("tracker_deleted"));
		summary.put(2, tracker_to_delete.id);
			
		renderJSON(summary);
	}

	public static void move(Long id) {
		String to = params.get("move_to");
//		Logger.info("move tracker %d to position %s", id, to);
		Tracker tracker_to_move = Tracker.findById(id);
		
		if (to.equals("highest")) {
			List<Tracker> trackers = Tracker.find("position < ?", tracker_to_move.position).fetch();
			for (Iterator iterator = trackers.iterator(); iterator.hasNext();) {
				Tracker tracker = (Tracker) iterator.next();
				tracker.position = tracker.position +1;
				tracker.save();
			}
			tracker_to_move.position = 1;	
		} else if (to.equals("higher")) {
			Tracker tracker_to_swap = Tracker.find("position = ?", tracker_to_move.position - 1).first();
			tracker_to_swap.position = tracker_to_move.position;	
			tracker_to_move.position = tracker_to_move.position - 1;
			tracker_to_swap.save();
		} else if (to.equals("lower")) {
			Tracker tracker_to_swap = Tracker.find("position = ?", tracker_to_move.position + 1).first();
			tracker_to_swap.position = tracker_to_move.position;	
			tracker_to_move.position = tracker_to_move.position + 1;
			tracker_to_swap.save();
		} else if (to.equals("lowest")) {
			List<Tracker> trackers = Tracker.find("position > ?", tracker_to_move.position).fetch();
			for (Iterator iterator = trackers.iterator(); iterator.hasNext();) {
				Tracker tracker = (Tracker) iterator.next();
				tracker.position = tracker.position - 1;
				tracker.save();			
			}
			tracker_to_move.position = Tracker.count();	
		}
			
		tracker_to_move.save();
		
		Map summary = new HashMap();
		summary.put(0, Messages.get("tracker_moved"));
		summary.put(1, tracker_to_move.id);
		summary.put(2, tracker_to_move.position);
			
		renderJSON(summary);
	}
	
}
