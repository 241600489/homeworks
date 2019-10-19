package zym.spring.utils;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeanHelperTest {
    private List<UserDo> userDtos = new ArrayList<>();
    @BeforeEach
    public void init() {
        UserDo userDo1 = new UserDo();
        userDo1.setUsername("lzq");
        userDo1.setAddress("hube");
        userDo1.setPassword("fafafdafasfaasgagggagagagde");
        UserDo userDo2 = new UserDo();
        userDo2.setUsername("zym");
        userDo2.setAddress("fos");
        userDo2.setPassword("fafafdafasfaaggagagagde");
        userDtos.add(userDo1);
        userDtos.add(userDo2);
    }

    @Test
    public void copyForList() {
        List<UserDto> userDtos = BeanHelper.copyForList(UserDto.class, this.userDtos);
        if (Objects.nonNull(userDtos)) {
            userDtos.forEach(System.out::println);
        }
    }

    @Test
    public void testCopyForList() {
        //传入UserDto::new 便可 这个是方法引用，当实现一个函数式接口若返现只有一行即可使用但是分情况，在这里就不赘述了
        List<UserDto> userDtos = BeanHelper.copyForList(UserDto::new, this.userDtos);
        if (Objects.nonNull(userDtos)) {
            userDtos.forEach(System.out::println);
        }
    }
}
