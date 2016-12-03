import com.scot.iframework.redis.utils.RedisUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

/**
 * 测试redis集群环境
 * Created by shengke on 2016/11/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-redis.xml"})
@ActiveProfiles("localhost")
public class TestRedis {

    /**
     * redis工具类.
     */
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 测试存储.
     */
    @Test
    @Ignore
    public void testSet() {
        try {
            System.out.println(redisUtil.setObject("payment1", "payment111"));
            System.out.println(redisUtil.setObject("payment2", "payment222", 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试获取.
     */
    @Test
    @Ignore
    public void testGet() {
        try {
            System.out.println(redisUtil.getObject("payment1", String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试删除.
     */
    @Test
    @Ignore
    public void testDelete() {
        try {
            System.out.println(redisUtil.deleteKey("payment1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
