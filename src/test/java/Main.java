import com.rs.util.map.LngLat;
import com.rs.util.map.amap.AmapTile;
import com.rs.util.map.bmap.BmapTile;
import com.rs.util.map.bmap.BmapTilePoint;

public class Main {

    public static void main(String[] args) {
        LngLat lngLat = new LngLat(121.589622, 31.205825, LngLat.Map.bmap);
        BmapTilePoint bmapTilePoint = BmapTilePoint.fromLngLat(lngLat, 16).toBmapTilePoint(128, 128);
        System.out.println(bmapTilePoint);

        LngLat ll = new LngLat(121.582981, 31.199571, LngLat.Map.amap);
        System.out.println(AmapTile.fromLngLat(ll, 15));

        System.out.println(new AmapTile(27445, 13392, 15).toLngLat());
        System.out.println(new BmapTile(13225, 3553, 16).toBmapTilePoint(128,128).toLngLat());
    }

}
