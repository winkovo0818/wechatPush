package com.winkovo.utils;

public class EmotionUtils {
    static final String[] list = {"\\(^o^)/~O(∩_∩)O","(๑´ㅂ`๑)","ヾ(✿ﾟ▽ﾟ)ノ","ヾ(≧≦*)ヾ(*≧∪≦)","(*´・ｖ・)","(≧≦)(*≧▽≦)","｡◕ᴗ◕｡","ƪ(˘⌣˘)ʃ","(◕ᴗ◕✿)","(´▽`)ﾉ"};

    public static String getEmotion(){
        int index = (int)(Math.random()*10);
        if (index < 0 || index > list.length){
            index = 0;
        }
        return list[index];
    }
}
