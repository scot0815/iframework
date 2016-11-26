import com.scot.permissions.redis.factory.JedisClusterFactory;
import com.scot.permissions.redis.utils.ApplicationContextUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * 测试redis集群环境
 * Created by shengke on 2016/11/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-redis.xml"})
@ActiveProfiles("localhost")
public class TestConfig {

    @Autowired
    private JedisClusterFactory jedisClusterFactory;

    @Test
    @Ignore
    public void test() throws Exception {
        JedisCluster jedisCluster = jedisClusterFactory.getObject();
        //System.out.println(jedisCluster.set("shengke", "scot"));
        System.out.println(jedisCluster.get("shengke"));
    }
}
