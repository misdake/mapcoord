import com.rs.util.map.LngLat;

public class Main {

    public static void main(String[] args) {
        System.out.println(LngLat.parse("121.584798,31.199877", LngLat.Map.tmap).convertTo(LngLat.Map.amap));
        System.out.println(LngLat.parse("121.584798,31.199877", LngLat.Map.tmap).convertTo(LngLat.Map.bmap));
    }

}
