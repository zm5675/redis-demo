package com.hmdp.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisIDWork;
import com.hmdp.utils.SimpleRedisLock;
import com.hmdp.utils.UserHolder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {
    @Autowired
    private ISeckillVoucherService seckillVoucherService;
    @Autowired
    private RedisIDWork redisIDWork;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;
    // 创建线程池
    private static final ExecutorService SECKILL_ORDER_EXECUTOR = Executors.newSingleThreadExecutor();

//    @PostConstruct // 该注解在bean的声明周期中，当类被初始化后开始执行
//    private void init() {
//        SECKILL_ORDER_EXECUTOR.submit(new VoucherOrderHandle());
//    }

    // 线程任务
//    private class VoucherOrderHandle implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    // 获取队列中的订单信息 XREADGROUP GROUP g1 c1 count 1 STREAM stream.orders 0
//                    // redis中先创建消息对列：XGROUP CREATE stream.orders g1 0 MKSTREAM
//                    List<MapRecord<String, Object, Object>> res = stringRedisTemplate.opsForStream().read(
//                            Consumer.from("g1", "c1"),
//                            StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2)),
//                            StreamOffset.create("stream.orders", ReadOffset.lastConsumed())
//                    );
//                    // 判断消息是否获取成功
//                    if (res == null || res.isEmpty()){
//                        // 如果获取失败，则没有消息，继续下一次循环
//                        continue;
//                    }
//                    // 如果有消息，解析消息，下单
//                    MapRecord<String, Object, Object> record = res.get(0);
//                    Map<Object, Object> value = record.getValue();
//                    VoucherOrder voucherOrder = BeanUtil.fillBeanWithMap(value, new VoucherOrder(), true);
//                    // ack确认 SACK streams.orders g1 id
//                    stringRedisTemplate.opsForStream().acknowledge("stream.orders","g1",record.getId());
//                    // 创建订单
//                    handleVoucherOrder(voucherOrder);
//                } catch (Exception e) {
//                    log.error("处理订单异常", e);
//                    handlePendingList();
//                }
//
//            }
//        }
//       private void handlePendingList(){
//            // 处理异常消息
//           while (true) {
//               try {
//                   // 获取pending-list中的订单信息 XREADGROUP GROUP g1 c1 count 1 STREAM stream.orders 0
//                   List<MapRecord<String, Object, Object>> res = stringRedisTemplate.opsForStream().read(
//                           Consumer.from("g1", "c1"),
//                           StreamReadOptions.empty().count(1),
//                           StreamOffset.create("stream.orders", ReadOffset.from("0"))
//                   );
//                   // 判断消息是否获取成功
//                   if (res == null || res.isEmpty()){
//                       // 如果获取失败，则没有消息，结束循环
//                       break;
//                   }
//                   // 如果有消息，解析消息，下单
//                   MapRecord<String, Object, Object> record = res.get(0);
//                   Map<Object, Object> value = record.getValue();
//                   VoucherOrder voucherOrder = BeanUtil.fillBeanWithMap(value, new VoucherOrder(), true);
//                   // ack确认 SACK streams.orders g1 id
//                   stringRedisTemplate.opsForStream().acknowledge("stream.orders","g1",record.getId());
//                   // 创建订单
//                   handleVoucherOrder(voucherOrder);
//               } catch (Exception e) {
//                   log.error(e.getMessage());
//                   log.error("处理pending-list订单异常", e);
//                   try {
//                       Thread.sleep(20);
//                   } catch (InterruptedException interruptedException) {
//                       interruptedException.printStackTrace();
//                   }
//               }
//
//           }
//
//       }
//    }

    // 阻塞队列，当一个线程尝试从队列里面获取元素时，如果队列没有元素，该线程会被阻塞。直到队列里面有元素，该线程才会被唤醒，获取该元素。
    private BlockingQueue<VoucherOrder> orderTasks = new ArrayBlockingQueue<>(1024 * 1024);
    // 读取脚本信息
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    public void handleVoucherOrder(VoucherOrder voucherOrder) {
        // 获取用户id
        Long userId = voucherOrder.getUserId();
        // 创建锁对象
        RLock lock = redissonClient.getLock("lock:order:" + userId);
        // 获取锁
        boolean isLock = lock.tryLock();
        if (!isLock) {
            // 获取锁失败，返回错误
            log.error("不允许重复下单");
        }
        try {
            proxy.createVoucherOrder(voucherOrder);
        } finally {
            lock.unlock();
        }
    }

    //
//    @Autowired
//    private RedissonClient redissonClient;
    private IVoucherOrderService proxy;
//    public Result seckilVoucher(Long voucherId) {
//        // 创建订单 ,订单id，用户id，代金券id
//        Long orderId = redisIDWork.nextId("order");
//        // 获取用户
//        Long id = UserHolder.getUser().getId();
//        //执行lua脚本
//        Long result = stringRedisTemplate.execute(SECKILL_SCRIPT, Collections.emptyList(), voucherId.toString(), id.toString(),String.valueOf(orderId));
//        // 判断结果是否为0
//        int r = result.intValue();
//        if (r != 0) {
//            // 不为0，没有购买资格
//            return Result.fail(r == 1 ? "库存不足" : "不能重复下单");
//        }
//
//        // 获取代理对象
//        this.proxy = (IVoucherOrderService) AopContext.currentProxy();
//        // 返回订单id
//        return Result.ok(orderId);
//    }

//    public Result seckilVoucher(Long voucherId) {
//        // 获取用户
//        Long id = UserHolder.getUser().getId();
//        //执行lua脚本
//        Long result = stringRedisTemplate.execute(SECKILL_SCRIPT, Collections.emptyList(), voucherId.toString(), id.toString());
//        // 判断结果是否为0
//        int r = result.intValue();
//        if (r != 0) {
//            // 不为0，没有购买资格
//            return Result.fail(r == 1 ? "库存不足" : "不能重复下单");
//        }
//        // 为0，有购买资格，把下单信息保存到阻塞队列中
//        long order = redisIDWork.nextId("order");
//        // 保存阻塞队列
//        VoucherOrder voucherOrder = new VoucherOrder();
//        // 创建订单 ,订单id，用户id，代金券id
//        long orderId = redisIDWork.nextId("order");
//        voucherOrder.setId(orderId);
//        voucherOrder.setUserId(id);
//        voucherOrder.setVoucherId(voucherId);
//        // 将订单信息加入到阻塞队列中
//        orderTasks.add(voucherOrder);
//        // 获取代理对象
//        this.proxy = (IVoucherOrderService) AopContext.currentProxy();
//        this.save(voucherOrder);
//        // 返回订单id
//        return Result.ok(orderId);
//    }

    @Override
    public Result seckilVoucher(Long voucherId) {
        // 查询优惠券
        SeckillVoucher voucher = seckillVoucherService.getById(voucherId);
        // 判断是否在规定时间内
        if (voucher.getBeginTime().isAfter(LocalDateTime.now())) {
            // 尚未开始
            return Result.fail("秒杀尚未开始！");
        }
        if (voucher.getEndTime().isBefore(LocalDateTime.now())) {
            // 已经结束
            return Result.fail("秒杀已经结束！");
        }
        // 判断库存
        if (voucher.getStock() < 1) {
            // 库存不足
            return Result.fail("库存不足！");
        }
        Long id = UserHolder.getUser().getId();
        // 同一个用户使用悲观锁，防止用户重复获取秒杀优惠券，适用于单体项目，当在分布式项目中可能会失效
//        synchronized (id.toString().intern()) {
//            // 防止事务失效,获取代理对象
//            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
//            return proxy.createVoucherOrder(voucherId);
//        }

        // 创建锁对象,分布式事务实现（使用redis实现分布式锁）
        SimpleRedisLock simpleRedisLock = new SimpleRedisLock("order:" + id, stringRedisTemplate);
        // 获取锁
        boolean isLock = simpleRedisLock.tryLock(4);
        // 使用redissonClient获取锁
        //RLock lock = redissonClient.getLock("lock:order" + id);
//        // 释放锁
//        lock.unlock();
        if (!isLock) {
            // 获取锁失败，返回错误
            return Result.fail("不允许重复订单");
        }
        try {
            // 防止事务失效,获取代理对象
            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
            return proxy.createVoucherOrder(voucherId);
        } finally {
            simpleRedisLock.unLock();
        }
    }

    @Transactional
    public Result createVoucherOrder(Long voucherId) {
        Long id = UserHolder.getUser().getId();
        // 一人一单判断
        int count = this.query().eq("user_id", id).eq("voucher_id", voucherId).count();
        if (count > 0) {
            return Result.fail("当前用户已经有该优惠券！");
        }
        // 扣减库存 eq("stock",voucher.getStock()) 乐观锁方法解决并发问题
        boolean success = seckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherId)
                .gt("stock", 0)
                .update();
        if (!success) {
            return Result.fail("库存不足！");
        }
        VoucherOrder voucherOrder = new VoucherOrder();
        // 创建订单 ,订单id，用户id，代金券id
        long orderId = redisIDWork.nextId("order");
        voucherOrder.setId(orderId);
        voucherOrder.setUserId(id);
        voucherOrder.setVoucherId(voucherId);
        this.save(voucherOrder);
        // 返回订单编号
        return Result.ok(orderId);
    }

    @Transactional
    public void createVoucherOrder(VoucherOrder voucherOrder) {
        Long userId = voucherOrder.getUserId();
       // Long id = UserHolder.getUser().getId();
        // 一人一单判断
        int count = this.query().eq("user_id", userId).eq("voucher_id", voucherOrder.getVoucherId()).count();
        if (count > 0) {
            log.error("当前用户已经购买过了！");
        }
        // 扣减库存 eq("stock",voucher.getStock()) 乐观锁方法解决并发问题
        boolean success = seckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherOrder.getVoucherId())
                .gt("stock", 0)
                .update();
        if (!success) {
            log.error("库存不足");
        }
        this.save(voucherOrder);
        log.debug("下单成功");
    }


}
