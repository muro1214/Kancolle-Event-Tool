package kancolle.fleet;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import kancolle.structure.FleetType;
import kancolle.structure.ShipType;

public class CombinedFleets {

    public static boolean isConditionOK1(final FleetType fleetType, final List<String> types) {
        Logger.getGlobal().info(String.format("FleetType = %s, Types = %s", fleetType.typeName(),
                String.join(", ", types)));

        // 第一艦隊の6隻がまだ種別すら決まってないときは、とりあえず問題なしにしておく
        if (types.contains("艦種")) {
            return true;
        }

        // 第一の旗艦に潜水艦(潜水母艦)はNG。連合艦隊の種別は関係ない
        if (types.get(0).equals(ShipType.SUBMARINE.typeName()) ||
                types.get(0).equals(ShipType.SUBMARINE_TENDER.typeName())) {
            return false;
        }

        boolean flag = false;
        Map<String, Long> typeMap = groupingBy(types);
        if (fleetType == FleetType.CARRIER_TASK_FORCE) {
            flag = carrierTaskForce1(typeMap);
        } else if (fleetType == FleetType.SURFACE_TASK_FORCE) {
            flag = surfaceTaskForce1(typeMap);
        } else if (fleetType == FleetType.TRANSPORT_ESCORT) {
            flag = transportEscort1(typeMap);
        }

        return flag;
    }

    public static boolean isConditionOK2(final FleetType fleetType, final List<String> types) {
        Logger.getGlobal().info(String.format("FleetType = %s, Types = %s", fleetType.typeName(),
                String.join(", ", types)));

        // 第二艦隊の6隻がまだ種別すら決まってないときは、とりあえず問題なしにしておく
        if (types.contains("艦種")) {
            return true;
        }

        // 第二の旗艦に潜水艦(潜水母艦)はNG。連合艦隊の種別は関係ない
        if (types.get(0).equals(ShipType.SUBMARINE.typeName()) ||
                types.get(0).equals(ShipType.SUBMARINE_TENDER.typeName())) {
            return false;
        }

        boolean flag = false;
        Map<String, Long> typeMap = groupingBy(types);
        if (fleetType == FleetType.CARRIER_TASK_FORCE) {
            flag = carrierTaskForce2(typeMap);
        } else if (fleetType == FleetType.SURFACE_TASK_FORCE) {
            flag = surfaceTaskForce2(typeMap);
        } else if (fleetType == FleetType.TRANSPORT_ESCORT) {
            if(!types.get(0).equals(ShipType.LIGHT_CRUISER.typeName()) &&
                    !types.get(0).equals(ShipType.TRAINING_CRUISER.typeName())){
                return false;
            }
            flag = transportEscort2(typeMap);
        }

        return flag;
    }

