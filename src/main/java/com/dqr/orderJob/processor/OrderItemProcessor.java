package com.dqr.orderJob.processor;

import org.springframework.batch.item.ItemProcessor;

import com.dqr.orderJob.beans.Order;
import com.dqr.orderJob.beans.SvcReq;

/**
 * Created by dqromney on 3/15/17.
 */
public class OrderItemProcessor implements ItemProcessor<Order, SvcReq> {

    @Override
    public SvcReq process(final Order order) throws Exception {

        final SvcReq svcReq = new SvcReq();

        svcReq.setId(order.getOrderID());
        svcReq.setName(order.getOrderName().toUpperCase());

        System.out.println("Converting (" + order + ") into (" + svcReq + ")");

        return svcReq;
    }
}