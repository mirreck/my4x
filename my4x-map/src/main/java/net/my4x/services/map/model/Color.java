package net.my4x.services.map.model;

public class Color {

   private int a =255;
   private int red;
   private int green;
   private int blue;
   public int getRed() {
      return red;
   }
   public int getGreen() {
      return green;
   }
   public int getBlue() {
      return blue;
   }
   public Color(int red, int green, int blue) {
      super();
      this.red = red;
      this.green = green;
      this.blue = blue;
   }
   
   public int getRGB(){
      return ((a & 0xFF) << 24) |
            ((red & 0xFF) << 16) |
            ((green & 0xFF) << 8)  |
            ((blue & 0xFF) << 0);
   }

	public Color darker(double d) {
		return new Color((int) (this.red * d), (int) (this.green * d),
				(int) (this.blue * d));
	}
   
   

}
