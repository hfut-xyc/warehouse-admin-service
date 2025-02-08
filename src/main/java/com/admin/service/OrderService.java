package com.admin.service;

import com.admin.mapper.OrderMapper;
import com.admin.mapper.ProductMapper;
import com.admin.mapper.WarehouseMapper;
import com.admin.mapper.WarehouseProductMapper;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Order;
import com.admin.pojo.entity.Product;
import com.admin.pojo.entity.Warehouse;
import com.admin.pojo.entity.WarehouseProduct;
import com.admin.utils.OrderIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private WarehouseMapper warehouseMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private WarehouseProductMapper warehouseProductMapper;

    private final ReentrantLock lockInsertStock = new ReentrantLock();

    public Order updateStock(Order order) throws Exception {
        log.info(order.toString());
        Warehouse warehouse = warehouseMapper.selectByWid(order.getWarehouseId());
        if (warehouse == null) {
            throw new Exception("仓库不存在");
        }
        log.info(warehouse.toString());

        Product product = productMapper.selectByPid(order.getProductId());
        if (product == null) {
            throw new Exception("商品不存在");
        }
        log.info(product.toString());

        String str = "" + warehouse.getWarehouseId() + product.getProductId();
        synchronized (str.intern()) {
            // 先提交事务，再释放锁
            //OrderService proxy = (OrderService) AopContext.currentProxy();
            //proxy.updateStock(order);
            this.updateStockOrder(order);
        }
        return order;
    }

    @Transactional
    public void updateStockOrder(Order order) throws Exception {
        List<WarehouseProduct> records = warehouseProductMapper.selectByWidPid(order.getWarehouseId(), order.getProductId());
        if (records.isEmpty()) {
            throw new Exception("库存不存在");
        }
        WarehouseProduct record = records.get(0);
        log.info(record.toString());

        int newStock = record.getStock() + order.getCount();
        if (newStock < 0) {
            throw new Exception("库存不足");
        }

        record.setStock(newStock);
        log.info(record.toString());
        Integer res = warehouseProductMapper.updateStock(record);
        if (res != 1) {
            throw new Exception("更新库存失败");
        }

        order.setStock(newStock);
        order.setOrderId(OrderIdUtils.createOrderId());
        Integer res1 = orderMapper.insert(order);
        if (res1 != 1) {
            throw new Exception("添加订单失败");
        }
    }

    public Order insertStock(Order order) throws Exception {
        if (order.getCount() <= 0) {
            throw new Exception("插入的库存数量必须大于0");
        }
        Warehouse warehouse = warehouseMapper.selectByWid(order.getWarehouseId());
        if (warehouse == null) {
            throw new Exception("仓库不存在");
        }
        log.info(warehouse.toString());

        Product product = productMapper.selectByPid(order.getProductId());
        if (product == null) {
            throw new Exception("商品不存在");
        }
        log.info(product.toString());

        try {
            lockInsertStock.lock();
            // 先提交事务，再释放锁
            this.insertStockOrder(order);
        } finally {
            lockInsertStock.unlock();
        }
        return order;
    }

    @Transactional
    public void insertStockOrder(Order order) throws Exception {
        List<WarehouseProduct> records = warehouseProductMapper.
            selectByWidPid(order.getWarehouseId(), order.getProductId());

        if (records.isEmpty()) {
            WarehouseProduct record = new WarehouseProduct();
            record.setWarehouseId(order.getWarehouseId());
            record.setProductId(order.getProductId());
            record.setWarehouseName(order.getWarehouseName());
            record.setProductName(order.getProductName());
            record.setStock(order.getCount());
            order.setStock(order.getCount());
            Integer res = warehouseProductMapper.insert(record);
            if (res != 1) {
                throw new Exception("插入库存失败");
            }
        } else {
            WarehouseProduct record = records.get(0);
            record.setStock(record.getStock() + order.getCount());
            order.setStock(record.getStock() + order.getCount());
            Integer res = warehouseProductMapper.updateStock(record);
            if (res != 1) {
                throw new Exception("更新库存失败");
            }
        }
        order.setOrderId(OrderIdUtils.createOrderId());
        Integer res = orderMapper.insert(order);
        if (res != 1) {
            throw new Exception("添加订单失败");
        }
    }

    @Transactional
    public Integer deleteById(String orderId) throws Exception {
        Integer res = orderMapper.deleteById(orderId);
        if (res != 1) {
            throw new Exception("删除订单失败");
        }
        return res;
    }

    /**
     * 根据订单日期分页查询订单
     */
    public Map<String, Object> selectListByDate(SelectListDTO dto) {
        Integer total = orderMapper.selectCountByDate(dto.getKeyword());
        List<Order> orderList = orderMapper.selectListByDate(
            (dto.getPage() - 1) * dto.getPageSize(), dto.getPageSize(), dto.getKeyword());

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("orderList", orderList);
        return map;
    }
}
