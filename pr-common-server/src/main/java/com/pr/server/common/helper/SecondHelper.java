//package com.pr.server.common.helper;
//
//
//import com.qbe.contract.state.schema.state.PremiumState;
//import com.qbe.contract.state.schema.state.QBEStatus;
//import net.corda.core.contracts.StateAndRef;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SecondHelper {
//    public static  Map<String,Integer> CountsHelperWithParty(StateAndRef<PremiumState> premiumState) {
//        Map<String,Integer> DashBoardMapWithParty=new HashMap<String, Integer>();
//        String collectionType = premiumState.getState().getData().getCollectionType();
//        LocalDateTime SOSettlementDueDate = LocalDateTime.now();
//        LocalDateTime MACSettlementDueDate = LocalDateTime.now();
//        LocalDateTime POSettlementDueDate = LocalDateTime.now();
//        if (null!=premiumState.getState().getData().getSOSettlementDueDate()) {
//            SOSettlementDueDate = premiumState.getState().getData().getSOSettlementDueDate();
//        }
//        if(null!=premiumState.getState().getData().getMACSettlementDueDate()) {
//            MACSettlementDueDate = premiumState.getState().getData().getMACSettlementDueDate();
//        }
//        if(null!=premiumState.getState().getData().getPOSettlementDueDate()) {
//            POSettlementDueDate = premiumState.getState().getData().getPOSettlementDueDate();
//        }
//        LocalDateTime today = LocalDateTime.now();
//        /*long SOgapdays = SOSettlementDueDate.until(today, ChronoUnit.DAYS);
//        long MACgapdays = MACSettlementDueDate.until(today, ChronoUnit.DAYS);
//        long POgapdays = POSettlementDueDate.until(today, ChronoUnit.DAYS);*/
//        String Status = premiumState.getState().component1().getStatus().toString();
//        QBEStatus qbeStatus = QBEStatus.fromText(Status);
//        if (collectionType.equalsIgnoreCase("Local")) {
//            switch (qbeStatus) {
//                //Case statements
//                case MAC_ALLOCATION_PENDING:
//                    boolean macAllow = getAllowValue(Status, MACSettlementDueDate,today);
//                    if (macAllow)
//                        pushValueintoMap(collectionType, Status, DashBoardMapWithParty);
//                    break;
//                case SO_ALLOCATION_PENDING:
//                    boolean soAllow = getAllowValue(Status, SOSettlementDueDate,today);
//                    if (soAllow)
//                        pushValueintoMap(collectionType, Status, DashBoardMapWithParty);
//                    break;
//                case PO_ALLOCATION_PENDING:
//                    boolean poAllow = getAllowValue(Status, POSettlementDueDate, today);
//                    if (poAllow)
//                        pushValueintoMap(collectionType, Status, DashBoardMapWithParty);
//                    break;
//                default:
//                    pushValueintoMap(collectionType, Status, DashBoardMapWithParty);
//            }
//        } else if (collectionType.equalsIgnoreCase("Central")) {
//            switch (qbeStatus) {
//                //Case statements
//                case MAC_ALLOCATION_PENDING:
//                    boolean macAllow = getAllowValue(Status,MACSettlementDueDate,today);
//                    if (macAllow)
//                        pushValueintoMap(collectionType, Status, DashBoardMapWithParty);
//                    break;
//                case SO_ALLOCATION_PENDING:
//                    boolean soAllow = getAllowValue(Status, SOSettlementDueDate,today);
//                    if (soAllow)
//                        pushValueintoMap(collectionType, Status, DashBoardMapWithParty);
//                    break;
//                case PO_ALLOCATION_PENDING:
//                    boolean poAllow = getAllowValue(Status, POSettlementDueDate, today);
//                    if (poAllow)
//                        pushValueintoMap(collectionType, Status, DashBoardMapWithParty);
//                    break;
//                default:
//                    pushValueintoMap(collectionType, Status, DashBoardMapWithParty);
//            }
//        }
//        return DashBoardMapWithParty;
//    }
//
//    private static boolean getAllowValue(String Status, LocalDateTime settlementDate, LocalDateTime today)
//    {
//        if(Status.equalsIgnoreCase("MAC_ALLOCATION_PENDING"))
//            return true;
//        else if(Status.equalsIgnoreCase("SO_ALLOCATION_PENDING") && settlementDate.isBefore(today) )
//            return true;
//        else if(Status.equalsIgnoreCase("PO_ALLOCATION_PENDING") && settlementDate.isBefore(today))
//            return true;
//
//        return false;
//    }
//
//    private static boolean pushValueintoMap(String collectionType,String Status, Map<String,Integer> DashBoardMap)
//    {
//        boolean isKeyPresent = DashBoardMap.containsKey(collectionType + ":" + Status);
//        DashBoardMap.containsKey(collectionType + ":" + Status);
//        int dashBoardValue=0;
//        if (isKeyPresent) {
//            dashBoardValue = DashBoardMap.get(collectionType + ":" + Status);
//            dashBoardValue++;
//            DashBoardMap.put(collectionType + ":" + Status, dashBoardValue);
//        } else {
//            DashBoardMap.put(collectionType + ":" + Status, 1);
//        }
//        return true;
//    }
//}
