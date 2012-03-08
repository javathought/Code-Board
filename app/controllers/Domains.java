package controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.IterateBlock;

import models.Enumeration;
import models.State;
import models.Domain;
import models.User;
import play.Logger;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.i18n.Messages;
import play.mvc.With;

public class Domains extends Admin {

	public static void list() {
		List<Domain> domains = Domain.find("order by position asc").fetch();
		render(domains);
	}
	
	public static void create() {
		render("@edit");
	}
	
	public static void edit(Long id) {
		Domain domain = Domain.findById(id);
		render(domain);
	}
	
	public static void save(@Valid Domain domain) {
        if (Validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
//            validation.keep(); // keep the errors for the next request
            render("@edit", domain);            
        }

		if(domain.position == 0) {
			domain.position = Domain.count() + 1;
		}
		
		if (domain.isDefault) {
			Domain defaultDomain = Domain.find("isDefault = ?", true).first();
			if (defaultDomain  != null) {
				defaultDomain.isDefault = false;
				defaultDomain.save();
			}
		}
		
		domain.save();
		list();
	}

	public static void delete(Long id) {
		Domain domain_to_delete = Domain.findById(id);
		List<Domain> domains = Domain.find("position > ?", domain_to_delete.position).fetch();
		for (Iterator iterator = domains.iterator(); iterator.hasNext();) {
			Domain domain = (Domain) iterator.next();
			domain.position = domain.position - 1;
			domain.save();			
		}
		domain_to_delete.delete();
		
		Map summary = new HashMap();
		summary.put(0, 1);
		summary.put(1, Messages.get("domain_deleted"));
		summary.put(2, domain_to_delete.id);
			
		renderJSON(summary);
	}

	public static void move(Long id) {
		String to = params.get("move_to");
//		Logger.info("move domain %d to position %s", id, to);
		Domain domain_to_move = Domain.findById(id);
		
		if (to.equals("highest")) {
			List<Domain> domains = Domain.find("position < ?", domain_to_move.position).fetch();
			for (Iterator iterator = domains.iterator(); iterator.hasNext();) {
				Domain domain = (Domain) iterator.next();
				domain.position = domain.position +1;
				domain.save();
			}
			domain_to_move.position = 1;	
		} else if (to.equals("higher")) {
			Domain domain_to_swap = Domain.find("position = ?", domain_to_move.position - 1).first();
			domain_to_swap.position = domain_to_move.position;	
			domain_to_move.position = domain_to_move.position - 1;
			domain_to_swap.save();
		} else if (to.equals("lower")) {
			Domain domain_to_swap = Domain.find("position = ?", domain_to_move.position + 1).first();
			domain_to_swap.position = domain_to_move.position;	
			domain_to_move.position = domain_to_move.position + 1;
			domain_to_swap.save();
		} else if (to.equals("lowest")) {
			List<Domain> domains = Domain.find("position > ?", domain_to_move.position).fetch();
			for (Iterator iterator = domains.iterator(); iterator.hasNext();) {
				Domain domain = (Domain) iterator.next();
				domain.position = domain.position - 1;
				domain.save();			
			}
			domain_to_move.position = Domain.count();	
		}
			
		domain_to_move.save();
		
		Map summary = new HashMap();
		summary.put(0, Messages.get("domain_moved"));
		summary.put(1, domain_to_move.id);
		summary.put(2, domain_to_move.position);
			
		renderJSON(summary);
	}
	
}
