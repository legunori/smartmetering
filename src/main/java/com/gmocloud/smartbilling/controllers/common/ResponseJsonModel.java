package com.gmocloud.smartbilling.controllers.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/09.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseJsonModel {
    Integer status=0;
    String message="OK";
}
