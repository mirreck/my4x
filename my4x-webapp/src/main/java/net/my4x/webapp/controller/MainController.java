package net.my4x.webapp.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.my4x.dungeon.model.Dungeon;
import net.my4x.dungeon.services.DungeonService;
import net.my4x.map.service.MapTileService;
import net.my4x.population.model.Family;
import net.my4x.population.service.PersonnageService;
import net.my4x.talk.model.TalkStep;
import net.my4x.talk.service.TalkService;
import net.my4x.webapp.mapper.DungeonMapper;
import net.my4x.webapp.model.DungeonDTO;

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
   
   @Autowired
   private DungeonService dungeonService;
   
   @Autowired
   private PersonnageService personnageService;
   
   @Autowired
   private TalkService talkService;
   
   private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
   
	@RequestMapping("home")
    public ModelAndView home() {
	    
		ModelAndView mav = new ModelAndView("home");
        return mav;
    }

   @RequestMapping("map")
   public String mapHome() {
      return "map";
   }
   
   @RequestMapping("indoor")
   public String indoorHome() {
      return "indoor";
   }
   @RequestMapping("family")
   public String familyHome() {
      return "family";
   }   
	
   @RequestMapping("rest/talk/{id}/{step}")
   @ResponseBody
   public TalkStep talk(@PathVariable("id") String id, @PathVariable("step") String step) {

      LOGGER.debug("talk:"+id);
      return talkService.talk(id, id, step);
   }
   
   
   @RequestMapping(value = "rest/dungeon/{id}",headers = {"Accept=text/xml, application/json"}, produces = "application/json; charset=utf-8")    
   @ResponseBody
   public DungeonDTO dungeon(@PathVariable("id") String id) {

      LOGGER.debug("generating dungeon:"+id);
      
      Dungeon dun = dungeonService.load();
      
      LOGGER.debug("generated:"+dun.toString());
      
      return DungeonMapper.map(dun);
   }
	
   @RequestMapping(value = "rest/family/{id}", produces="application/json")
   @ResponseBody
   public Family family(@PathVariable("id") String id) {
      
      return personnageService.generateFamilly();
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
