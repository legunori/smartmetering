package com.gmocloud.smartbilling.dao.customRepositories;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/17.
 */
@Repository
public class MeteringStatusCustomRepository {
    @PersistenceContext
    private EntityManager em;

    private static final String meteringStatusListQuery =
            "select BULD_ID," +
                   " building_name," +
                   " ROOM_ID," +
                   " location," +
                   " meter_name," +
                   " latest_timestamp," +
                   " status" +
                   " from v_metering_status " +
                   " where 1 = 1 ";

    private static final String meteringStatusCountQuery =
            "select  BULD_ID," +
                    " building_name," +
                    " sum(case when status = 'running' then 1 else 0 end) as running_count," +
                    " sum(case when status = 'warning' then 1 else 0 end) as warning_count," +
                    " sum(case when status = 'stopping' then 1 else 0 end) as stopping_count" +
                    " from v_metering_status " +
                    " where 1 = 1" +
                    " group by BULD_ID, building_name";

    public List<MeteringStatusListViewModel> getMeteringStatusList(
            String buildingCode, String status) {
        StringBuilder sb = new StringBuilder(meteringStatusListQuery);
        List<Object> params = new ArrayList<>();
        if(!StringUtils.isEmpty(buildingCode)) {
            params.add(buildingCode);
            sb.append(" and BULD_ID = ?").append(params.size());
        }
        if(!StringUtils.isEmpty(status)) {
            params.add(status);
            sb.append(" and status = ?").append(params.size());
        }
        sb.append(" order by BULD_ID asc, ROOM_ID asc");

        javax.persistence.Query q = em.createNativeQuery(sb.toString());
        for(int i=0; i<params.size(); i++) {
            q.setParameter(i+1,params.get(i));
        }

        List<Object[]> items = q.getResultList();

        return items.stream().map(col ->
                new MeteringStatusListViewModel(
                        (String) col[0],
                        (String) col[1],
                        (String) col[2],
                        (String) col[3],
                        (String) col[4],
                        col[5]==null?null:((Timestamp) col[5]).toLocalDateTime(),
                        (String)col[6])
        ).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class MeteringStatusListViewModel {

        private String buildingCode; // ・・・建物コード
        private String buildingName; // ・・・建物名
        private String roomCode;     // ・・・部屋番号
        private String location;     // ・・・接続場所
        private String meterName;    // ・・・検針機器名
        @JsonFormat(pattern = "yyyy年MM月dd日HH時mm分ss秒")
        private LocalDateTime latestTimestamp;   // ・・・最新検針日時
        private String status;       // ・・・ステータス

    }

    public List<MeteringStatusCountViewModel> getMeteringStatusCount() {
        StringBuilder sb = new StringBuilder(meteringStatusCountQuery);

        javax.persistence.Query q = em.createNativeQuery(sb.toString());

        List<Object[]> items = q.getResultList();

        return items.stream().map(col ->
                new MeteringStatusCountViewModel(
                        (String) col[0],
                        (String) col[1],
                        ((BigDecimal) col[2]).intValue(),
                        ((BigDecimal) col[3]).intValue(),
                        ((BigDecimal) col[4]).intValue())
        ).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class MeteringStatusCountViewModel {
        private String buildingCode; // ・・・建物コード
        private String buildingName; // ・・・建物名
        private int runningCount; // ・・・Running件数
        private int warningCount; // ・・・Warning件数
        private int stoppingCount;// ・・・Stopping件数
    }
}
