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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

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

    /**
     * 根据订单日期分页查询订单
     */
    public Map<String, Object> selectListByDate(SelectListDTO dto) {
        Integer total = orderMapper.selectCountByDate(dto.getDate());
        List<Order> orderList = orderMapper.selectListByDate(
                (dto.getPage() - 1) * dto.getPageSize(), dto.getPageSize(), dto.getKeyword());

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("orderList", orderList);
        return map;
    }


    public Integer updateStockOrder(Order order) throws Exception {
        Warehouse warehouse = warehouseMapper.selectByWid(order.getWarehouseId());
        if (warehouse == null) {
            throw new Exception("仓库不存在");
        }
        Product product = productMapper.selectByPid(order.getProductId());
        if (product == null) {
            throw new Exception("商品不存在");
        }

        String str = "" + warehouse.getWarehouseId() + product.getProductId();
        synchronized (str.intern()) {
            // 先提交事务，再释放锁
            //OrderService proxy = (OrderService) AopContext.currentProxy();
            //proxy.updateStock(order);
            this.updateStock(order);
        }
        return 1;
    }

    @Transactional
    public void updateStock(Order order) throws Exception {
        WarehouseProduct record = warehouseProductMapper.selectByWidPid(order.getWarehouseId(), order.getProductId());
        if (record == null) {
            throw new Exception("库存不存在");
        }
        if (record.getStock() + order.getCount() < 0) {
            throw new Exception("库存不足");
        }
        WarehouseProduct warehouseProduct = new WarehouseProduct();
        warehouseProduct.setWarehouseId(order.getWarehouseId());
        warehouseProduct.setProductId(order.getProductId());
        warehouseProduct.setStock(record.getStock() + order.getCount());
        Integer res = warehouseProductMapper.updateStock(warehouseProduct);
        if (res != 1) {
            throw new Exception("更新库存失败");
        }
        Integer res1 = orderMapper.insert(order);
        if (res1 != 1) {
            throw new Exception("添加订单失败");
        }
    }

    public Integer insertStockOrder(Order order) throws Exception {
        if (order.getCount() <= 0) {
            throw new Exception("插入的库存数量必须大于0");
        }
        Warehouse warehouse = warehouseMapper.selectByWid(order.getWarehouseId());
        if (warehouse == null) {
            throw new Exception("仓库不存在");
        }
        Product product = productMapper.selectByPid(order.getProductId());
        if (product == null) {
            throw new Exception("商品不存在");
        }

        try {
            lockInsertStock.lock();
            //OrderService proxy = (OrderService) AopContext.currentProxy();
            //proxy.insertStock(order);
            this.insertStock(order);
        } finally {
            // 先提交事务，再释放锁
            lockInsertStock.unlock();
        }

        return 1;
    }

    @Transactional
    public void insertStock(Order order) throws Exception {
        WarehouseProduct warehouseProduct = new WarehouseProduct();
        warehouseProduct.setWarehouseId(order.getWarehouseId());
        warehouseProduct.setProductId(order.getProductId());
        warehouseProduct.setWarehouseName(order.getWarehouseName());
        warehouseProduct.setProductName(order.getProductName());

        WarehouseProduct record = warehouseProductMapper.selectByWidPid(order.getWarehouseId(), order.getProductId());
        if (record == null) {
            warehouseProduct.setStock(order.getCount());
            Integer res = warehouseProductMapper.insert(warehouseProduct);
            if (res != 1) {
                throw new Exception("插入库存失败");
            }
        } else {
            warehouseProduct.setStock(record.getStock() + order.getCount());
            Integer res = warehouseProductMapper.updateStock(warehouseProduct);
            if (res != 1) {
                throw new Exception("更新库存失败");
            }
        }
        Integer res = orderMapper.insert(order);
        if (res != 1) {
            throw new Exception("添加订单失败");
        }
    }

    @Transactional
    public Integer deleteById(Integer orderId) throws Exception {
        Integer res = orderMapper.deleteById(orderId);
        if (res != 1) {
            throw new Exception("删除订单失败");
        }
        return res;
    }

}
