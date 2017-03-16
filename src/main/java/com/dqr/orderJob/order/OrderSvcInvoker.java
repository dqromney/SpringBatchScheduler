package com.dqr.orderJob.order;

import com.dqr.orderJob.beans.SvcReq;
import com.dqr.orderJob.beans.SvcResp;
import org.springframework.batch.item.ItemWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *
 * Created by dqromney on 3/15/17.
 */
public class OrderSvcInvoker implements ItemWriter<SvcReq> {

    @Override
    public void write(List<? extends SvcReq> svcReqs) throws Exception {

        for (SvcReq svcReq : svcReqs) {

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<SvcResp> respEntity = restTemplate
                .postForEntity("http://localhost:8080/phone/order", svcReq, SvcResp.class);

            SvcResp resp = respEntity.getBody();

            System.out.println("calling web service:" + resp);
        }

        System.out.println("Processed items:" + svcReqs.size());
    }

}