package com.rs.util.map.bmap;

import com.rs.util.map.LngLat;

public class BmapGlPoint {
    public final int   tileX;
    public final int   tileY;
    public final float x;
    public final float y;

    public BmapGlPoint(BmapTile tile, float x, float y) {
        if (tile.zoom != 16) throw new IllegalArgumentException("BmapGlPoint需要zoom=16的tile");
        this.tileX = tile.x;
        this.tileY = tile.y;
        this.x = x;
        this.y = y;
    }
    public BmapGlPoint(int tileX, int tileY, float x, float y) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.x = x;
        this.y = y;
    }

    public LngLat toLngLat() {
        BmapTile bmapTile = new BmapTile(tileX, tileY, 16);
        LngLat lngLat = bmapTile.toBmapTilePoint(x / 4f, y / 4f).toLngLat();
        return lngLat;
    }
}
