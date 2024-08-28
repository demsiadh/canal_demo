package love.jiahao.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.jiahao.domain.po.User;
import love.jiahao.util.RedisUtil;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * <big>监听canal的变化</big>
 *
 * @author 13684
 * @date 2024/8/28
 */
@Slf4j
@Component
@CanalTable(value = "user")
@RequiredArgsConstructor
public class UserHandler implements EntryHandler<User> {
    private final RedisUtil<User> redisUtil;

    /**
     * 实现用户信息的插入操作
     * 通过将用户对象存储到Redis中，以用户ID作为键，实现快速访问
     * 此方法解释了如何在Redis中保存用户对象，强调了使用Redis进行快速数据访问和操作的重要性
     *
     * @param user 待插入的用户对象，包含用户的唯一ID和其他相关信息
     */
    @Override
    public void insert(User user) {
        redisUtil.set(String.valueOf(user.getId()), user);
    }

    /**
     * 更新用户信息
     * 该方法通过使用新的用户对象替换Redis中现有的用户对象来实现用户信息的更新
     * 说明了更新操作的实现逻辑，即使用户信息发生变更，通过Redis可以快速高效地进行更新
     *
     * @param before 更新前的用户对象，用于定位需要更新的用户
     * @param after 更新后的用户对象，包含新的用户信息，将以此内容更新原有信息
     */
    @Override
    public void update(User before, User after) {
        redisUtil.set(String.valueOf(after.getId()), after);
    }

    /**
     * 删除用户信息
     * 通过用户ID从Redis中移除用户对象，实现用户信息的删除操作
     * 解释了删除操作的实现方式，强调了通过Redis可以快速高效地进行数据的删除
     *
     * @param user 待删除的用户对象，只需提供用户ID即可完成删除操作
     */
    @Override
    public void delete(User user) {
        redisUtil.delete(String.valueOf(user.getId()));
    }
}
