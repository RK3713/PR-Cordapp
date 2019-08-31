package com.pr.server.common.helper;

import com.pr.contract.state.schema.schema.PRSchemaV1;
import com.pr.contract.state.schema.states.PRState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.services.Vault;
import net.corda.core.node.services.vault.Builder;
import net.corda.core.node.services.vault.CriteriaExpression;
import net.corda.core.node.services.vault.PageSpecification;
import net.corda.core.node.services.vault.QueryCriteria;
import net.corda.core.serialization.CordaSerializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import static net.corda.core.node.services.vault.QueryCriteriaUtils.DEFAULT_PAGE_NUM;
import static net.corda.core.node.services.vault.QueryCriteriaUtils.DEFAULT_PAGE_SIZE;


@CordaSerializable
public class PRControllerHelper {

    private final static Logger logger = LoggerFactory.getLogger(PRControllerHelper.class);

    /**
     * View PrRequest by requestId
     */

    public static List<StateAndRef<PRState>> getPrStateFromRequestId(String requestId, CordaRPCOps rpcOps)
            throws NoSuchFieldException {


        QueryCriteria generalCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED);
        Field PrIdField = PRSchemaV1.PersistentPR.class.getDeclaredField("id");

        logger.info(" ID is " + PrIdField.toString());
        logger.info(" ID is " + requestId);



            CriteriaExpression PrIdIndex = Builder.equal(PrIdField, requestId);

        logger.info(" see here " + PrIdIndex.toString());

            QueryCriteria customCriteria = new QueryCriteria.VaultCustomQueryCriteria<>(PrIdIndex);
        logger.info(" see here 1");
            QueryCriteria criteria = generalCriteria.and(customCriteria);
        logger.info(" see here 2");
            PageSpecification pageSpecification;
        logger.info(" see here 3");
            Vault.Page<PRState> results;
        logger.info(" see here 4");
            List<StateAndRef<PRState>> states = new ArrayList<>();
        logger.info(" see here 5");
            Integer pageNum = DEFAULT_PAGE_NUM;
        logger.info(" see here 6");
            do {
                pageSpecification = new PageSpecification(pageNum, DEFAULT_PAGE_SIZE);
                logger.info(" see here 7");
                results = rpcOps.vaultQueryByWithPagingSpec(PRState.class, criteria, pageSpecification);
                logger.info(" see here 8");
                states.addAll(results.getStates());
                logger.info(" see here 9");
                pageNum++;
                logger.info(" see here 10");
            } while ((pageSpecification.getPageSize() * (pageNum)) <= results.getTotalStatesAvailable());
            if (results.getStates().size() > 0) {
                logger.info(" results.getStates()"+results.getStates());
                return results.getStates();

            } else {
                logger.info(" ArrayList<>()");
                return new ArrayList<>();
            }




    }

    /**
     * It displays the LocalPolicy List information at Dash Board with Party as parameter
     */
