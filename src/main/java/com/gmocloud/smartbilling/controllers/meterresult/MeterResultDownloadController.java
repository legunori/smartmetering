package com.gmocloud.smartbilling.controllers.meterresult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmocloud.smartbilling.dao.customRepositories.MeterValueDailyCustomRepository;



@RestController
public class MeterResultDownloadController {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	MeterValueDailyCustomRepository meterValueDailyCustomRepository;

	@RequestMapping(value = "/meterresult/MeterResultDownload", method = RequestMethod.GET)
	public void meterResultDownload(
            @RequestParam(required = false) String buildingCode,
            @RequestParam(required = false) String roomCode,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) LocalTime toTime,
            HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {

		File file = resourceLoader.getResource("classpath:MeterValueResultListTemplete.xlsx").getFile();
		FileInputStream in = new FileInputStream(file);
		Workbook book = null;
		OutputStream out = null;

		try {

			book = WorkbookFactory.create(in);
			DataFormat format = book.createDataFormat();

			//レスポンス設定
			String filename = "meter_result_" + fromDate.format(DateTimeFormatter.ofPattern("yyyyMM")) + ".xlsx";
			response.addHeader("Content-Type", "application/octet-stream");
			response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''" + filename);

			//集計する必要がある場合は、該当部屋のみ集計
			LocalDate today = LocalDate.now();
			if (toDate.equals(today)){
				meterValueDailyCustomRepository.doMeterSummaryByRoom(buildingCode, roomCode, today, today);
			}

			List<MeterValueDailyCustomRepository.MeterValueDailyViewModel> meterValues =
					meterValueDailyCustomRepository.getMeterValueDailyList(buildingCode, roomCode, fromDate, toDate, toTime);

			Sheet sheet = book.getSheetAt(0);

			sheet.getRow(1).getCell(2).setCellValue(meterValues.get(0).getBuildingName() + "／" + String.valueOf(Integer.parseInt(meterValues.get(0).getRoomCode())) + "号室");

			int rowNo = 3;

			for (MeterValueDailyCustomRepository.MeterValueDailyViewModel meterValueModel: meterValues) {

				rowNo++;
				// 検針日
				sheet.getRow(rowNo).getCell(0).setCellValue(meterValueModel.getRecDate().format(DateTimeFormatter.ofPattern("yyyy年M月d日")));
				// 電気
				sheet.getRow(rowNo).getCell(1).setCellValue(toDbl(meterValueModel.getValueEp()));
				// 水道
				sheet.getRow(rowNo).getCell(2).setCellValue(toDbl(meterValueModel.getValueWt()));
				// 給湯
				sheet.getRow(rowNo).getCell(3).setCellValue(toDbl(meterValueModel.getValueHw()));
				// 下水道
				sheet.getRow(rowNo).getCell(4).setCellValue(toDbl(meterValueModel.getValueWt()) + toDbl(meterValueModel.getValueHw()));
				// 冷暖房
				sheet.getRow(rowNo).getCell(5).setCellValue(toDbl(meterValueModel.getValueCh()));
				// 灯油
				sheet.getRow(rowNo).getCell(6).setCellValue(toDbl(meterValueModel.getValueOl()));
				// ガス
				sheet.getRow(rowNo).getCell(7).setCellValue(toDbl(meterValueModel.getValueGs()));
				// 動力
				sheet.getRow(rowNo).getCell(8).setCellValue(toDbl(meterValueModel.getValuePw()));
				// 電話
				sheet.getRow(rowNo).getCell(9).setCellValue(toDbl(meterValueModel.getValuePh()));
			}

			rowNo++;
			// 合計ラベル
			Cell cell = sheet.getRow(rowNo).getCell(0);
			cell.setCellValue("合計");

			// 電気
			cell = sheet.getRow(rowNo).getCell(1);
			cell.setCellFormula("SUM(B4" + ":B" + rowNo + ")");
			// 水道
			cell = sheet.getRow(rowNo).getCell(2);
			cell.setCellFormula("SUM(C4" + ":C" + rowNo + ")");
			// 給湯
			cell = sheet.getRow(rowNo).getCell(3);
			cell.setCellFormula("SUM(D4" + ":D" + rowNo + ")");
			// 下水道
			cell = sheet.getRow(rowNo).getCell(4);
			cell.setCellFormula("SUM(E4" + ":E" + rowNo + ")");
			// 冷暖房
			cell = sheet.getRow(rowNo).getCell(5);
			cell.setCellFormula("SUM(F4" + ":F" + rowNo + ")");
			// 灯油
			cell = sheet.getRow(rowNo).getCell(6);
			cell.setCellFormula("SUM(G4" + ":G" + rowNo + ")");
			// ガス
			cell = sheet.getRow(rowNo).getCell(7);
			cell.setCellFormula("SUM(H4" + ":H" + rowNo + ")");
			// 動力
			cell = sheet.getRow(rowNo).getCell(8);
			cell.setCellFormula("SUM(I4" + ":I" + rowNo + ")");
			// 電話
			cell = sheet.getRow(rowNo).getCell(9);
			cell.setCellFormula("SUM(J4" + ":J" + rowNo + ")");

			book.write(response.getOutputStream());

 		} catch (EncryptedDocumentException | InvalidFormatException e) {
			e.printStackTrace();
		} finally {
			if(book!=null)
				book.close();
		}
	}
	private double toDbl(BigDecimal val) {
		return val==null ? 0 : val.doubleValue();
	}

}
