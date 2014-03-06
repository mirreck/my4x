package net.my4x.services.map;

import java.io.File;

public interface MapTileService {
	File getHeightTile(int x, int y, int zoom);
	void resetAllTiles();
	File getWaterTile(int tileX, int tileY, int tileZ);
}
