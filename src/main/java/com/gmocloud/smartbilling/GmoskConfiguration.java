package com.gmocloud.smartbilling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/08.
 */
@Configuration
@EnableAsync // @Asyncの有効化
public class GmoskConfiguration {

    // 非同期処理のスレッドプール設定
    // 非同期処理効率化のため、@Asyncでスレッドプールを使用するように設定する
    // （デフォルトでは@Asyncで非同期実行の度に新しくスレッドが生成される）

    //デフォルト ※@Asyncデフォルトだと"taskExecutor"という名前のBeanが利用される
    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(40);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Default-");
        executor.initialize();
        return executor;
    }

    // 操作ログ用のタスク処理設定
    // ※シングルスレッドでキューのログを処理させる
    @Bean
    public AsyncTaskExecutor logTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Log-");
        executor.initialize();
        return executor;
    }
}
