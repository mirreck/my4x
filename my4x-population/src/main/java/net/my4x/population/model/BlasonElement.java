package net.my4x.population.model;

public class BlasonElement {
   private HeradicFigure figure;
   private HeraldicColor color = HeraldicColor.SABLE;
   public BlasonElement(HeradicFigure figure, HeraldicColor color) {
      super();
      this.figure = figure;
      this.color = color;
   }
   public HeradicFigure getFigure() {
      return figure;
   }
   public HeraldicColor getColor() {
      return color;
   }
   
}
