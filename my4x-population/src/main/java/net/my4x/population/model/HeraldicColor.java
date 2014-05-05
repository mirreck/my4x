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
   ARGENT("e0e0e0",ColorFamily.METAL),
   OR("ffe000",ColorFamily.METAL);
   
   private enum ColorFamily{
      EMAIL,METAL
   };
   
   private String code;
   private ColorFamily colorFamily = ColorFamily.EMAIL;

   private HeraldicColor(String code, ColorFamily f) {
      this.code = code;
      this.colorFamily = f;
   }
   private HeraldicColor(String code) {
      this.code = code;
   }

   public String getCode() {
      return code;
   }
   public HeraldicColor[] matchingColors(){
      switch(this.colorFamily){
         case EMAIL: return new HeraldicColor[]{ARGENT,OR};
         case METAL: return new HeraldicColor[]{AZUR,GUEULES,ORANGE,SINOPLE,POURPRE,SANGUINE,MURE,SABLE};
         default :return null;
      }
   }
  
}
