package net.my4x.webapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	@RequestMapping("home")
    public ModelAndView step2() {
		ModelAndView mav = new ModelAndView("home");
        return mav;
    }
	
	@RequestMapping("rest/data/{id}")
	@ResponseBody
	public String[] data(@PathVariable String id){
	   return new String[] {"a","b"};
	}
}
