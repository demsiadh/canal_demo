package love.jiahao.domain.po;

import lombok.Data;

import java.io.Serializable;

/**
 * <big>用户实体类</big>
 *
 * @author 13684
 * @date 2024/8/28
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String name;
}
