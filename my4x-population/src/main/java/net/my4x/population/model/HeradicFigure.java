package net.my4x.population.model;

public enum HeradicFigure {
   BASE,PARTI,COUPE,TAILLE,TRANCHE;
   
   public static HeradicFigure[] primaryFigures(){
      return new HeradicFigure[] {PARTI,COUPE,TAILLE,TRANCHE};
   };
}
