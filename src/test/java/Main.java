import com.rs.util.map.LngLat;
import com.rs.util.map.bmap.BmapTilePoint;

public class Main {

    public static void main(String[] args) {
        LngLat lngLat = new LngLat(121.588817, 31.205884, LngLat.Map.bmap);
        BmapTilePoint bmapTilePoint = BmapTilePoint.fromLngLat(lngLat, 18);
        System.out.println(bmapTilePoint);
    }

}
