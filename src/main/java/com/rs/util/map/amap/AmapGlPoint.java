package com.rs.util.map.amap;

import com.rs.util.map.LngLat;

public class AmapGlPoint {
    public final int   zoom;
    public final int   tileX;
    public final int   tileY;
    public final float x;
    public final float y;

    public AmapGlPoint(AmapTile tile, float x, float y) {
        this.tileX = tile.x;
        this.tileY = tile.y;
        this.zoom = tile.zoom;
        this.x = x;
        this.y = y;
    }
    public AmapGlPoint(int tileX, int tileY, int zoom, float x, float y) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.zoom = zoom;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f)#(%d, %d)@%d", x, y, tileX, tileY, zoom);
    }

    public static AmapGlPoint fromLngLat(LngLat lngLat, int zoom) {
        LngLat ll = lngLat.convertTo(LngLat.Map.amap);
        double x = (ll.lng + 180) / 360 * (1 << zoom);
        double y = (1.0 - Math.log(Math.tan(Math.toRadians(ll.lat)) + 1.0 / Math.cos(Math.toRadians(ll.lat))) / Math.PI) / 2.0 * (1 << zoom);
        float dx = (float) (x - Math.floor(x));
        float dy = (float) (y - Math.floor(y));
        return new AmapGlPoint((int) x, (int) y, zoom, dx, dy);
    }

    public LngLat toLngLat() {
        final double ratio = 1 << zoom;
        double lng = (tileX + x) / ratio * 360f - 180f;
        double lat = Math.toDegrees(Math.atan(Math.sinh(Math.PI - (tileY + y) / ratio * Math.PI * 2)));
        LngLat lngLat = new LngLat(lng, lat, LngLat.Map.amap);
        return lngLat;
    }
}
