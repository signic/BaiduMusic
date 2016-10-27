package lanou.baidumusic.tool;

/**
 * Created by dllo on 16/10/26.
 */
public class Values {

    // 音乐 -> 推荐
    public static final String MUSIC_RECOMMENDATION = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=android&version=5.9.0" +
            ".0&channel=1382d&operator=0&method=baidu.ting.plaza" +
            ".index&cuid=90AE6B089CD064D03DDF18681495AC77";

    // 音乐 -> 歌单 -> 最热
    public static final String MUSIC_PLAYLIST_HOT = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=android&version=5.9.0" +
            ".0&channel=1382d&operator=0&method=baidu.ting.ugcdiy" +
            ".getChanneldiy&param=7mri8DH6%2FXPfGVVNqT8mFagWl1yW8ZwsqghyiS" +
            "%2BMc5Zpz6iOAgNUW5Nh3YVeC%2BUlcdGz4a%2F4HrX3cniukhSTohoY3BBFH7Ho6" +
            "%2FfpGCc1sM6H8Kl39kAZ2LSVq2u5boAY&timestamp=1477535244&sign" +
            "=30eb754e9c38d6b83036c96fa26b2d41";

    // 音乐 -> 歌单 -> 最新
    public static final String MUSIC_PLAYLIST_LAST = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=android&version=5.9.0" +
            ".0&channel=1382d&operator=0&method=baidu.ting.ugcdiy" +
            ".getChanneldiy&param=qbVQKf%2BxXMt6dV1ngdfCGQ292jurRHn2613lmi7ThnnijQK5TjmjR" +
            "%2Bssl1czjeLJpbC93SCXgFiKPv41YhPq4v5yWm9zo83gdyWUSxqePC7naR24MzomxMcQO3kTiEm5" +
            "&timestamp=1477534974&sign=873191c8ce2316a6a9c75a9b3da2b7c9";

    // 音乐 -> 榜单
    public static final String MUSIC_TOPLIST = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?method=baidu" +
            ".ting.billboard.billCategory&format=json&from=ios&version=5.2" +
            ".1&from=ios&channel=appstore";

    // 音乐 -> 视频 -> 最新
    public static final String MUSIC_VIDEO_LAST = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=android&version=5.9.0" +
            ".0&channel=1382d&operator=0&provider=11%2C12&method=baidu.ting.mv" +
            ".searchMV&format=json&order=1&page_num=1&page_size=20&query=%E5%85%A8%E9%83%A8";

    // 音乐 -> 视频 -> 最热
    public static final String MUSIC_VIDEO_HOT = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=android&version=5.9.0" +
            ".0&channel=1382d&operator=0&provider=11%2C12&method=baidu.ting.mv" +
            ".searchMV&format=json&order=0&page_num=1&page_size=20&query=%E5%85%A8%E9%83%A8";

    // 直播
    public static final String LIVE = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=android&version=5.9.0" +
            ".0&channel=1382d&operator=0&method=baidu.ting.show.live&page_no=1&page_size=40";
}
