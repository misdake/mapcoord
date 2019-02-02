import com.rs.util.map.LngLat;
import com.rs.util.map.amap.AmapGlPoint;
import com.rs.util.map.amap.AmapTile;

public class Main {

    public static void main(String[] args) {
        LngLat coordinate = new LngLat(114.17, 22.31, LngLat.Map.amap);
        System.out.println(coordinate);
        System.out.println(coordinate.convertTo(LngLat.System.WGS84));
        System.out.println(coordinate.convertTo(LngLat.Map.bmap));
        LngLat parsed = LngLat.parse("114.17, 22.31", LngLat.System.BD09);
        System.out.println(parsed);
    }

}
