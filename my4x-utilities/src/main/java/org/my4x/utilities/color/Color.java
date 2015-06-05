package org.my4x.utilities.color;

public class Color {

	
	private final RGB rgb;
	private final HSL hsl;
	
	public RGB getRgb() {
		return rgb;
	}

	public HSL getHsl() {
		return hsl;
	}

	public static Color rgbColor(int r, int g, int b){
		RGB rgb = new RGB(r,g,b);
		return new Color(rgb, ColorUtils.hsl(rgb));
	}
	
	public static Color hslColor(double h, double s, double l){
		HSL hsl = new HSL(h,s,l);
		return new Color(ColorUtils.rgb(hsl),hsl);
	}

	public Color(RGB rgb, HSL hsl) {
		super();
		this.rgb = rgb;
		this.hsl = hsl;
	}
	public String cssCode(){
		return String.format("%02X", rgb.r) + String.format("%02X", rgb.g) + String.format("%02X", rgb.b);
	}

}
