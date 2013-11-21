package net.my4x.webapp.controller;


import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	
	@RequestMapping("rest/maptiles/{x}/{y}/{z}")
	public ResponseEntity<byte[]> maptiles(@PathVariable String x, @PathVariable String y, @PathVariable String z) throws IOException {
	    InputStream in = MainController.class.getResourceAsStream("/test.png");

	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);

	    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
	}
}
