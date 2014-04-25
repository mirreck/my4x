package net.my4x.population.model;

public enum HeradicFigure {
   BASE,PARTI,COUPE;
   
   public static HeradicFigure[] primaryFigures(){
      return new HeradicFigure[] {PARTI,COUPE};
   };
}