    private static boolean carrierTaskForce1(final Map<String, Long> typeMap) {
        long carriers = removeOrDefault(typeMap, ShipType.AIRCRAFT_CARRIER.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.LIGHT_AIRCRAFT_CARRIER.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.ARMORED_AIRCRAFT_CARRIER.typeName(), 0L);
        if(carriers < 2L || carriers > 4L){
            return false;
        }

        long battleShips = removeOrDefault(typeMap, ShipType.BATTLESHIP.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.AVIATION_BATTLESHIP.typeName(), 0L);
        if(battleShips > 2L){
            return false;
        }

        long submarines = removeOrDefault(typeMap, ShipType.SUBMARINE.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.SUBMARINE_TENDER.typeName(), 0L);
        if(submarines > 4L){
            return false;
        }

        long others = typeMap.values().stream().mapToLong(x -> x).sum();
        if(others > 4L){
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

        // 高速、低速の分類ができないのですべてOKとする。高速+があるのでしゃーない
        long battleShips = removeOrDefault(typeMap, ShipType.BATTLESHIP.typeName(), 0L)
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

        return true;
    }

    private static boolean surfaceTaskForce1(final Map<String, Long> typeMap) {
        long lightCruisers = removeOrDefault(typeMap, ShipType.LIGHT_CRUISER.typeName(), 0L);
        long torpedoCruisers = removeOrDefault(typeMap, ShipType.TORPEDO_CRUISER.typeName(), 0L);
        long heavyCruisers = removeOrDefault(typeMap, ShipType.HEAVY_CRUISER.typeName(), 0L);
        long aircraftCruisers = removeOrDefault(typeMap, ShipType.AIRCRAFT_CRUISER.typeName(), 0L);
        long battleShips = removeOrDefault(typeMap, ShipType.BATTLESHIP.typeName(), 0L);
        long aviations = removeOrDefault(typeMap, ShipType.AVIATION_BATTLESHIP.typeName(), 0L);
        long total = lightCruisers + torpedoCruisers + heavyCruisers + aircraftCruisers + battleShips + aviations;
        if(total < 2L){
            return false;
        }
        if(lightCruisers + torpedoCruisers > 6L){
            return false;
        }
        if(heavyCruisers + aircraftCruisers > 4L){
            return false;
        }
        if(battleShips + aviations > 4L){
            return false;
        }

        long carriers = removeOrDefault(typeMap, ShipType.AIRCRAFT_CARRIER.typeName(), 0L);
        long lightCarriers = removeOrDefault(typeMap, ShipType.LIGHT_AIRCRAFT_CARRIER.typeName(), 0L);
        if(carriers > 1L){
            return false;
        }
        if(lightCarriers > 2L){
            return false;
        }
        if(carriers != 0 && lightCarriers != 0){
            return false;
        }

        long submarines = removeOrDefault(typeMap, ShipType.SUBMARINE.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.SUBMARINE_TENDER.typeName(), 0L);
        if(submarines > 4L){
            return false;
        }

        long others = typeMap.values().stream().mapToLong(x -> x).sum();
        if(others > 4L){
            return false;
        }

        return true;
    }

    private static boolean surfaceTaskForce2(final Map<String, Long> typeMap) {
        long lightCruiser = removeOrDefault(typeMap, ShipType.LIGHT_CRUISER.typeName(), 0L);
        if(lightCruiser != 1){
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

        // 高速、低速の分類ができないのですべてOKとする。高速+があるのでしゃーない
        long battleShips = removeOrDefault(typeMap, ShipType.BATTLESHIP.typeName(), 0L)
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

        return true;
    }

    private static boolean transportEscort1(final Map<String, Long> typeMap) {
        long destroyers = removeOrDefault(typeMap, ShipType.DESTROYER.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.DESTROYER_ESCORT.typeName(), 0L);
        if(destroyers < 4L || destroyers > 6L){
            return false;
        }

        long lightCruisers = removeOrDefault(typeMap, ShipType.LIGHT_CRUISER.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.TRAINING_CRUISER.typeName(), 0L);
        if(lightCruisers > 2L){
            return false;
        }

        long aircraftCruisers = removeOrDefault(typeMap, ShipType.AIRCRAFT_CRUISER.typeName(), 0L);
        if(aircraftCruisers > 2L){
            return false;
        }

        long aviations = removeOrDefault(typeMap, ShipType.AVIATION_BATTLESHIP.typeName(), 0L);
        if(aviations > 2L){
            return false;
        }

        long seaplane = removeOrDefault(typeMap, ShipType.SEAPLANE_CARRIER.typeName(), 0L);
        if(seaplane > 2L){
            return false;
        }

        long amphibious = removeOrDefault(typeMap, ShipType.AMPHIBIOUS_ASSAULT_SHIP.typeName(), 0L);
        if(amphibious > 1L){
            return false;
        }

        // 護衛空母だけだが・・・enumがないので軽空母を許容
        long lightCarrier = removeOrDefault(typeMap, ShipType.LIGHT_AIRCRAFT_CARRIER.typeName(), 0L);
        if(lightCarrier > 1L){
            return false;
        }

        long submarineTender = removeOrDefault(typeMap, ShipType.SUBMARINE_TENDER.typeName(), 0L);
        if(submarineTender > 1L){
            return false;
        }

        long oilers = removeOrDefault(typeMap, ShipType.FLEET_OILER.typeName(), 0L);
        if(oilers > 2L){
            return false;
        }

        long others = typeMap.values().stream().mapToLong(x -> x).sum();
        if(others != 0L){
            return false;
        }

        return true;
    }

    private static boolean transportEscort2(final Map<String, Long> typeMap) {
        long lightCruisers = removeOrDefault(typeMap, ShipType.LIGHT_CRUISER.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.TRAINING_CRUISER.typeName(), 0L);
        if(lightCruisers < 1L || lightCruisers > 2L){
            return false;
        }

        long destroyers = removeOrDefault(typeMap, ShipType.DESTROYER.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.DESTROYER_ESCORT.typeName(), 0L);
        if(destroyers < 3L || destroyers > 5L){
            return false;
        }

        long heavyCruirsers = removeOrDefault(typeMap, ShipType.HEAVY_CRUISER.typeName(), 0L)
                + removeOrDefault(typeMap, ShipType.AIRCRAFT_CRUISER.typeName(), 0L);
        if(heavyCruirsers > 2L){
            return false;
        }

        long others = typeMap.values().stream().mapToLong(x -> x).sum();
        if(others != 0L){
            return false;
        }

        return true;
    }

    private static Map<String, Long> groupingBy(final List<String> types){
        return types.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
    }

    private static <K, V> V removeOrDefault(final Map<K, V> map, final K key, final V defaultValue){
        V value = map.remove(key);

        return Objects.isNull(value) ? defaultValue : value;
    }
}
