package kancolle.fleet;

import kancolle.structure.ShipType;

public class CombinedFleets {

    public static boolean isConditionOK1(final FleetType fleetType, final String type11, final String type12,
            final String type13, final String type14, final String type15, final String type16){

        // 第一艦隊の6隻がまだ種別すら決まってないときは、とりあえず問題なしにしておく
        if(isNotSelected(type11, type12, type13, type14, type15, type16)){
            return true;
        }

        // 第一の旗艦に潜水艦(潜水母艦)はNG。連合艦隊の種別は関係ない
        if(type11.equals(ShipType.SUBMARINE.typeName()) || type11.equals(ShipType.SUBMARINE_TENDER.typeName())){
            return false;
        }

        boolean flag = false;
        if(fleetType == FleetType.CARRIER_TASK_FORCE){
            flag = carrierTaskForce1(type11, type12, type13, type14, type15, type16);
        }else if(fleetType == FleetType.SURFACE_TASK_FORCE){
            flag = surfaceTaskForce1(type11, type12, type13, type14, type15, type16);
        }else if(fleetType == FleetType.TRANSPORT_ESCORT){
            flag = transportEscort1(type11, type12, type13, type14, type15, type16);
        }

        return flag;
    }

    public static boolean isConditionOK2(final FleetType fleetType, final String type21, final String type22,
            final String type23, final String type24, final String type25, final String type26){

        // 第二艦隊の6隻がまだ種別すら決まってないときは、とりあえず問題なしにしておく
        if(isNotSelected(type21, type22, type23, type24, type25, type26)){
            return true;
        }

        // 第二の旗艦に潜水艦(潜水母艦)はNG。連合艦隊の種別は関係ない
        if(type21.equals(ShipType.SUBMARINE.typeName()) || type21.equals(ShipType.SUBMARINE_TENDER.typeName())){
            return false;
        }

        boolean flag = false;
        if(fleetType == FleetType.CARRIER_TASK_FORCE){
            flag = carrierTaskForce2(type21, type22, type23, type24, type25, type26);
        }else if(fleetType == FleetType.SURFACE_TASK_FORCE){
            flag = surfaceTaskForce2(type21, type22, type23, type24, type25, type26);
        }else if(fleetType == FleetType.TRANSPORT_ESCORT){
            flag = transportEscort2(type21, type22, type23, type24, type25, type26);
        }

        return flag;
    }

    private static boolean isNotSelected(final String type1, final String type2, final String type3,
            final String type4, final String type5, final String type6){
        return type1.equals("艦種") || type2.equals("艦種") || type3.equals("艦種") ||
                type4.equals("艦種") || type5.equals("艦種") || type6.equals("艦種");
    }

    private static boolean carrierTaskForce1(final String type11, final String type12,
            final String type13, final String type14, final String type15, final String type16){

        return false;
    }

    private static boolean carrierTaskForce2(final String type21, final String type22,
            final String type23, final String type24, final String type25, final String type26){

        return false;
    }

    private static boolean surfaceTaskForce1(final String type11, final String type12,
            final String type13, final String type14, final String type15, final String type16){

        return false;
    }

    private static boolean surfaceTaskForce2(final String type21, final String type22,
            final String type23, final String type24, final String type25, final String type26){

        return false;
    }

    private static boolean transportEscort1(final String type11, final String type12,
            final String type13, final String type14, final String type15, final String type16){

        return false;
    }

    private static boolean transportEscort2(final String type21, final String type22,
            final String type23, final String type24, final String type25, final String type26){

        return false;
    }
}
