package net.my4x.webapp.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.my4x.services.map.MapTileService;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
   
   @Autowired
   private MapTileService tilesService;
   
   private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
   
	@RequestMapping("home")
    public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home");
        return mav;
    }
	
	@RequestMapping("rest/resetmap")
	@ResponseBody
	public String reset(){
		tilesService.resetAllTiles();
	   return "Done";
	}
	
	@RequestMapping("rest/maptiles/{z}/{x}/{y}")
	public ResponseEntity<byte[]> maptiles(@PathVariable String x, @PathVariable String y, @PathVariable String z) throws IOException {
	   LOGGER.debug("Loading tile :x={} y={}",x,y);
	   int tileX = Integer.parseInt(x);
	   int tileY = Integer.parseInt(y);
	   int tileZ = Integer.parseInt(z);
	   InputStream in =  new FileInputStream(tilesService.getHeightTile(tileX, tileY, tileZ));
	  
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
	    try {
	    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
	    } finally {
	       in.close();
	    }
	}
	
	   @RequestMapping("rest/maptiles-water/{z}/{x}/{y}")
	    public ResponseEntity<byte[]> waterMaptiles(@PathVariable String x, @PathVariable String y, @PathVariable String z) throws IOException {
	       LOGGER.debug("Loading tile :x={} y={}",x,y);
	       int tileX = Integer.parseInt(x);
	       int tileY = Integer.parseInt(y);
	       int tileZ = Integer.parseInt(z);
	       InputStream in =  new FileInputStream(tilesService.getWaterTile(tileX, tileY, tileZ));
	      
	        final HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_PNG);
	        try {
	        return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
	        } finally {
	           in.close();
	        }
	    }
}
