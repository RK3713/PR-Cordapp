package com.pr.common.flow;

import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.transactions.SignedTransaction;

@InitiatingFlow
public abstract class RequestFormFlow extends FlowLogic<SignedTransaction> {
}
