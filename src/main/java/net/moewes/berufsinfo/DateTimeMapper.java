package net.moewes.berufsinfo;

import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "cdi")
public interface DateTimeMapper {


    default String timeToString(LocalTime time) {
        return time != null ? time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null;
    }

    default LocalTime stringToTime(String time) {
        return time != null ? LocalTime.parse(time) : null;
    }

    default String dateToString(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    default LocalDate stringToDate(String date) {
        return date != null ? LocalDate.parse(date) : null;
    }
}
