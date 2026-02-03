package com.ideapool.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ideapool.springboot.app.models.entity.Client;
import com.ideapool.springboot.app.models.entity.Invoice;
import com.ideapool.springboot.app.models.entity.InvoiceItem;
import com.ideapool.springboot.app.models.entity.Product;
import com.ideapool.springboot.app.models.service.IClientService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("invoice")
@SessionAttributes("invoice")
public class InvoiceController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private IClientService clientService;
	
	@GetMapping("/form/{clientId}")
	public String create(
			@PathVariable(value="clientId") Long clientId,
			Map<String, Object> model,
			RedirectAttributes flash) {
		
		Client client = clientService.findOne(clientId);
		if (client == null) {
            flash.addFlashAttribute("error", "Client id does not exist!");
			return "redirect:/list";
		}
		
		Invoice invoice = new Invoice();
		invoice.setClient(client);
		
		model.put("title", "Create Invoice");
		model.put("invoice", invoice);
		
		return "invoice/form";
	}
	
	@GetMapping(value="load-products/{term}", produces = {"application/json"})
	public @ResponseBody List<Product> loadProducts(@PathVariable String term) {
		return clientService.finProductByName(term);
	}
	
	@GetMapping("/view/{id}")
	public String view(
			@PathVariable(value="id") Long id,
			Model model,
			RedirectAttributes flash) {
		Invoice invoice = clientService.findInvoiceById(id);
		if (invoice == null) {
            flash.addFlashAttribute("error", "Invoice id does not exist!");
            return "redirect:/list";
		}
		
		model.addAttribute("invoice", invoice);
		model.addAttribute("title", "Invoice: ".concat(invoice.getDescription()));
		return "invoice/view";
	}
	
	@RequestMapping(value="/update/{id}")
    public String edit(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        if (id <= 0) {
            flash.addFlashAttribute("error", "Invoice id cannot be zero!");
            return "redirect:/list";
        }

        Invoice invoice = clientService.findInvoiceByIdWithItems(id);
        if (invoice == null) {
            flash.addFlashAttribute("error", "Invoice id does not exist!");
            return "redirect:/list";
        }

        model.put("title", "Update Invoice");
        model.put("invoice", invoice);
        return "invoice/form";
    }
	
	@PostMapping("/form")
	public String save(
			@Valid Invoice invoice,
			BindingResult result,
			Model model,
			@RequestParam(name="item_id[]", required=false) Long[] itemId,
			@RequestParam(name="quantity[]", required=false) Integer[] quantity,
			RedirectAttributes flash,
			SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Create Invoice");
			return "invoice/form";
		}
		
		if (itemId == null || itemId.length == 0) {
			model.addAttribute("title", "Create Invoice");
			model.addAttribute("error", "The invoice cannot have no lines");
			return "invoice/form";
		}
		
		for(int i=0; i<itemId.length; i++) {
			Product product = clientService.findProductById(itemId[i]);
			InvoiceItem line = new InvoiceItem();
			line.setQuantity(quantity[i]);
			line.setProduct(product);
			invoice.addItem(line);
			
			log.info("ID: " + itemId[i].toString() + ", quantity: " + quantity[i].toString());
		}
		
		clientService.saveInvoice(invoice);
		status.setComplete();
		flash.addFlashAttribute("success", "Invoice created with success!");
		return "redirect:/view/" + invoice.getClient().getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(
			@PathVariable(value="id") Long id,
			RedirectAttributes flash) {
		Invoice invoice = clientService.findInvoiceById(id);
		
		if (invoice != null) {
			clientService.deleteInvoice(id);
			flash.addFlashAttribute("success", "Invoice deleted with success!");
			return "redirect:/view/" + invoice.getClient().getId();
		}
		flash.addFlashAttribute("error", "Invoice does not exists!");
		return "redirect:/list";
	}
	
}
