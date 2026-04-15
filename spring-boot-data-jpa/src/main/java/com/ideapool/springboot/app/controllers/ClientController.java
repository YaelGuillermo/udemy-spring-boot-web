package com.ideapool.springboot.app.controllers;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ideapool.springboot.app.models.entity.Client;
import com.ideapool.springboot.app.models.service.IClientService;
import com.ideapool.springboot.app.models.service.IUploadFileService;
import com.ideapool.springboot.app.util.paginator.PageRender;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("client")
public class ClientController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
    @Autowired
    @Qualifier("clientServiceJPA")
    private IClientService clientService;

    @Autowired
    private IUploadFileService uploadService;

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(@RequestParam(name="page", defaultValue="0") int page, Model model, Authentication authentication, HttpServletRequest request) {
    	
    	if (authentication != null) {
    		logger.info("Hello authenticated user, your username is ".concat(authentication.getName()));
    	}
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	if (auth != null) {
    		logger.info("Using static form SecurityContextHolder.getContext().getAuthentication(): Authenticated user, username: ".concat(auth.getName()));
    	}
    	
    	if(hasRole("ROLE_ADMIN")) {
    		logger.info("Hello ".concat(auth.getName().concat(" you have access!")));
    	} else {
    		logger.info("Hello ".concat(auth.getName().concat(" you do not have access!")));
    	}
    	
    	SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
    	
    	if (securityContext.isUserInRole("ADMIN")) {
    		logger.info("Using form SecurityContextHolderAwareRequestWrapper: Hello ".concat(auth.getName().concat(" you have access!")));
    	} else {
    		logger.info("Using form SecurityContextHolderAwareRequestWrapper: Hello ".concat(auth.getName().concat(" you do not have access!")));
    	}
    	
    	if (request.isUserInRole("ROLE_ADMIN")) {
    		logger.info("Using form HttpServletRequest: Hello ".concat(auth.getName().concat(" you have access!")));
    	} else {
    		logger.info("Using form HttpServletRequest: Hello ".concat(auth.getName().concat(" you do not have access!")));
    	}
    	
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Client> clients = clientService.findAll(pageRequest);

        PageRender<Client> pageRender = new PageRender<>("/list", clients);

        model.addAttribute("title", "List of clients");
        model.addAttribute("page", pageRender);
        model.addAttribute("clients", clients);
        return "list";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/view/{id}")
    public String view(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Client client = clientService.fetchByIdWithInvoices(id);
        if (client == null) {
            flash.addFlashAttribute("error", "Client id does not exist!");
            return "redirect:/list";
        }

        model.put("title", "View Client: " + client.getName());
        model.put("client", client);
        return "view";
    }

    
    @Secured({"ROLE_USER"})
    @RequestMapping(value="/uploads/{filename:.+}")
    public ResponseEntity<Resource> viewPhoto(@PathVariable String filename) {
        Resource resource = uploadService.load(filename);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/form")
    public String create(Map<String, Object> model) {
        Client client = new Client();
        model.put("title", "Form of clients");
        model.put("client", client);
        return "form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/form/{id}")
    public String edit(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        if (id <= 0) {
            flash.addFlashAttribute("error", "Client id cannot be zero!");
            return "redirect:/list";
        }

        Client client = clientService.findOne(id);
        if (client == null) {
            flash.addFlashAttribute("error", "Client id does not exist!");
            return "redirect:/list";
        }

        model.put("title", "Update Client");
        model.put("client", client);
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String save(
        @Valid Client client,
        BindingResult result,
        Model model,
        @RequestParam("photoFile") MultipartFile photo,
        @RequestParam(name="removePhoto", defaultValue="false") boolean removePhoto,
        SessionStatus status,
        RedirectAttributes flash
    ) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Form of clients");
            return "form";
        }

        String oldPhoto = client.getPhoto();

        if (removePhoto && oldPhoto != null && !oldPhoto.isBlank()) {
            uploadService.delete(oldPhoto);
            client.setPhoto(null);
            flash.addFlashAttribute("info", "Photo removed!");
            oldPhoto = null;
        }

        if (photo != null && !photo.isEmpty()) {
            if (oldPhoto != null && !oldPhoto.isBlank()) {
                uploadService.delete(oldPhoto);
            }

            String newFilename = uploadService.copy(photo);
            client.setPhoto(newFilename);
            flash.addFlashAttribute("info", "Photo uploaded with success!");
        }

        String messageFlash = (client.getId() == null) ? "created" : "updated";
        clientService.save(client);
        status.setComplete();
        flash.addFlashAttribute("success", "Client " + messageFlash + " with success!");

        return "redirect:/list";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/delete/{id}")
    public String delete(@PathVariable(value="id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            Client client = clientService.findOne(id);
            if (client != null) {
                uploadService.delete(client.getPhoto());
                clientService.delete(id);
                flash.addFlashAttribute("success", "Client deleted with success!");
            }
        }
        return "redirect:/list";
    }
    
    private boolean hasRole(String role) {
    	SecurityContext context = SecurityContextHolder.getContext();
    	if (context == null)
    		return false;
    	
    	Authentication auth = context.getAuthentication();
    	
    	if (auth == null)
    		return false;
    	
    	Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
    	
    	return authorities.contains(new SimpleGrantedAuthority(role));
    }
}