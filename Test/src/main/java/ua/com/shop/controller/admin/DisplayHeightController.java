package ua.com.shop.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ua.com.shop.dto.form.DisplayHeightForm;
import ua.com.shop.service.DisplayHeightService;
import ua.com.shop.validator.DisplayHeightValidator;

@Controller
@RequestMapping("/admin/displayheight")
@SessionAttributes("displayheight")
public class DisplayHeightController {

	@Autowired
	private DisplayHeightService displayHeightService;

	@InitBinder("displayheight")
	protected void bind(WebDataBinder binder) {
		binder.setValidator(new DisplayHeightValidator(displayHeightService));
	}

	@ModelAttribute("displayheight")
	public DisplayHeightForm getForm() {
		return new DisplayHeightForm();
	}

	@GetMapping
	public String show(Model model) {
		model.addAttribute("displayheights", displayHeightService.findAll());
		return "admin-displayheight";
	}

	@PostMapping
	public String save(
			@ModelAttribute("displayheight") @Valid DisplayHeightForm displayHeightForm,
			BindingResult br, Model model, SessionStatus status) {
		if (br.hasErrors()) {
			return show(model);
		}
		displayHeightService.save(displayHeightForm);
		status.setComplete();
		return "redirect:/admin/displayheight";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable int id, Model model) {
		model.addAttribute("displayheight", displayHeightService.findForm(id));
		show(model);
		return "admin-displayheight";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		displayHeightService.delete(id);
		return "redirect:/admin/displayheight";
	}

}
