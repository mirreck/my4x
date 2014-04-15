package net.my4x.population.model;

public enum HeraldicColor {
   AZUR("0000FF"),
   GUEULES("FF0000"),
   ORANGE("FF8000"),
   SINOPLE ("00C000"),
   POURPRE("89395E"),
   SANGUINE("C00000"),
   MURE("800040"),
   SABLE("000000"),
   ARGENT("e0e0e0"),
   OR("ffe000");
   
  private String code;

  private HeraldicColor(String code){
     this.code = code;
  }

public String getCode() {
   return code;
}
  
}
