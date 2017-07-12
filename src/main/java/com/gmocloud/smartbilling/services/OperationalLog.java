package com.gmocloud.smartbilling.services;

import com.gmocloud.smartbilling.security.UserSession;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/10.
 */
@Log
@Service
public class OperationalLog {

    @Async("logTaskExecutor") //シングルスレッドでキューのログを処理する
    public void post(String userId, String message) {
        UserSession user = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("start");
        log.info(user.getUserEntity().getUserName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("end!");
    }
}
