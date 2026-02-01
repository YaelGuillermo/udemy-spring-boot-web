package com.ideapool.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ideapool.springboot.app.models.entity.Client;
import com.ideapool.springboot.app.models.service.IClientService;
import com.ideapool.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("client")
public class ClientController {

	@Autowired
	@Qualifier("clientServiceJPA")
	private IClientService clientService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(
			@RequestParam(name="page", defaultValue="0") int page,
			Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Client> clients = clientService.findAll(pageRequest);
		PageRender<Client> pageRender = new PageRender("/list", clients);
		model.addAttribute("title", "List of clients");
		model.addAttribute("page", pageRender);
		model.addAttribute("clients", clients);
		return "list";
	}
	
	@RequestMapping(value="/form")
	public String create(Map<String, Object> model) {
		Client client = new Client();
		model.put("title", "Form of clients");
		model.put("client", client);
		return "form";
	}
	
	@RequestMapping(value="/form/{id}")
	public String edit(
			@PathVariable(value="id") Long id, 
			Map<String, Object> model,
			RedirectAttributes flash) {
		Client client = null;
		if (id > 0) {
			client = clientService.findOne(id);
			if (client == null) {
				flash.addFlashAttribute("success", "Client id does not exist!");
				return "redirect:/list";
			}
		} else {
			flash.addFlashAttribute("error", "Client id cannot be zero!");
			return "redirect:/list";
		}
		model.put("title", "Update Client");
		model.put("client", client);
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String save(
			@Valid Client client, 
			BindingResult result, 
			Model model, 
			SessionStatus status,
			RedirectAttributes flash) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Form of clients");
			return "form";
		}
		String messageFlash = (client.getId() == null) ? "created" : "updated";
		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", "Client " + messageFlash + " with success!");
		return "redirect:/list";
	}
	
	@RequestMapping(value="/delete/{id}")
	public String delete(
			@PathVariable(value="id") Long id,
			RedirectAttributes flash) {
		if (id > 0) {
			clientService.delete(id);
			flash.addFlashAttribute("success", "Client deleted with success!");
		}
		return "redirect:/list";
	}

}
