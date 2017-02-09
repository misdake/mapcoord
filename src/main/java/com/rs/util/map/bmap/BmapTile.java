package com.rs.util.map.bmap;

import com.rs.util.map.LngLat;

public class BmapTile {
    public final int zoom;
    public final int tileX;
    public final int tileY;

    public BmapTile(int tileX, int tileY, int zoom) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.zoom = zoom;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) @ %d", tileX, tileY, zoom);
    }

    public static BmapTile fromLngLat(LngLat lngLat, int zoom) {
        BmapPoint bmapPoint = BmapPoint.fromLngLat(lngLat);
        if (bmapPoint == null) return null;
        BmapTilePoint bmapTilePoint = bmapPoint.toBmapTilePoint(zoom);
        return bmapTilePoint;
    }

    public BmapTilePoint toBmapTilePoint(double px, double py) {
        return new BmapTilePoint(tileX, tileY, zoom, px, py);
    }
}
