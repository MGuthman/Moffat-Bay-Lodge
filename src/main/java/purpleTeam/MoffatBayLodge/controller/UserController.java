// Purple Team: D. Bonis, R. Duvall, M. Guthman, O.Tsolmon
// Author: O.Tsolmon
// Date: 09/03/2023
// Updated: 09/09/2023 -- Updated redirect by using RedirectView instead of ModelAndView

package purpleTeam.MoffatBayLodge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import purpleTeam.MoffatBayLodge.bean.User;
import purpleTeam.MoffatBayLodge.service.UserService;

//User controller that handles all the incoming request, process, the user input, displays, and returns the correct view or jsp file.
//This user controller encapsulates the business logic by interpreting the client request and execute the necessary actions.

@Controller
public class UserController {

	@Autowired
	private final UserService userService;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;

	}

	@GetMapping("/register")
	public ModelAndView showRegistrationForm() {
		ModelAndView registerUserView = new ModelAndView("register-user");
		registerUserView.addObject("user", new User());
		return registerUserView;

	}

	@PostMapping("/register")
	public RedirectView saveUserRegistrationForm(@ModelAttribute("user") User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		String encodedConfirmPassword = passwordEncoder.encode(user.getConfirmPassword());

		user.setPassword(encodedPassword);
		user.setConfirmPassword(encodedConfirmPassword);

		userService.saveUser(user);
		return new RedirectView("/login");
	}
}
