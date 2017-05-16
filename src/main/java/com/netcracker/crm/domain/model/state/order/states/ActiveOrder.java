package com.netcracker.crm.domain.model.state.order.states;


import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.state.order.OrderState;

import static com.netcracker.crm.domain.model.OrderStatus.ACTIVE;

/**
 * Created by bpogo on 5/9/2017.
 */
public class ActiveOrder extends OrderState {

    public ActiveOrder(Order order) {
        super(order);
        this.stateName = ACTIVE.getName();
        this.order.setStatus(ACTIVE);
    }

    @Override
    public History requestToPauseOrder() {
        History history = getOrderHistory(DESC_REQUEST_TO_PAUSE_ORDER);
        order.setState(new RequestToPauseOrder(order));

        return history;
    }

    @Override
    public History requestToDisableOrder() {
        History history = getOrderHistory(DESC_REQUEST_TO_DISABLE_ORDER);
        order.setState(new RequestToDisableOrder(order));

        return history;
    }
}
