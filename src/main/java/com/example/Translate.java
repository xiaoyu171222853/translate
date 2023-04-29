package com.example;
import Read.*;
import Translate.*;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.message.data.*;

public final class Translate extends JavaPlugin {
    static String send;//成员变量
    public static final Translate INSTANCE = new Translate();
    public Translate() {
        super(new JvmPluginDescriptionBuilder("com.example.Translate", "1.2.5")
                .author("小宇")
                .info("第一次摸索编写")
                .name("Translation")
                .build()
        );
    }
    //onEnable启用期间被调用
    @Override
    public void onEnable() {
        Send s = new Send();
        s.Send_Mes();
        try {
            Trans.assignment();
        } catch (Exception e) {
            System.out.println("读取错误");
        }
        Flieoutput flieoutput=new Flieoutput();
        flieoutput.Flie();
        System.out.println("!!!重要的事情写在前面!!!\n" +
                     "请勿将mirai放在C盘,否则translate插件会有问题\n" +
                             "请注意请注意请注意\n" +
                            "!!!重要的事情写在前面!!!\n");
    }
    //必将是一个庞大的类
    public static class Send {
        public void Send_Mes() {
            System.out.println("正在监听组群消息");
            Flieoutput flieoutput=new Flieoutput();
            // 创建监听(Global)、发送简单消息(群组)/功能区
            GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
                String content = event.getMessage().contentToString();
                if (content.contains("菜单")) {
                    try {
                        FileInput file=new FileInput();
                        send = file.Reads("data/translate/menu.txt");
                    } catch (Exception e) {
                        Flieoutput flieOutput=new Flieoutput();
                        flieOutput.Flie();
                    }
                    event.getSubject().sendMessage(send);
                }
                if (content.equals("机器人信息")) {
                    try {
                    FileInput file=new FileInput();
                    send = file.Reads("data/translate/Information.txt");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                    event.getSubject().sendMessage(send);
                }

                if (content.contains("翻译")) {
                    try {
                        Trans t = new Trans();
                        content = content.replace(" ", "");//将所有收到的和翻译有关的消息清除空格
                        MessageChain mc=new MessageChainBuilder()
                                        .append(new QuoteReply(event.getMessage()))
                                        .append(t.tran(content))
                                        .build();
                        event.getSubject().sendMessage(mc);
                    } catch (Exception e) {
                        System.out.println("请配置data/translate/baidu.txt,或者发现未知错误，联系小宇:171222853");
                    }
                }
            });
        }
    }

    //翻译，中英互译
    static class Trans {
           static String id,key;
       static int startIndex,endIndex;
          public static void assignment() {//赋值
              FileInput file=new FileInput();
              String temp = null;
              try {
                  temp = file.Reads("data/translate/baidu.txt");
              } catch (Exception e) {
                  System.out.println("找不到文件");
              }
              startIndex = temp.indexOf("ID")+3;
             endIndex = temp.indexOf(";", startIndex);
               id = temp.substring(startIndex, endIndex);
              startIndex = temp.indexOf("KEY")+4;
              endIndex = temp.indexOf(";", startIndex);
              key=temp.substring(startIndex, endIndex);
            }
        private String tran(String mes) throws Exception {
            final String APP_ID = id;
            final String SECURITY_KEY = key;
            TransApi api = new TransApi(APP_ID, SECURITY_KEY);
            String responseBody;
            int startIndex, start, end;
            if (mes.contains("翻译")) {
                startIndex = mes.indexOf("翻译") + 2;
                responseBody = api.getTransResult(mes.substring(startIndex), "en", "zh");
                start = responseBody.lastIndexOf("dst") + 6;
                end = responseBody.indexOf("\"", start);
                String result = responseBody.substring(start, end);
                UnicodeToZh u = new UnicodeToZh();
                String temp = u.utz(result);
                if (temp.equals(mes.substring(startIndex))) {
                    responseBody = api.getTransResult(mes.substring(startIndex), "zh", "en");
                    start = responseBody.lastIndexOf("dst") + 6;
                    end = responseBody.indexOf("\"", start);
                    send = responseBody.substring(start, end);
                } else {
                    send = temp;
                }
            }
            return send;
        }
    }
}
    //Unicode转中文
    class UnicodeToZh {
        public String utz(String ascii) {
            int n = ascii.length() / 6;
            StringBuilder sb = new StringBuilder(n);
            for (int i = 0, j = 2; i < n; i++, j += 6) {
                String code = ascii.substring(j, j + 4);
                char ch = (char) Integer.parseInt(code, 16);
                sb.append(ch);
            }
            return sb.toString();
        }
    }
