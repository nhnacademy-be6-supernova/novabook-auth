package store.novabook.auth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AdminController {

	@GetMapping("/admin")
	public String adminP() {

		SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return "admin Controller";
	}
}