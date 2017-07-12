package com.gmocloud.smartbilling.controllers.dashboard.models;

import lombok.Data;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/07.
 */
@Data
public class MeteringStatusJsonModel {
    private String buildingCode;
    private String buildingName;
    private int runningCount;
    private int warningCount;
    private int stoppingCount;
}
