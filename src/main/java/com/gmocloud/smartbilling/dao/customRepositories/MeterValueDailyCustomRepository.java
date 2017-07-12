package com.gmocloud.smartbilling.dao.customRepositories;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/17.
 */
@Repository
public class MeterValueDailyCustomRepository {
    @PersistenceContext
    private EntityManager em;

    private static final String meterValueDailyQuery =
            "select a.building_code," +
                    " b.building_name," +
                    " a.room_code," +
                    " a.rec_date," +
                    " sum((case when (a.utilities_code = 'EP') then a.value else 0 end)) AS value_ep," +
                    " sum((case when (a.utilities_code = 'WT') then a.value else 0 end)) AS value_wt," +
                    " sum((case when (a.utilities_code = 'HW') then a.value else 0 end)) AS value_hw," +
                    " sum((case when (a.utilities_code = 'CH') then a.value else 0 end)) AS value_ch," +
                    " sum((case when (a.utilities_code = 'OL') then a.value else 0 end)) AS value_ol," +
                    " sum((case when (a.utilities_code = 'GS') then a.value else 0 end)) AS value_gs," +
                    " sum((case when (a.utilities_code = 'PW') then a.value else 0 end)) AS value_pw," +
                    " sum((case when (a.utilities_code = 'PH') then a.value else 0 end)) AS value_ph " +
                    " from meter_value_daily a, building b" +
                    " where room_code <> '0000' and a.building_code = b.building_code";

    private static final String meterValueDailyDefualtDateQuery =
            "select rec_date_from," +
                  " rec_date_to " +
                  " from job_schedule " +
                  "  where rec_date_from < curdate() and rec_date_to > curdate() and job_code = 'METER_SUMMARY'";

    private static final String meteringCountingQueryClosing =
            "call flunitapi_db.sp_meter_summary_by_time_for_Sekisan(?1, ?2, ?3)";

    private static final String meteringSummaryByRoom = "call flunitapi_db.sp_meter_summary_daily_by_room(?1, ?2, ?3, ?4)";

    public void doMeterSummaryByRoom(String buildingCode, String roomCode, LocalDate recFromDate, LocalDate recToDate){

    	javax.persistence.Query q = em.createNativeQuery(meteringSummaryByRoom);
        q.setParameter(1, buildingCode);
        q.setParameter(2, roomCode);
        q.setParameter(3, recFromDate.toString());
        q.setParameter(4, recToDate.toString());

        List<Object[]> items= q.getResultList();

    }

    public List<MeterValueDailyViewModel> getMeterValueDailyList(
            String buildingCode, String roomCode, LocalDate recFromDate, LocalDate recToDate, LocalTime toTime) {

    	LocalDate today = LocalDate.now();

    	StringBuilder sb = new StringBuilder(meterValueDailyQuery);
        List<Object> params = new ArrayList<>();
        if(!StringUtils.isEmpty(buildingCode)) {
            params.add(buildingCode);
            sb.append(" and a.building_code = ?").append(params.size());
        }
        if(!StringUtils.isEmpty(roomCode)) {
            params.add(roomCode);
            sb.append(" and room_code = ?").append(params.size());
        }
        if(recFromDate != null) {
            params.add(recFromDate.toString());
            sb.append(" and rec_date >= ?").append(params.size());
        }
        if(recToDate != null) {
            if((StringUtils.isEmpty(buildingCode) || StringUtils.isEmpty(roomCode))&&(recToDate.isAfter(today))) {
            	params.add(recToDate.toString());
                sb.append(" and rec_date <= ?").append(params.size());
            }else{
            	params.add(recToDate.toString());
                sb.append(" and rec_date < ?").append(params.size());
            }
        }

        sb.append(" group by a.building_code, a.room_code, a.rec_date ");
        sb.append(" order by a.building_code, a.room_code, a.rec_date ");

        javax.persistence.Query q = em.createNativeQuery(sb.toString());
        for(int i=0; i<params.size(); i++) {
            q.setParameter(i+1,params.get(i));
        }

        List<Object[]> items = q.getResultList();

        // 時刻を考慮した集計
        if((!StringUtils.isEmpty(buildingCode) && !StringUtils.isEmpty(roomCode)) &&
        	(recToDate.isBefore(today) || recToDate.equals(today))) {
	        LocalDateTime recToDateTime = LocalDateTime.of(recToDate, toTime);

	        q = em.createNativeQuery(meteringCountingQueryClosing);
	        q.setParameter(1, buildingCode);
	        q.setParameter(2, roomCode);
	        q.setParameter(3, Timestamp.valueOf(recToDateTime));
	        List<Object[]> items2= q.getResultList();

	        items.addAll(items2);

        }
        List<MeterValueDailyViewModel> result = items.stream().map(col ->
        new MeterValueDailyViewModel(
                (String) col[0],
                (String) col[1],
                (String) col[2],
                col[3]==null?null:((Date) col[3]).toLocalDate(),
                (BigDecimal)col[4],
                (BigDecimal)col[5],
                (BigDecimal)col[6],
                (BigDecimal)col[7],
                (BigDecimal)col[8],
                (BigDecimal)col[9],
                (BigDecimal)col[10],
                (BigDecimal)col[11])
                 ).collect(Collectors.toList());

        return result;
    }

    @Data
    @AllArgsConstructor
    public static class MeterValueDailyViewModel {

        private String buildingCode; // ・・・建物コード
        private String buildingName; // ・・・建物名
        private String roomCode;     // ・・・部屋番号
        @JsonFormat(pattern = "yyyy年MM月dd日")
        private LocalDate recDate;   // ・・・検針日
        private BigDecimal valueEp;  // ・・・検針結果：電気
        private BigDecimal valueWt;  // ・・・検針結果：水道
        private BigDecimal valueHw;  // ・・・検針結果：給湯
        private BigDecimal valueCh;  // ・・・検針結果：冷暖房
        private BigDecimal valueOl;  // ・・・検針結果：灯油
        private BigDecimal valueGs;  // ・・・検針結果：ガス
        private BigDecimal valuePw;  // ・・・検針結果：動力
        private BigDecimal valuePh;  // ・・・検針結果：電話

    }

    public List<MeterValueDailyDefaultDateViewModel> getMeterValueDailyDefaultDate() {
        StringBuilder sb = new StringBuilder(meterValueDailyDefualtDateQuery);

        javax.persistence.Query q = em.createNativeQuery(sb.toString());

        List<Object[]> items = q.getResultList();

        return items.stream().map(col ->
                new MeterValueDailyDefaultDateViewModel(
                        col[0]==null?null:((Date) col[0]).toLocalDate(),
                        col[1]==null?null:((Date) col[1]).toLocalDate())
                ).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class MeterValueDailyDefaultDateViewModel {
        private LocalDate recFromDate;   // ・・・検針日From
        private LocalDate recToDate;     // ・・・検針日To
    }

}
