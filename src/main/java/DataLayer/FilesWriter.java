package DataLayer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FilesWriter {
    public static void writeBill(String text) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss");
            LocalDateTime now = LocalDateTime.now();
            String title="ORDER"+dtf.format(now);
            PrintWriter writer = new PrintWriter(title+".txt", "UTF-8");
            writer.println(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeReport(int type,String text) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss");
            LocalDateTime now = LocalDateTime.now();
            String title="REPORT"+type+"_"+dtf.format(now);
            PrintWriter writer = new PrintWriter(title+".txt", "UTF-8");
            writer.println(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
