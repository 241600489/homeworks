package zym.stream.newtime;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

/**
 * @Author unyielding
 * @date 2018/7/21 0021 17:48
 * @desc java 8 的时间日期
 */
public class LocalDateAndTime {

    @Test
    public void localDateOf() {
        LocalDate date = LocalDate.of(2018, 3, 15);
        System.out.println("年：" + date.getYear());
        System.out.println("月：" + date.getMonthValue());
        System.out.println("星期的第几天：" + date.getDayOfWeek().getValue());
        System.out.println("年：" + date.get(ChronoField.YEAR));
        System.out.println("月：" + date.get(ChronoField.MONTH_OF_YEAR));
        System.out.println("星期的第几天：" + date.get(ChronoField.DAY_OF_WEEK
        ));
    }

    @Test
    public void localDateTime() {
        System.out.println(LocalDateTime.parse("2018-03-15 08:00:00")
                .getLong(ChronoField.MILLI_OF_SECOND));
    }

}
