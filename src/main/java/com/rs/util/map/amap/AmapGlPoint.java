package com.rs.util.map.amap;

import com.rs.util.map.LngLat;

public class AmapGlPoint {
    public final int   tileX_12;
    public final int   tileY_12;
    public final float x;
    public final float y;

    public AmapGlPoint(AmapTile tile, float x, float y) {
        if (tile.zoom != 15) throw new IllegalArgumentException("AmapGlPoint需要zoom=15的tile");
        tileX_12 = tile.x >> 3;
        tileY_12 = tile.y >> 3;
        this.x = x;
        this.y = y;
    }
    public AmapGlPoint(int tileX_z15, int tileY_z15, float x, float y) {
        tileX_12 = tileX_z15 >> 3;
        tileY_12 = tileY_z15 >> 3;
        this.x = x;
        this.y = y;
    }

    private static final double RATIO = 1 << 15;
    public LngLat toLngLat() {
        double lng = x / RATIO * 360f - 180f;
        double lat = Math.toDegrees(Math.atan(Math.sinh(Math.PI - y / RATIO * Math.PI * 2)));
        LngLat lngLat = new LngLat(lng, lat, LngLat.Map.amap);
        return lngLat;
    }
}
