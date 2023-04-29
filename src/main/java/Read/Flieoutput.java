package Read;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Flieoutput {
    public void Flie(){
        try{
            Path path= Paths.get("data/translate");
            Files.createDirectory(path);//创建文件夹
            File file = new File("data/translate/Information.txt");//创建需要的文件
            File file1=new File("data/translate/menu.txt");
            File file2=new File("data/translate/baidu.txt");
            if(file.createNewFile()||file1.createNewFile()) {
                FileWriter fw = new FileWriter(file);
                FileWriter fw1 = new FileWriter(file1);
                FileWriter fw2 = new FileWriter(file2);
                fw.write("本机器人以mirai开源框架为基础，在其上编写插件完成各种功能");//初始化，用户可自由修改
                fw1.write("--呼叫成功，喵~---\n" +
                        "--翻译 机器人信息--\n" +
                        "注:翻译支持中英互译,\n" +
                        "采用百度翻译开放平台api\n" +
                        "——————————————");
                fw2.write("//插件采用百度翻译开发平台接口(通用文本翻译)，顾你需要拥有自己的id和密钥\n" +
                        "入口    http://api.fanyi.baidu.com/\n" +
                        "ID=xxxxxxxxx;(请将xxxxxxx改为你的申请到的APP id,结尾分号别删)\n" +
                        "KEY=xxxxxxxxx;(请将xxxxxxx改为你的申请到的密钥,结尾分号别删)");
                fw.close();
                fw1.close();
                fw2.close();
                System.out.println("文件创建成功！，请前往mirai目录下的data/translate进行相应的配置");
            }else
                System.out.println("已加载文件,如若未进行配置，请前往mirai目录下的data/translate进行相应配置");
        }
        catch(IOException ioe) {
            System.out.println("已创建文件");
        }
    }
}
