package com.netcracker.crm.scheduler.searcher;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;

import java.util.List;

/**
 * Created by Pasha on 12.05.2017.
 */
public interface OrderSearcher {
    List<Order> searchForActivate();
    List<Order> searchForPause();
    List<Order> searchForResume();
    List<Order> searchForDisable();

    List<Order> searchCsrOrder(Long csrId);

    List<Order> searchCsrPauseOrder(Long csrId);

    List<Order> searchCsrResumeOrder(Long csrId);

    List<Order> searchCsrDisableOrder(Long csrId);

    List<User> getOnlineCsrs();
}
