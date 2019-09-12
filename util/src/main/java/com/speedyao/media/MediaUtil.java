package com.speedyao.media;

import org.apache.commons.io.FileUtils;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 多媒体工具类
 * Created by speedyao on 2018/5/18.
 */
public class MediaUtil {
    private static final String RING_PATH ="Ring01.wav";


    /**
     * 播放ring
     */

    public static void ring(){
        String s = MediaUtil.class.getClassLoader().getResource(RING_PATH).getPath();
        playWav(s);
    }

    /**
     * 播放指定路径的声音
     */
    public static void playWav(String wavPath){
        InputStream ringStream=null;
        try {
            ringStream= new FileInputStream(wavPath);
            AudioStream as = new AudioStream(ringStream);
            AudioPlayer.player.start(as);
            //避免主程序结束，声音没有播放完
            AudioPlayer.player.join(5000);
            AudioPlayer.player.stop(as);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(ringStream!=null){
                try {
                    ringStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void decodeCue(String file) throws IOException {
        List list = FileUtils.readLines(new File(file), "gbk");
        list.forEach(str-> System.out.println(str));
        File bakFile=new File(file);
        if(!bakFile.exists()){
            bakFile.createNewFile();
        }
        FileUtils.writeLines(bakFile,"UTF-8",list);
    }

}
