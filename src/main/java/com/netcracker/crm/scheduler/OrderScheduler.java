package com.netcracker.crm.scheduler;

import com.netcracker.crm.scheduler.cacher.impl.OrderCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


/**
 * Created by Pasha on 11.05.2017.
 */
@Service
public class OrderScheduler {
    private final OrderCache orderCache;

    @Autowired
    public OrderScheduler(OrderCache orderCache) {
        this.orderCache = orderCache;
    }
//  Hint for cron scheduler
//   second, minute, hour, day of month, month, day(s) of week

    @Scheduled(cron = "0 */1 * * * *")
    public void orderForActivate(){
        orderCache.fillCache();
    }


    @Scheduled(cron = "0 */2 * * * *")
    public void cleanCache(){
        orderCache.cleanCache();
    }
}
