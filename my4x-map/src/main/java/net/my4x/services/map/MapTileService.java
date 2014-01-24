package net.my4x.services.map;

import java.io.InputStream;

public interface MapTileService {
	InputStream getTileAsStream(int x, int y, int zoom);

	void resetAllTiles();
}
