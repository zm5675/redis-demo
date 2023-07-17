package com.hmdp;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hmdp.entity.Shop;
import com.hmdp.service.impl.ShopServiceImpl;
import com.hmdp.utils.RedisIDWork;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringBootTest
class HmDianPingApplicationTests {
    @Autowired
    private ShopServiceImpl iShopService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisIDWork redisIDWork;

    private ExecutorService es = Executors.newFixedThreadPool(500);

    @Test
    void testIsBlack() {
        redisTemplate.opsForValue().set(10,"sA");
        Object name = redisTemplate.opsForValue().get(10);
        System.out.println(name);

    }
    @Test
    void testIsBlackss() {
        stringRedisTemplate.opsForValue().set("name","sA");

        Object name = stringRedisTemplate.opsForValue().get("��");
        System.out.println(name);

    }

    @Test
    void testBlack() {
        stringRedisTemplate.opsForValue().set("is", "");
        String is = stringRedisTemplate.opsForValue().get("bb");
        System.out.println(is);
        System.out.println(StringUtils.isBlank(is));
    }

    @Test
    void testSaveShop2Redis() throws InterruptedException {
        iShopService.saveShop2Redis(1L, 10L);
    }

    @Test
    void testIDInc() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(400);
        Runnable task = () -> {
            for (int i = 0; i < 500; i++) {
                long id = redisIDWork.nextId("order");
                System.out.println(id);
                latch.countDown();
                
            }
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 400; i++) {
            es.submit(task);
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }
    @Test
    void loadShopData(){
        // 查询店铺信息
        List<Shop> list = iShopService.list();
        // 把店铺分组，按照typeId分组，id一致的放到一个集合中
        Map<Long,List<Shop>> map = list.stream().collect(Collectors.groupingBy(shop -> shop.getTypeId()));
        // 分批完成写入redis中
        for (Map.Entry<Long, List<Shop>> entry : map.entrySet()) {
            // 获取id类型
            Long type = entry.getKey();
            String key = "shop:geo:" + type;
            // 获取同类型的店铺集合
            List<Shop> shops = entry.getValue();
            // 写入redis GEOADD key 经度 维度 member
            List<RedisGeoCommands.GeoLocation<String>> locations = new ArrayList<>(shops.size());

            for (Shop shop : shops) {
               // stringRedisTemplate.opsForGeo().add(key,new Point(shop.getX(),shop.getY()),shop.getId().toString());
                locations.add(new RedisGeoCommands.GeoLocation<>(shop.getId().toString(),new Point(shop.getX(),shop.getY())));
            }
            stringRedisTemplate.opsForGeo().add(key,locations);
        }

    }

    @Test
    void testcon(){
        stringRedisTemplate.opsForValue().set("con", String.valueOf(222),30, TimeUnit.SECONDS);
    }
    @Test
    void testHyperLogLog(){
        String[] values = new String[1000];
        int j = 0;
        for (int i = 0; i < 1000000; i++) {
            j = i % 1000;
            values[j] = "user_" + i;
            if (j == 999){
                // 发送到redis
                stringRedisTemplate.opsForHyperLogLog().add("hl2",values);
            }
        }
        // 统计数量
        Long si = stringRedisTemplate.opsForHyperLogLog().size("hl2");
        System.out.println(si);

    }
}
