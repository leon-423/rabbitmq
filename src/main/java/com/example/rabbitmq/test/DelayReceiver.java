package com.example.rabbitmq.test;

import com.example.rabbitmq.config.DelayRabbitConfig;
import com.example.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by trumpkin 2019/12/4 10:52
 */

@Component
@Slf4j
public class DelayReceiver {

    @RabbitListener(queues = {DelayRabbitConfig.ORDER_QUEUE_NAME})
    public void orderDelayQueue(Order order, Message message, Channel channel) {
        log.info("###########################################");
        log.info("【orderDelayQueue 监听的消息】 - 【消费时间】 - [{}]- 【订单内容】 - [{}]",  new Date(), order.toString());
        if(order.getOrderStatus() == 0) {
            order.setOrderStatus(2);
            log.info("【该订单未支付，取消订单】" + order.toString());
        } else if(order.getOrderStatus() == 1) {
            log.info("【该订单已完成支付】");
        } else if(order.getOrderStatus() == 2) {
            log.info("【该订单已取消】");
        }
        log.info("###########################################");
    }

}
