package controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.State;
import play.data.validation.Valid;
import play.i18n.Messages;
import ext.move.Mover;

public class States extends Admin {

	public static void create() {
		render("@edit");
	}

	public static void delete(Long id) {
		State state_to_delete = State.findById(id);
		
		Mover.upAfter(state_to_delete);
	
		state_to_delete.delete();
		
		Map summary = new HashMap();
		summary.put(0, 1);
		summary.put(1, Messages.get("state_deleted"));
		summary.put(2, state_to_delete.id);
			
		renderJSON(summary);
	}

	public static void edit(Long id) {
		State state= State.findById(id);
		render(state);
	}

	public static void list() {
		List<State> states = State.find("order by position").fetch();
		render(states);
	}

	public static void move(Long id) {
			String to = params.get("move_to");
			State state_to_move = State.findById(id);
			
			Mover.move(state_to_move, to);
			
			state_to_move.save();
			
			Map summary = new HashMap();
			summary.put(0, Messages.get("state_moved"));
			summary.put(1, state_to_move.id);
			summary.put(2, state_to_move.position);
				
			renderJSON(summary);
		}

	public static void save(boolean closed, boolean isDefault, @Valid State state) {
		if(state.position == 0) {
			state.position = State.count() + 1;
		}
		state.closed = closed;
		if (isDefault) {
			List <State> states = State.find("isDefault = ?", true).fetch();
			for (Iterator iterator = states.iterator(); iterator.hasNext();) {
				State stateU = (State) iterator.next();
				stateU.isDefault = false;
				stateU.save();
				
			}
		}
		state.isDefault = isDefault;
		state.save();
		list();
	}

}
