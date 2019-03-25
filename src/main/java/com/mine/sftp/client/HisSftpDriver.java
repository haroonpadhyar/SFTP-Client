package com.mine.sftp.client;

import java.io.File;

public class HisSftpDriver {

    public static void main(String[] args) {
        // load config
        if(args.length != 6){
            System.out.println("Please provide init arguments: ");
            System.out.println("Usage: java -jar ehis-client-jar-with-dependencies.jar [HOST] [USER] [PRI-KEY-FILE] [PASS_CODE] [LOCAL-WORKING-DIR] [TIME-TO-START]");
            System.out.println("Example: java -jar ehis-client-jar-with-dependencies.jar N16339 adminkey C:\\Users\\hpadhyar\\my-sftp-key.ppk Aa123456 C:\\Users\\hpadhyar\\HIS-SFTP-DATA 00:00:00");
            System.exit(1);
        }

        SystemConstant.SFTP_HOST = args[0];
        SystemConstant.SFTP_USER = args[1];
        SystemConstant.PRI_KEY_FILE = args[2];
        SystemConstant.PRI_KEY_PWD = args[3];
        SystemConstant.LOCAL_WORKING_DIR = args[4];
        SystemConstant.TIME_TO_RUN = args[5];

        init();
        System.out.println("Initializes with parameters");

        new ScheduledTask().run("");
    }

    private static void init(){
        File file = new File(SystemConstant.LOCAL_WORKING_DIR);
        if(!file.exists()){
            file.mkdir();
        }
    }
}
