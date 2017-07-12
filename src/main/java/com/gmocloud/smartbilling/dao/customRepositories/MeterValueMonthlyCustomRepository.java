package com.gmocloud.smartbilling.dao.customRepositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/17.
 */
@Repository
public class MeterValueMonthlyCustomRepository {
    @PersistenceContext
    private EntityManager em;

    private static final String meterValueMonthlyQuery =
            "select a.building_code, " +
                    " b.building_name," +
                    " a.room_code," +
                    " a.month," +
                    " sum((case when (a.utilities_code = 'EP') then a.value else 0 end)) AS value_ep," +
                    " sum((case when (a.utilities_code = 'WT') then a.value else 0 end)) AS value_wt," +
                    " sum((case when (a.utilities_code = 'HW') then a.value else 0 end)) AS value_hw," +
                    " sum((case when (a.utilities_code = 'CH') then a.value else 0 end)) AS value_ch," +
                    " sum((case when (a.utilities_code = 'OL') then a.value else 0 end)) AS value_ol," +
                    " sum((case when (a.utilities_code = 'GS') then a.value else 0 end)) AS value_gs," +
                    " sum((case when (a.utilities_code = 'PW') then a.value else 0 end)) AS value_pw," +
                    " sum((case when (a.utilities_code = 'PH') then a.value else 0 end)) AS value_ph " +
                    " from meter_value_monthly a, building b" +
                    " where a.room_code <> '0000' and a.building_code = b.building_code";

    //    private static final String conditionBildingCode = "and t2.building_code = :buildingCode";
    private static final String meterValueMonthList = "select distinct month, 'a' from meter_value_monthly order by month desc";

    public List<MeterValueMonthlyViewModel> getMeterValueMonthlyList(
            String buildingCode, String roomCode, String month) {
        StringBuilder sb = new StringBuilder(meterValueMonthlyQuery);
        List<Object> params = new ArrayList<>();
        if(!StringUtils.isEmpty(buildingCode)) {
            params.add(buildingCode);
            sb.append(" and a.building_code = ?").append(params.size());
        }
        if(!StringUtils.isEmpty(roomCode)) {
            params.add(roomCode);
            sb.append(" and room_code = ?").append(params.size());
        }
        if(!StringUtils.isEmpty(month)) {
            params.add(month);
            sb.append(" and month = ?").append(params.size());
        }

        sb.append(" group by a.building_code, a.room_code, a.month ");
        sb.append(" order by a.building_code asc, a.room_code asc, a.month desc ");

        javax.persistence.Query q = em.createNativeQuery(sb.toString());
        for(int i=0; i<params.size(); i++) {
            q.setParameter(i+1,params.get(i));
        }

		List<Object[]> items = q.getResultList();

        // col[2]==null?null:((Date) col[2]).toLocalDate(),

        return items.stream().map(col ->
                new MeterValueMonthlyViewModel(
                        (String) col[0],
                        (String) col[1],
                        (String) col[2],
                        (String) col[3],
                        (BigDecimal)col[4],
                        (BigDecimal)col[5],
                        (BigDecimal)col[6],
                        (BigDecimal)col[7],
                        (BigDecimal)col[8],
                        (BigDecimal)col[9],
                        (BigDecimal)col[10],
                        (BigDecimal)col[11])
        ).collect(Collectors.toList());
    }

    public List<MeterValueMonthListViewModel> getMeterValueMonthList(){

        StringBuilder sb = new StringBuilder(meterValueMonthList);
        javax.persistence.Query q = em.createNativeQuery(sb.toString());

        List<Object[]> items = q.getResultList();

        return items.stream().map(col ->
                new MeterValueMonthListViewModel(
                        (String) col[0],
                        (String) col[1])
       ).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class MeterValueMonthlyViewModel {

        private String buildingCode; // ・・・建物コード
        private String buildingName; // ・・・建物名
        private String roomCode;     // ・・・部屋番号
        private String month;        // ・・・検針年月
        private BigDecimal valueEp;  // ・・・検針結果：電気
        private BigDecimal valueWt;  // ・・・検針結果：水道
        private BigDecimal valueHw;  // ・・・検針結果：給湯
        private BigDecimal valueCh;  // ・・・検針結果：冷暖房
        private BigDecimal valueOl;  // ・・・検針結果：灯油
        private BigDecimal valueGs;  // ・・・検針結果：ガス
        private BigDecimal valuePw;  // ・・・検針結果：動力
        private BigDecimal valuePh;  // ・・・検針結果：電話

    }

    @Data
    @AllArgsConstructor
    public static class MeterValueMonthListViewModel {
        private String month;        // ・・・検針年月
        private String dummy;        //

    }

}
