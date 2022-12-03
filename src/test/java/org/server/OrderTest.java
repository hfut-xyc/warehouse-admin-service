package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.Order;
import org.server.mapper.OrderMapper;
import org.server.service.OrderService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
class OrderTest {

	@Resource
	private OrderService orderService;

	@Resource
	private OrderMapper orderMapper;

	@Test
	void Test1() {
		Order order = new Order();
		order.setEid(10000);
		order.setWid(1);
		order.setPid(20000);
		order.setStatus(1);
		order.setAmount(-400);
	}

	@Test
	void Test2() {
		Date start = new Date(1590422400000L);
		Date end = new Date(1590460980000L);
	}
}