/*    public static List<StateAndRef<PremiumState>> getLocalPolicyInfofromDBwithParty(String status, String collectionType, AbstractParty party,
                                                                                    CordaRPCOps rpcOps) throws NoSuchFieldException {
        QueryCriteria generalCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED);
        Field StatusIdField = PremiumStateSchemaV1.PersistentPremium.class.getDeclaredField("Status");
        QBEStatus qbeStatus = QBEStatus.fromText(status);
        CriteriaExpression StatusIdIndex = Builder.equal(StatusIdField, qbeStatus);
        Field CollectionTypeIdField = PremiumStateSchemaV1.PersistentPremium.class.getDeclaredField("CollectionType");
        CriteriaExpression CollectionIdIndex = Builder.equal(CollectionTypeIdField, collectionType);
        Field PartyIdField = PremiumStateSchemaV1.PersistentPremium.class.getDeclaredField("SOParty");
        CriteriaExpression PartyIdIndex = Builder.equal(PartyIdField, party);
        QueryCriteria customCriteria2 = new QueryCriteria.VaultCustomQueryCriteria<>(PartyIdIndex);
        QueryCriteria customCriteria = new QueryCriteria.VaultCustomQueryCriteria<>(StatusIdIndex);
        QueryCriteria customCriteria1 = new QueryCriteria.VaultCustomQueryCriteria<>(CollectionIdIndex);
        QueryCriteria criteria = generalCriteria.and(customCriteria).and(customCriteria1).and(customCriteria2);
//        Vault.Page<PremiumState> results = rpcOps.vaultQueryByCriteria(criteria, PremiumState.class);
        PageSpecification pageSpecification;
        Vault.Page<PremiumState> results;
        List<StateAndRef<PremiumState>> states = new ArrayList<>();
        List<StateAndRef<PremiumState>> statesNew = new ArrayList<>();
        Integer pageNum = DEFAULT_PAGE_NUM;
        do {
            pageSpecification = new PageSpecification(pageNum, DEFAULT_PAGE_SIZE);
            results = rpcOps.vaultQueryByWithPagingSpec(PremiumState.class, criteria, pageSpecification);
            states.addAll(results.getStates());
            pageNum++;
        } while ((pageSpecification.getPageSize() * (pageNum)) <= results.getTotalStatesAvailable());

        for (StateAndRef<PremiumState> check : states) {
            if (check.getState().getData().getStatus().equals(QBEStatus.SO_ALLOCATION_PENDING)
                    && check.getState().getData().getSOSettlementDueDate().isAfter(LocalDateTime.now())
                    || check.getState().getData().getStatus().equals(QBEStatus.MAC_ALLOCATION_PENDING)
                    && check.getState().getData().getMACSettlementDueDate().isAfter(LocalDateTime.now())) {
                statesNew.add(check);
            }
        }

        if (states.size() > 0) {
            if (statesNew.size() > 0) {
                for (StateAndRef<PremiumState> checkNew : statesNew) {
                    states.remove(checkNew);
                }
            }
            return states;
        } else {
            return new ArrayList<>();
        }
    }

    *//**
     * It displays the LocalPolicy List information at Dash Board withOut Party as parameter
     *//*
    public static List<StateAndRef<PremiumState>> getLocalPolicyInfofromDBwithoutParty(String status, String collectionType,
                                                                                       CordaRPCOps rpcOps) throws NoSuchFieldException {
        QueryCriteria generalCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED);
        Field StatusIdField = PremiumStateSchemaV1.PersistentPremium.class.getDeclaredField("Status");
        QBEStatus qbeStatus = QBEStatus.fromText(status);
        CriteriaExpression StatusIdIndex = Builder.equal(StatusIdField, qbeStatus);
        Field CollectionTypeIdField = PremiumStateSchemaV1.PersistentPremium.class.getDeclaredField("CollectionType");
        CriteriaExpression CollectionIdIndex = Builder.equal(CollectionTypeIdField, collectionType);
        QueryCriteria customCriteria = new QueryCriteria.VaultCustomQueryCriteria<>(StatusIdIndex);
        QueryCriteria customCriteria1 = new QueryCriteria.VaultCustomQueryCriteria<>(CollectionIdIndex);
        QueryCriteria criteria = generalCriteria.and(customCriteria).and(customCriteria1);
//        Vault.Page<PremiumState> results = rpcOps.vaultQueryByCriteria(criteria, PremiumState.class);
        PageSpecification pageSpecification;
        Vault.Page<PremiumState> results;
        List<StateAndRef<PremiumState>> states = new ArrayList<>();
        List<StateAndRef<PremiumState>> statesNew = new ArrayList<>();
        Integer pageNum = DEFAULT_PAGE_NUM;
        do {
            pageSpecification = new PageSpecification(pageNum, DEFAULT_PAGE_SIZE);
            results = rpcOps.vaultQueryByWithPagingSpec(PremiumState.class, criteria, pageSpecification);
            states.addAll(results.getStates());
            pageNum++;
        } while ((pageSpecification.getPageSize() * (pageNum)) <= results.getTotalStatesAvailable());
        for (StateAndRef<PremiumState> check : states) {
            if ((check.getState().getData().getStatus().equals(QBEStatus.SO_ALLOCATION_PENDING)
                    && check.getState().getData().getSOSettlementDueDate().isAfter(LocalDateTime.now()))
                    || (check.getState().getData().getStatus().equals(QBEStatus.PO_ALLOCATION_PENDING)
                    && check.getState().getData().getPOSettlementDueDate().isAfter(LocalDateTime.now()))
            ) {
                statesNew.add(check);
            }
        }

        if (states.size() > 0) {
            if (statesNew.size() > 0) {
                for (StateAndRef<PremiumState> checkNew : statesNew) {
                    states.remove(checkNew);
                }
            }
            if (status.equalsIgnoreCase("SO_ALLOCATION_PENDING")) {
                Comparator<StateAndRef<PremiumState>> compareByDate = (StateAndRef<PremiumState> o1, StateAndRef<PremiumState> o2)
                        -> o1.getState().getData().getSOSettlementDueDate()
                        .compareTo(o2.getState().getData().getSOSettlementDueDate());
                Collections.sort(states, compareByDate);
            } else if (status.equalsIgnoreCase("MAC_ALLOCATION_PENDING")) {
                Comparator<StateAndRef<PremiumState>> compareByDate = (StateAndRef<PremiumState> o1, StateAndRef<PremiumState> o2)
                        -> o1.getState().getData().getMACSettlementDueDate()
                        .compareTo(o2.getState().getData().getMACSettlementDueDate());
                Collections.sort(states, compareByDate);
            } else if (status.equalsIgnoreCase("PO_ALLOCATION_PENDING")) {
                Comparator<StateAndRef<PremiumState>> compareByDate = (StateAndRef<PremiumState> o1, StateAndRef<PremiumState> o2)
                        -> o1.getState().getData().getPOSettlementDueDate()
                        .compareTo(o2.getState().getData().getPOSettlementDueDate());
                Collections.sort(states, compareByDate);
            }
            return states;
        } else {
            return new ArrayList<>();
        }
    }

    *//**
     * View Local Policies by MNPolicyId, It will fetch local policies related to MNPolicy
     *//*
    public static List<StateAndRef<PremiumState>> getPremiumStateFromMNPolicyId(String PolicyId, CordaRPCOps rpcOps) throws NoSuchFieldException {
        QueryCriteria generalCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED);
        Field PolicyIdField = PremiumStateSchemaV1.PersistentPremium.class.getDeclaredField("MNPolicyID");
        CriteriaExpression PolicyIdIndex = Builder.equal(PolicyIdField, PolicyId);
        QueryCriteria customCriteria = new QueryCriteria.VaultCustomQueryCriteria<>(PolicyIdIndex);
        QueryCriteria criteria = generalCriteria.and(customCriteria);
        PageSpecification pageSpecification;
        Vault.Page<PremiumState> results;
        List<StateAndRef<PremiumState>> states = new ArrayList<>();
        Integer pageNum = DEFAULT_PAGE_NUM;
        do {
            pageSpecification = new PageSpecification(pageNum, DEFAULT_PAGE_SIZE);
            results = rpcOps.vaultQueryByWithPagingSpec(PremiumState.class, criteria, pageSpecification);
            states.addAll(results.getStates());
            pageNum++;
        } while ((pageSpecification.getPageSize() * (pageNum)) <= results.getTotalStatesAvailable());
//        Vault.Page<PremiumState> results = rpcOps.vaultQueryByCriteria(criteria, PremiumState.class);
        if (results.getStates().size() > 0) {
            return results.getStates();
        } else {
            return new ArrayList<>();
        }
    }

    *//**
     * View Local Policies by Linearid
     *//*
    public static List<StateAndRef<PremiumState>> getPremiumReqFromLinearId(String id, CordaRPCOps rpcOps) {
        UniqueIdentifier uniqueIdentifier = UniqueIdentifier.Companion.fromString(id);

        Set<Class<PremiumState>> contractStateTypes
                = new HashSet(Collections.singletonList(PremiumState.class));
        List<StateAndRef<PremiumState>> updatedStateList;

        QueryCriteria linearCriteria = new QueryCriteria.LinearStateQueryCriteria(null, Arrays.asList(uniqueIdentifier),
                Vault.StateStatus.UNCONSUMED, contractStateTypes);


        Vault.Page<PremiumState> results = rpcOps.vaultQueryByCriteria(linearCriteria, PremiumState.class);

        if (results.getStates().size() > 0) {
            return results.getStates();
        } else {
            return new ArrayList<>();
        }
    }

    *//**
     * It displays the status information(ex: SO_PENDING_CEDING etc.,) at Dash Board
     *//*
    public static Map<String, Integer> getCountsForDashBoardWithParty(AbstractParty party, CordaRPCOps rpcOps) throws NoSuchFieldException {

        Map<String, Integer> DashBoardMapWithParty = new HashMap<String, Integer>();
        QueryCriteria generalCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED);
        Field PartyIdField = null;
        if (party.nameOrNull().getOrganisation().equalsIgnoreCase("SO1")
                || party.nameOrNull().getOrganisation().equalsIgnoreCase("SO2"))
            PartyIdField = PremiumStateSchemaV1.PersistentPremium.class.getDeclaredField("SOParty");
        else if (party.nameOrNull().getOrganisation().equalsIgnoreCase("MAC"))
            PartyIdField = PremiumStateSchemaV1.PersistentPremium.class.getDeclaredField("MACParty");
        else
            PartyIdField = PremiumStateSchemaV1.PersistentPremium.class.getDeclaredField("POParty");

        CriteriaExpression PartyIdIndex = Builder.equal(PartyIdField, party);
        QueryCriteria customCriteria = new QueryCriteria.VaultCustomQueryCriteria<>(PartyIdIndex);
        QueryCriteria criteria = generalCriteria.and(customCriteria);
        PageSpecification pageSpecification;
        Vault.Page<PremiumState> results;
        List<StateAndRef<PremiumState>> states = new ArrayList<>();
        Integer pageNum = DEFAULT_PAGE_NUM;
        do {
            pageSpecification = new PageSpecification(pageNum, DEFAULT_PAGE_SIZE);
            results = rpcOps.vaultQueryByWithPagingSpec(PremiumState.class, criteria, pageSpecification);
            states.addAll(results.getStates());
            pageNum++;
        } while ((pageSpecification.getPageSize() * (pageNum)) <= results.getTotalStatesAvailable());

        int dashBoardValue = 0;
        LocalDateTime SOSettlementDueDate = null, MACSettlementDueDate = null, POSettlementDueDate = null;
        for (StateAndRef<PremiumState> premiumState : states) {
            DashBoardMapWithParty = SecondHelper.CountsHelperWithParty(premiumState);

        }
        return DashBoardMapWithParty;
    }

    *//**
     * It displays the status information(ex: SO_PENDING_CEDING etc.,) at Dash Board without Party
     *//*
    public static Map<String, Integer> getLocalPolicyInfofromDBwithoutParty(CordaRPCOps rpcOps) throws NoSuchFieldException {

        Map<String, Integer> DashBoardMap = new HashMap<String, Integer>();
        QueryCriteria generalCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED);
        PageSpecification pageSpecification;
        Vault.Page<PremiumState> results;
        List<StateAndRef<PremiumState>> states = new ArrayList<>();
        Integer pageNum = DEFAULT_PAGE_NUM;
        do {
            pageSpecification = new PageSpecification(pageNum, DEFAULT_PAGE_SIZE);
            results = rpcOps.vaultQueryByWithPagingSpec(PremiumState.class, generalCriteria, pageSpecification);
            states.addAll(results.getStates());
            pageNum++;
        } while ((pageSpecification.getPageSize() * (pageNum)) <= results.getTotalStatesAvailable());


        int dashBoardValue = 0;
        for (StateAndRef<PremiumState> premiumState : states) {
            String Status = premiumState.getState().component1().getStatus().toString();
            String collectionType = premiumState.getState().getData().getCollectionType();
            QBEStatus qbeStatus = QBEStatus.fromText(Status);
            LocalDateTime SOSettlementDueDate = LocalDateTime.now();
            LocalDateTime MACSettlementDueDate = LocalDateTime.now();
            LocalDateTime POSettlementDueDate = LocalDateTime.now();
            if (null != premiumState.getState().getData().getSOSettlementDueDate()) {
                SOSettlementDueDate = premiumState.getState().getData().getSOSettlementDueDate();
            }
            if (null != premiumState.getState().getData().getMACSettlementDueDate()) {
                MACSettlementDueDate = premiumState.getState().getData().getMACSettlementDueDate();
            }
            if (null != premiumState.getState().getData().getPOSettlementDueDate()) {
                POSettlementDueDate = premiumState.getState().getData().getPOSettlementDueDate();
            }
            LocalDateTime today = LocalDateTime.now();
            if (collectionType.equalsIgnoreCase("Local")) {
                switch (qbeStatus) {
                    //Case statements
                    case MAC_ALLOCATION_PENDING:
                        boolean macAllow = getAllowValue(Status, MACSettlementDueDate, today);
                        if (macAllow)
                            pushValueintoMap(collectionType, Status, DashBoardMap);
                        break;
                    case SO_ALLOCATION_PENDING:
                        boolean soAllow = getAllowValue(Status, SOSettlementDueDate, today);
                        if (soAllow)
                            pushValueintoMap(collectionType, Status, DashBoardMap);
                        break;
                    case PO_ALLOCATION_PENDING:
                        boolean poAllow = getAllowValue(Status, POSettlementDueDate, today);
                        if (poAllow)
                            pushValueintoMap(collectionType, Status, DashBoardMap);
                        break;
                    default:
                        pushValueintoMap(collectionType, Status, DashBoardMap);
                }
            } else if (collectionType.equalsIgnoreCase("Central")) {
                switch (qbeStatus) {
                    //Case statements
                    case MAC_ALLOCATION_PENDING:
                        boolean macAllow = getAllowValue(Status, MACSettlementDueDate, today);
                        if (macAllow)
                            pushValueintoMap(collectionType, Status, DashBoardMap);
                        break;
                    case SO_ALLOCATION_PENDING:
                        boolean soAllow = getAllowValue(Status, SOSettlementDueDate, today);
                        if (soAllow)
                            pushValueintoMap(collectionType, Status, DashBoardMap);
                        break;
                    case PO_ALLOCATION_PENDING:
                        boolean poAllow = getAllowValue(Status, POSettlementDueDate, today);
                        if (poAllow)
                            pushValueintoMap(collectionType, Status, DashBoardMap);
                        break;
                    default:
                        pushValueintoMap(collectionType, Status, DashBoardMap);
                }

            }

        }
        return DashBoardMap;
    }

    public static String sendNotification(PremiumState state, AbstractParty party) throws IOException {
        String partyName = "Invalid Party";
        String dateCheck = LocalDate.now().toString();
        if(party!=null) {
            if (party.toString().contains("O=SO1")) {
                partyName = QbeConstant.SO1;
                dateCheck = state.getSOAssumedAllocationDate().toString().isEmpty()?
                        LocalDate.now().toString():state.getSOAssumedAllocationDate().toString();
            }
            else if (party.toString().contains("O=SO2")) {
                partyName = QbeConstant.SO2;
                dateCheck = state.getSOAssumedAllocationDate().toString().isEmpty()?
                        LocalDate.now().toString():state.getSOAssumedAllocationDate().toString();
            }else if (party.toString().contains("O=PO")) {
                partyName = QbeConstant.PO;
                dateCheck = state.getPOAssumedAllocationDate().toString().isEmpty()?
                        LocalDate.now().toString():state.getPOAssumedAllocationDate().toString();
            }else if (party.toString().contains("O=MAC")) {
                partyName = QbeConstant.MAC;
                dateCheck = state.getMACAssumedAllocationDate().toString().isEmpty()?
                        LocalDate.now().toString():state.getMACAssumedAllocationDate().toString();
            }else if (party.toString().contains("O=MCC")) {
                partyName = QbeConstant.MCC;
            }
        }
        else
            partyName = "";

        ArrayList<String> dateList=new ArrayList<>();
        if (dateCheck.contains("T")) {
            String[] date = dateCheck.split("T");
            for (String dt : date) {
                if (!dt.contains("Z")) {
                    dateList.add(dt);
                }
            }
        }
        else
            dateList.add(dateCheck);

        logger.info("PartyName: " + partyName + "date" + dateList.get(0));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String emailbody = "MN Policy ID: " + state.getMNPolicyID() + System.lineSeparator() +"Policy ID: " +
                state.getPolicyNumber() + System.lineSeparator() + partyName +
                " has allocated Premium: " + state.getPremium() + " Euros on " + (dateList.get(0));
        HttpURLConnection conn = null;
        String output = null;
        try {

            URL url = new URL("http://localhost:8085/qbe/email/sendNotification");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/plain");

            //String input = "Test email, do not ignore";

            OutputStream os = conn.getOutputStream();
            os.write(emailbody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                return output;
            }
            
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                conn.disconnect();
            }
        return output;
    }


    private static boolean getAllowValue(String Status, LocalDateTime settlementDate, LocalDateTime today)
    {
        if(Status.equalsIgnoreCase("MAC_ALLOCATION_PENDING"))
            return true;
        else if(Status.equalsIgnoreCase("SO_ALLOCATION_PENDING") && settlementDate.isBefore(today) )
            return true;
        else if(Status.equalsIgnoreCase("PO_ALLOCATION_PENDING") && settlementDate.isBefore(today))
            return true;

        return false;
    }

    private static boolean pushValueintoMap(String collectionType,String Status, Map<String,Integer> DashBoardMap)
    {
        boolean isKeyPresent = DashBoardMap.containsKey(collectionType + ":" + Status);
        DashBoardMap.containsKey(collectionType + ":" + Status);
        int dashBoardValue=0;
        if (isKeyPresent) {
            dashBoardValue = DashBoardMap.get(collectionType + ":" + Status);
            dashBoardValue++;
            DashBoardMap.put(collectionType + ":" + Status, dashBoardValue);
        } else {
            DashBoardMap.put(collectionType + ":" + Status, 1);
        }
        return true;
    }*/

}
