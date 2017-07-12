package com.gmocloud.smartbilling.dao.customRepositories;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
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
public class TanddRecordPulseCustomRepository {
    @PersistenceContext
    private EntityManager em;

    private static final String TanddRecordRoomPelseQuery =
            "select a.BULD_ID," +
                    " b.building_name," +
                    " a.ROOM_ID," +
                    " a.REC_DATETIME," +
                    " a.VALUE " +
                    " from tandd_record_pulse a, building b" +
                    " where a.BULD_ID = b.building_code";

    public List<TanddRecordPulseViewModel> getTanddRecordPulseList(
            String buildingCode, String roomCode, LocalDate recDate) {
        StringBuilder sb = new StringBuilder(TanddRecordRoomPelseQuery);
        List<Object> params = new ArrayList<>();
        if(!StringUtils.isEmpty(buildingCode)) {
            params.add(buildingCode);
            sb.append(" and a.BULD_ID = ?").append(params.size());
        }
        if(!StringUtils.isEmpty(roomCode)) {
            params.add(roomCode);
            sb.append(" and a.ROOM_ID = ?").append(params.size());
        }
        if(recDate != null) {
            params.add(recDate.toString());
            sb.append(" and REC_DATETIME > ?").append(params.size());
        }
        if(recDate != null) {
            params.add(recDate.toString());
            sb.append(" and REC_DATETIME < (?").append(params.size());
            sb.append(" + interval 1 day)");
        }

        sb.append(" order by a.BULD_ID asc, a.ROOM_ID asc, a.REC_DATETIME desc ");

        javax.persistence.Query q = em.createNativeQuery(sb.toString());
        for(int i=0; i<params.size(); i++) {
            q.setParameter(i+1,params.get(i));
        }

        List<Object[]> items = q.getResultList();

        return items.stream().map(col ->
                new TanddRecordPulseViewModel(
                        (String) col[0],
                        (String) col[1],
                        (String) col[2],
                        col[3]==null?null:((Timestamp) col[3]).toLocalDateTime(),
                        (BigDecimal)col[4])
        ).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class TanddRecordPulseViewModel {

        private String buildingCode;   // ・・・建物コード
        private String buildingName;   // ・・・建物名
        private String roomCode;       // ・・・部屋番号
        @JsonFormat(pattern = "yyyy年MM月dd日HH時mm分ss秒")
        private LocalDateTime recDateTime; // ・・・検針日時
        private BigDecimal value;      // ・・・検針結果：CH1

    }

}
