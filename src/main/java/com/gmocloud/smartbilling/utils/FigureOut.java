package com.gmocloud.smartbilling.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 * １．遅延損害金（支払期日～入金日）の計算
 * ２．日割り額（対象日～月末）の計算
 * ３．半月割り額の計算
 */
public class FigureOut {

	/**
	 * 【遅延損害金の計算】
	 * 引数：請求金額、支払期日、入金日、延滞利息
	 *
	 * (1) 起算日から計算して年に満つる期間は，年利計算する。
	 * (2) 次に、年に満たない期間は、日割計算する。　※小数点以下切り捨て（注：切り上げると法定利率を超える可能性あり）
	 * そして(1)と(2)を合算する。
	 */
	static public int latePaymentCharge(
			int billingAmount, LocalDate dueDate, LocalDate creditedDate, double delayInterest) {

		if(billingAmount == 0) throw new IllegalArgumentException("請求金額がゼロ");
		if(billingAmount < 0) throw new IllegalArgumentException("請求金額がマイナス");
		if(dueDate == null) throw new IllegalArgumentException("期日がnull");
		if(creditedDate == null) throw new IllegalArgumentException("入金日がnull");
		if(delayInterest < 0) throw new IllegalArgumentException("年利がマイナス");

		if(creditedDate.isBefore(dueDate))
			return 0;

		Period period = dueDate.until(creditedDate);

		//年に満つる期間の年数を取得し、その期間を除いた期間を求める
		int years = period.getYears();
		long days = (years > 0)?
				dueDate.plusYears(years).until(creditedDate, ChronoUnit.DAYS)
				: dueDate.until(creditedDate, ChronoUnit.DAYS);

		BigDecimal bdBillingAmount = new BigDecimal(billingAmount);
		BigDecimal bdDays = new BigDecimal(days);
		BigDecimal bdDelayInterest = BigDecimal.valueOf(delayInterest/100);
		BigDecimal bd365 = new BigDecimal(365);

		//年に満たない期間の利息計算
		BigDecimal result = bdBillingAmount.multiply(bdDelayInterest).multiply(bdDays).divide(bd365, 5, BigDecimal.ROUND_DOWN);
		if(years > 0) {
			//年に満つる期間の利息計算
			result = result.add(bdBillingAmount.multiply(bdDelayInterest).multiply(new BigDecimal(years)));
		}

		return result.intValue();
	}

	/**
	 * 【日割り額（対象日～月末）の計算】
	 * 引数：月額、基準日、1か月の日数
	 * ・月額を１か月の日数で割って基準日から月末までの日数を掛けた金額を返す（小数点以下切り捨て）
	 */
	static public int amountProrateByDay(int monthlyAmount, LocalDate  referencDate, int daysMonth) {
		//TODO
		return 0;
	}

	/**
	 * 【半月割り額の計算】
	 * 引数：月額、基準日
	 * ・基準日の日が15日以下のとき、月額を２で割った金額を返す（小数点以下切り捨て）
	 * ・基準日の日が15日を超えているとき、月額を返す
	 */
	static public int amountProratedByHalfMonth(int monthlyAmount, LocalDate referencDate) {
		return referencDate.getDayOfMonth() > 15 ? monthlyAmount : monthlyAmount / 2;
	}
}
