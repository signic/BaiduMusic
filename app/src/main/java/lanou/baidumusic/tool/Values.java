package lanou.baidumusic.tool;

/**
 * Created by dllo on 16/10/26.
 */
public class Values {

    // 音乐 -> 歌单 -> 最热
    public static final String MUSIC_PLAYLIST_HOT = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?method=baidu.ting.diy" +
            ".gedan&page_no=1&page_size=30&from=ios&version=5.2.3&from=ios&channel=appstore";

    // 音乐 -> 榜单
    public static final String MUSIC_TOPLIST = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?method=baidu" +
            ".ting.billboard.billCategory&format=json&from=ios&version=5.2" +
            ".1&from=ios&channel=appstore";
}
