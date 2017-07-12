package com.gmocloud.smartbilling.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/17.
 */
public class DateUtil {
    public static LocalDate localDateFrom(java.util.Date date) {
        return date==null?null : LocalDateTime.ofInstant(
                        date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }
}
