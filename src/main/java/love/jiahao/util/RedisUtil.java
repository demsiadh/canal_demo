package love.jiahao.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <big>操作Redis的工具</big>
 *
 * @author 13684
 * @date 2024/8/27
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisUtil<T> {
    private static final int BASE_EXPIRATION = 60 * 60; // 基础过期时间为1小时
    private static final int RANDOM_RANGE = 60 * 10; // 随机范围为10分钟
    private static final Random RANDOM = new Random();
    private final RedisTemplate<String, T> redisTemplate;

    /**
     * 设置指定的key-value到Redis中
     * 为了减少缓存雪崩的风险，通过随机数机制设置key的过期时间
     *
     * @param key   Redis中的键
     * @param value 需要存储的值
     */
    public void set(String key, T value) {
        int nextInt = RANDOM.nextInt(RANDOM_RANGE);
        redisTemplate.opsForValue().set(key, value, BASE_EXPIRATION + nextInt, TimeUnit.SECONDS);
    }

    /**
     * 从Redis中根据key获取对应的值
     *
     * @param key Redis中的键
     * @return Redis中的值
     */
    public T get(String key) {
        if (!exist(key)) {
            log.warn("redis key:{}不存在", key);
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 判断指定的键是否存在于Redis中
     *
     * @param key 要判断的键
     * @return 如果键存在，则返回true；否则返回false
     */
    public boolean exist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除Redis中的指定键
     *
     * @param key 要删除的键
     */
    public void delete(String key) {
        // 执行删除操作
        Boolean delete = redisTemplate.delete(key);
        // 检查删除是否成功
        if (Boolean.FALSE.equals(delete)) {
            // 如果删除失败，记录警告日志
            log.warn("redis key:{} 删除失败!", key);
        }
    }
}
