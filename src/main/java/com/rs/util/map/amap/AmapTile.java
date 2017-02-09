package com.rs.util.map.amap;

import com.rs.util.map.LngLat;

public class AmapTile {
    public final int zoom;
    public final int x;
    public final int y;

    public AmapTile(int x, int y, int zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) @ %d", x, y, zoom);
    }

    public static AmapTile fromLngLat(LngLat lngLat, int zoom) {
        LngLat ll = lngLat.convertTo(LngLat.Map.amap);
        int x = (int) Math.floor((ll.lng + 180) / 360 * (1 << zoom));
        int y = (int) Math.floor((1.0 - Math.log(Math.tan(Math.toRadians(ll.lat)) + 1.0 / Math.cos(Math.toRadians(ll.lat))) / Math.PI) / 2.0 * (1 << zoom));
        return new AmapTile(x, y, zoom);
    }

    public LngLat toLngLat() {
        double n = 1 << zoom;
        double lng = x / n * 360 - 180;
        double lat = Math.toDegrees(Math.atan(Math.sinh(Math.PI - y / n * Math.PI * 2)));
        return new LngLat(LngLat.Map.amap, lng, lat);
    }
}
