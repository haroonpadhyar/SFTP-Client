package com.mine.sftp.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
    private Logger logger = Logger.getInstance();
    private long timeInterval = 2*60*1000;// 2min
    private long timeIntervalSec = 2*60;// 2min

    @Override
    public void run() {
        logger.init();
        LocalDateTime runAt = LocalDateTime.now();
        logger.info("Executing SFTP Client...");
        try {
            new SftpClient().process();
        } catch (Exception e) {
            logger.error(Util.getStackTraceAsString(e));
        }

        LocalDateTime nextRun = runAt.plusSeconds(timeIntervalSec);
        logger.info("Next run will be at: " + nextRun.format(DateTimeFormatter.ofPattern("yyyy-MM-dd|kk:mm:ss")));
        logger.destroy();
    }

    public void run(String timeToRun) {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, timeInterval);
    }
}
