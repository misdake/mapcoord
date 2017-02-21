package com.rs.util.map.bmap;

import com.rs.util.map.LngLat;

public class BmapTilePoint extends BmapTile {
    public final double px;
    public final double py;

    public BmapTilePoint(int tileX, int tileY, int zoom, double px, double py) {
        super(tileX, tileY, zoom);
        this.px = px;
        this.py = py;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) @ %d (%f, %f)", tileX, tileY, zoom, px, py);
    }

    public static BmapTilePoint fromLngLat(LngLat lngLat, int zoom) {
        BmapPoint bmapPoint = BmapPoint.fromLngLat(lngLat);
        if (bmapPoint == null) return null;
        BmapTilePoint bmapTilePoint = bmapPoint.toBmapTilePoint(zoom);
        return bmapTilePoint;
    }

    public BmapPoint toBmapPoint() {
        return new BmapPoint((tileX * 256 + px) * (1 << (18 - zoom)), (tileY * 256 + py) * (1 << (18 - zoom)));
    }

    public LngLat toLngLat() {
        LngLat lngLat = toBmapPoint().toLngLat();
        return lngLat;
    }
}
