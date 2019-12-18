package zym.reflect;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import zym.spring.utils.UserDo;
import zym.spring.utils.UserDto;

import java.util.Objects;

public class BeanHelperTest {

    @Test
    public void copyForBean() {
        UserDo userDo = new UserDo();
        long now = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            userDo.setPassword("123456dd" + i);
            userDo.setAddress("Hongkong");
            userDo.setUsername("Join" + i);
            UserDto userDto =BeanHelper.copyForBean(UserDto::new, userDo);
            Assertions.assertTrue(Objects.nonNull(userDto.getAddress())&&Objects.nonNull(userDto.getUsername()),"UserDto address和userName 不能为空");

        }

        System.out.println("cost:" + (System.currentTimeMillis()-now )  + "ms");
    }
}
