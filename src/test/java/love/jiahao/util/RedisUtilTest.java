package love.jiahao.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import love.jiahao.TestCanalApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <big>redis的工具类测试类</big>
 *
 * @author 13684
 * @date 2024/8/27
 */
@SpringBootTest(classes = TestCanalApplication.class)
public class RedisUtilTest {
    @Autowired
    private RedisUtil<Person> redisUtil;

    @Data
    static class Person{
        private int age;
        private String name;

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    @Test
    public void setTest() {
        Person person = new Person();
        person.age = 18;
        person.name = "jiahao";
        System.out.println(person);
        redisUtil.set("setTest", person);
    }

    @Test
    public void getTest() {
        Person person = redisUtil.get("setTest");
        System.out.println(person);
    }

}
