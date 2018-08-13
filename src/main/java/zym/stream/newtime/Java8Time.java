package zym.stream.newtime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Author unyielding
 * @date 2018/8/13 0013 11:24
 * @desc
 */
public class Java8Time {
    static long parseDateTimeString(String pattern, String text) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(text, dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
