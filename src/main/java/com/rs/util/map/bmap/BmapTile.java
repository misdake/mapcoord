package com.rs.util.map.bmap;

import com.rs.util.map.LngLat;

public class BmapTile {
    public final int zoom;
    public final int x;
    public final int y;

    public BmapTile(int x, int y, int zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) @ %d", x, y, zoom);
    }

    public static BmapTile fromLngLat(LngLat lngLat, int zoom) {
        BmapPoint bmapPoint = BmapPoint.fromLngLat(lngLat);
        if (bmapPoint == null) return null;
        BmapTilePoint bmapTilePoint = bmapPoint.toBmapTilePoint(zoom);
        return bmapTilePoint;
    }

    public BmapGlPoint toBmapGlPoint(float glX, float glY) {
        return new BmapGlPoint(this, glX, glY);
    }

    public BmapTilePoint toBmapTilePoint(double px, double py) {
        return new BmapTilePoint(x, y, zoom, px, py);
    }
}
