import com.sa.jedis.util.JedisConnectionFactory;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class Jedis_Test {

    @Test
    public void testString(){
        // 建立连接
        Jedis  jedis = new Jedis("192.168.246.128",6379);
        // 设置密码
        jedis.auth("5613889");
        // 选择库
        jedis.select(0);
        jedis.set("name","sa66");
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }
    @Test
    public void testList(){
        // 建立连接
//        Jedis  jedis = new Jedis("192.168.246.128",6379);
//        // 设置密码
//        jedis.auth("5613889");
        Jedis jedis = JedisConnectionFactory.getJedis();
        jedis.auth("5613889");
        // 选择库
        jedis.select(0);
        jedis.lpush("list","aa","bb");
        String lpop = jedis.lpop("list");
        System.out.println(lpop);
        jedis.close();
    }
}
