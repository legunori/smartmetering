package com.gmocloud.smartbilling.controllers.dashboard.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/18.
 */
@Data
public class EventsJsonModel {
    @JsonFormat(pattern = "yyyy年M月d日")
    private LocalDate actionDate;
    private String title;
    private String description;
}
