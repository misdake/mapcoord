import com.rs.util.map.LngLat;
import com.rs.util.map.amap.AmapTile;
import com.rs.util.map.bmap.BmapGlPoint;
import com.rs.util.map.bmap.BmapTile;
import com.rs.util.map.bmap.BmapTilePoint;

public class Main {

    public static void main(String[] args) {
        LngLat ll = new LngLat(121.475391,31.210567, LngLat.Map.amap);
        AmapTile tile = AmapTile.fromLngLat(ll, 15);
        LngLat ll2 = tile.toAmapGlPoint(0.1f, 0.1f).toLngLat();
        System.out.println(tile);
        System.out.println(ll2);
    }

}
