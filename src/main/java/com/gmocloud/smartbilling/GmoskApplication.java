package com.gmocloud.smartbilling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;

@SpringBootApplication
@EntityScan(basePackageClasses = {GmoskApplication.class, Jsr310JpaConverters.class})
@EnableAsync
public class GmoskApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"));
		SpringApplication.run(GmoskApplication.class, args);
	}

	/**
	 * Servletコンテナ起動時の設定クラス認識。
	 * warファイルデプロイのために必要
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GmoskApplication.class);
	}

	//Spring MVCで日付の文字列をLocalDateに自動的にパースさせる
	@Bean
	public Formatter<LocalDate> localDateFormatter() {
		return new Formatter<LocalDate>() {
			@Override
			public String print(LocalDate object, Locale locale) {
				return object.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			}
			@Override
			public LocalDate parse(String text, Locale locale) throws ParseException {
				return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			}
		};
	}
}
