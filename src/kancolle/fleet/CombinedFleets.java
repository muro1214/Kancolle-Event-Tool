package kancolle.fleet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import kancolle.structure.FleetType;
import kancolle.structure.ShipType;

public class CombinedFleets {

    public static boolean isConditionOK1(final FleetType fleetType, final String type11, final String type12,
            final String type13, final String type14, final String type15, final String type16) {

        // 第一艦隊の6隻がまだ種別すら決まってないときは、とりあえず問題なしにしておく
        if (isNotSelected(type11, type12, type13, type14, type15, type16)) {
            return true;
        }

        // 第一の旗艦に潜水艦(潜水母艦)はNG。連合艦隊の種別は関係ない
        if (type11.equals(ShipType.SUBMARINE.typeName()) || type11.equals(ShipType.SUBMARINE_TENDER.typeName())) {
            return false;
        }

        boolean flag = false;
        Map<String, Long> typeMap = groupingBy(Arrays.asList(type11, type12, type13, type14, type15, type16));
        if (fleetType == FleetType.CARRIER_TASK_FORCE) {
            flag = carrierTaskForce1(typeMap);
        } else if (fleetType == FleetType.SURFACE_TASK_FORCE) {
            flag = surfaceTaskForce1(typeMap);
        } else if (fleetType == FleetType.TRANSPORT_ESCORT) {
            flag = transportEscort1(typeMap);
        }

        return flag;
    }

    public static boolean isConditionOK2(final FleetType fleetType, final String type21, final String type22,
            final String type23, final String type24, final String type25, final String type26) {

        // 第二艦隊の6隻がまだ種別すら決まってないときは、とりあえず問題なしにしておく
        if (isNotSelected(type21, type22, type23, type24, type25, type26)) {
            return true;
        }

        // 第二の旗艦に潜水艦(潜水母艦)はNG。連合艦隊の種別は関係ない
        if (type21.equals(ShipType.SUBMARINE.typeName()) || type21.equals(ShipType.SUBMARINE_TENDER.typeName())) {
            return false;
        }

        boolean flag = false;
        Map<String, Long> typeMap = groupingBy(Arrays.asList(type21, type22, type23, type24, type25, type26));
        if (fleetType == FleetType.CARRIER_TASK_FORCE) {
            flag = carrierTaskForce2(typeMap);
        } else if (fleetType == FleetType.SURFACE_TASK_FORCE) {
            flag = surfaceTaskForce2(typeMap);
        } else if (fleetType == FleetType.TRANSPORT_ESCORT) {
            flag = transportEscort2(typeMap);
        }

        return flag;
    }

    private static boolean isNotSelected(final String type1, final String type2, final String type3,
            final String type4, final String type5, final String type6) {
        return type1.equals("艦種") || type2.equals("艦種") || type3.equals("艦種") ||
                type4.equals("艦種") || type5.equals("艦種") || type6.equals("艦種");
    }

    private static boolean carrierTaskForce1(final Map<String, Long> typeMap) {
        long carriers = typeMap.getOrDefault(ShipType.AIRCRAFT_CARRIER.typeName(), 0L)
                + typeMap.getOrDefault(ShipType.LIGHT_AIRCRAFT_CARRIER.typeName(), 0L)
                + typeMap.getOrDefault(ShipType.ARMORED_AIRCRAFT_CARRIER.typeName(), 0L);
        if(carriers < 2L || carriers > 4L){
            return false;
        }

        long battleShips = typeMap.getOrDefault(ShipType.BATTLESIHIP.typeName(), 0L)
                + typeMap.getOrDefault(ShipType.AVIATION_BATTLESHIP, 0L);
        if(battleShips > 2L){
            return false;
        }

        long submarines = typeMap.getOrDefault(ShipType.SUBMARINE.typeName(), 0L)
                + typeMap.getOrDefault(ShipType.SUBMARINE_TENDER.typeName(), 0L);
        if(submarines > 4L){
            return false;
        }

        return true;
    }

    private static boolean carrierTaskForce2(final Map<String, Long> typeMap) {
        long lightCruiser = removeOrDefault(typeMap, ShipType.LIGHT_CRUISER.typeName(), 0L);
        if(lightCruiser != 1L){
            return false;
        }

        long destroyers = removeOrDefault(typeMap, ShipType.DESTROYER.typeName(), 0L);
        if(destroyers < 2L || destroyers > 5L){
            return false;
        }

        long heavyCruisers = removeOrDefault(typeMap, ShipType.HEAVY_CRUISER.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.AIRCRAFT_CRUISER.typeName(), 0L);
        if(heavyCruisers > 2L){
            return false;
        }

        long seaplane = removeOrDefault(typeMap, ShipType.SEAPLANE_CARRIER.typeName(), 0L);
        if(seaplane > 1L){
            return false;
        }

        long lightCarrier = removeOrDefault(typeMap, ShipType.LIGHT_AIRCRAFT_CARRIER.typeName(), 0L);
        if(lightCarrier > 1L){
            return false;
        }

        long carrier = removeOrDefault(typeMap, ShipType.AIRCRAFT_CARRIER.typeName(), 0L);
        if(carrier != 0L){
            return false;
        }

        long battleShips = removeOrDefault(typeMap, ShipType.BATTLESIHIP.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.AVIATION_BATTLESHIP.typeName(), 0L);
        if(battleShips > 2L){
            return false;
        }

        long submarines = removeOrDefault(typeMap, ShipType.SUBMARINE.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.SUBMARINE_TENDER.typeName(), 0L);
        if(submarines > 3L){
            return false;
        }

        long others = typeMap.values().stream().mapToLong(x -> x).sum();
        if(others > 3L){
            return false;
        }

        return false;
    }

    private static boolean surfaceTaskForce1(final Map<String, Long> typeMap) {


        return false;
    }

    private static boolean surfaceTaskForce2(final Map<String, Long> typeMap) {


        return false;
    }

    private static boolean transportEscort1(final Map<String, Long> typeMap) {


        return false;
    }

    private static boolean transportEscort2(final Map<String, Long> typeMap) {


        return false;
    }

    private static Map<String, Long> groupingBy(List<String> types){
        return types.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
    }

    private static <K, V> V removeOrDefault(Map<K, V> map, K key, V defaultValue){
        V value = map.remove(key);

        return Objects.isNull(value) ? defaultValue : value;
    }
}
