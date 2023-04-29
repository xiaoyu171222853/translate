package Read;

import java.io.FileInputStream;


public class FileInput {
    static String mes;
    public String Reads(String address) throws Exception {
        FileInputStream inputStream = new FileInputStream(address);
        byte txt[]=new byte[2048];
        int len;
        while ((len=inputStream.read(txt))!=-1){
           mes= new String(txt,0,len);
        }
        inputStream.close();
        return mes;
    }
}
