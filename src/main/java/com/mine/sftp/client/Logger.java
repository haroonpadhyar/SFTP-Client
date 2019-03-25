package com.mine.sftp.client;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger { // TODO Rolling on size
    private String workingDir;
    private PrintWriter writer;
    private static Logger instance;

    public static Logger getInstance(){
        if(instance == null){
            synchronized (Logger.class){
                if(instance == null) {
                    instance = new Logger(SystemConstant.LOCAL_WORKING_DIR);
                }
            }
        }
        return instance;
    }

    private Logger(String workingDir){
        this.workingDir = workingDir;
    }

    public void init(){
        try {
            String fileName = "log_"+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+".txt";
            FileWriter fileWriter = new FileWriter(this.workingDir+ File.separator+fileName, true);
            writer = new PrintWriter(fileWriter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void info(String msg){
        try {

            String logMsg = new StringBuilder("Info: ")
                    .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd|kk:mm:ss:SSS")))
                    .append(" - ")
                    .append(msg)
                    .toString();
            writer.println(logMsg);
            System.out.println(logMsg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void error(String msg){
        try {

            String logMsg = new StringBuilder("*Err: ")
                    .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd|kk:mm:ss:SSS")))
                    .append(" - ")
                    .append(msg)
                    .toString();
            writer.println(logMsg);
            System.out.println(logMsg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void destroy(){
        try {
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        try{

            String workingDir = "C:\\Users\\hpadhyar\\ZSFTP";
            String fileName = "indexfile.txt";
//            FileWriter fileWriter = new FileWriter(workingDir+ File.separator+fileName, true);
//            PrintWriter writer = new PrintWriter(fileWriter);
//            long t1 = System.currentTimeMillis();
//            long nin = 1000000000;
//            for(int i = 0; i < 6000; i++){
//                String str = (1000000000+i)+"="+new Date();
//                writer.println(str);
//                System.out.println(str);
//            }
//            long t2 = System.currentTimeMillis();
//            System.out.println("Total TimeL "+ (t2-t1));
//            writer.close();

            Properties properties =new Properties();
            properties.load(new FileReader(workingDir+ File.separator+fileName));
            long t1 = System.currentTimeMillis();
            System.out.println(properties.get("1000000111"));
                        long t2 = System.currentTimeMillis();
            System.out.println("Total TimeL "+ (t2-t1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

}
