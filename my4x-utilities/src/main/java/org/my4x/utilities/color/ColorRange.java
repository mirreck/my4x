package org.my4x.utilities.color;

import com.github.mirreck.RandomUtils;

public class ColorRange {
	private final Color start;
	private final Color end;

	public ColorRange(Color start, Color end) {
		super();
		this.start = start;
		this.end = end;
	};

	public Color pickRgb() {
		int r = RandomUtils.intInInterval(start.getRgb().r, end.getRgb().r);
		int g = RandomUtils.intInInterval(start.getRgb().g, end.getRgb().g);
		int b = RandomUtils.intInInterval(start.getRgb().b, end.getRgb().b);
		return Color.rgbColor(r, g, b);
	}

	public Color pickHsl() {
		double h = RandomUtils.doubleInInterval(start.getHsl().h,
				end.getHsl().h);
		double s = RandomUtils.doubleInInterval(start.getHsl().s,
				end.getHsl().s);
		double l = RandomUtils.doubleInInterval(start.getHsl().l,
				end.getHsl().l);
		return Color.hslColor(h, s, l);
	}
}
