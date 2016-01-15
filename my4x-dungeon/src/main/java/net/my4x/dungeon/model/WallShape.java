package net.my4x.dungeon.model;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum WallShape {
   PILLAR('o'), N('║'), S('║'), E('═'), W('═'), NS('║'), NE('╚'), NW('╝'), SE('╔'), SW('╗'), EW('═'), NSE('╠'), NSW('╣'), NEW('╩'), SEW(
         '╦'), NSEW('╬');

   private static final Logger LOGGER = LoggerFactory.getLogger(WallShape.class);

   public final char code;

   WallShape(char code) {
      this.code = code;
   }

   public static WallShape forPositions(boolean n, boolean s, boolean e, boolean w) {
	   return fromCode((n?"N":"")+(s?"S":"")+(e?"E":"")+(w?"W":""));
   }
   
   public static WallShape fromCode(String val) {
      if(StringUtils.isBlank(val)){
         return PILLAR;
      }
      
      for (WallShape type : WallShape.values()) {
         if (type.name().equals(val)) {
            return type;
         }
      }
      LOGGER.debug("code not found :" + val);
      return PILLAR;
   }
}
