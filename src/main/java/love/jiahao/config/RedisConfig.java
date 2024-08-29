package love.jiahao.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <big>Redis的配置类</big>
 *
 * @author 13684
 * @date 2024/8/27
 */
@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate bean，用于自定义Redis的数据序列化和反序列化方式
     * 特别说明：此处的注释遵循了函数级别的块注释要求，详细解释了函数的目的、参数和返回值
     *
     * @param redisConnectionFactory Redis连接工厂，用于建立与Redis服务器的连接
     * @return 返回配置好的RedisTemplate实例，该实例用于在应用程序中进行Redis操作
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建RedisTemplate实例
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置Redis连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // key值使用spring默认的StringRedisSerializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // value值使用fastjson的GenericFastJsonRedisSerializer，提高序列化和反序列化的效率
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        // hash序列化的配置，同样使用StringRedisSerializer和GenericFastJsonRedisSerializer
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        // 返回配置好的RedisTemplate实例
        return redisTemplate;
    }
}
