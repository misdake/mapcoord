import com.rs.util.map.LngLat;
import com.rs.util.map.amap.AmapTile;
import com.rs.util.map.bmap.BmapGlPoint;
import com.rs.util.map.bmap.BmapPoint;
import com.rs.util.map.bmap.BmapTile;
import com.rs.util.map.bmap.BmapTilePoint;

public class Main {

    public static void main(String[] args) {
        LngLat ll = new LngLat(121.475391,31.210567, LngLat.Map.amap);
        AmapTile tile = AmapTile.fromLngLat(ll, 15);
        LngLat ll2 = tile.toAmapGlPoint(0.1f, 0.1f).toLngLat();
        System.out.println(tile);
        System.out.println(ll2);

        double d = LngLat.distanceMeter(new LngLat(LngLat.Map.amap, 121.695248, 31.185779), new LngLat(LngLat.Map.amap, 121.697812, 31.186403));
        System.out.println(d);
    }

}
