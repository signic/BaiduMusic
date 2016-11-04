package lanou.baidumusic.tool.volley;

/**
 * Created by dllo on 16/10/26.
 */
public final class Values {

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

    // 音乐 -> 歌单 -> 最热/最新 -> 列表
    public static final String MUSIC_PLAYLIST_LIST_FRONT = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?method=baidu.ting.diy.gedanInfo&from=ios&listid=";
    public static final String MUSIC_PLAYLIST_LIST_BEHIND = "&version=5.2.3&from=ios&channel=appstore";

    // 音乐 -> 榜单
    public static final String MUSIC_TOPLIST = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu" +
            ".ting.billboard.billCategory&format=json&from=ios&version=5.2.1&from=ios&channel=appstore";

    // 音乐 -> 榜单 -> 列表
    public static final String MUSIC_TOPLIST_LIST_FRONT = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=";
    public static final String MUSIC_TOPLIST_LIST_BEHIND =
            "&format=json&offset=0&size=50&from=ios&fields=title,song_id,author,resource_type," +
            "havehigh,is_new,has_mv_mobile,album_title,ting_uid,album_id,charge," +
                    "all_rate&version=5.2.1&from=ios&channel=appstore";

    // 音乐 -> 歌单 -> 列表 -> 歌曲信息
    public static final String SONG_INFO_FRONT ="http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=webapp_music&method=baidu.ting.song.play&format=json&callback=&songid=";
    public static final String SONG_INFO_BEHIND =  "&_=1413017198449";

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

    // 动态
    public static final String DYNAMIC = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=android&version=5.9.0" +
            ".0&channel=1382d&operator=0&method=baidu.ting.ugcfriend" +
            ".getList&format=json&param=gQHPwbE1lIc4uU" +
            "%2Fw5euOhYZy58BhsslJjB1hwSU1iBbiiphbJkqubemS8SVokSuKjcgG7FUkgMCC6K%2FtuVp0jg%3D%3D" +
            "&timestamp=1477732262&sign=8bfc0526cc289b44edf23d30cb6c8d2d";

    // 直播
    public static final String LIVE = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=android&version=5.9.0" +
            ".0&channel=1382d&operator=0&method=baidu.ting.show.live&page_no=1&page_size=40";

    // gedan
    public static final String GEDAN = "http://tingapi.ting.baidu" +
            ".com/v1/restserver/ting?from=android&version=5.9.0" +
            ".0&channel=1382d&operator=0&method=baidu.ting.ugcdiy" +
            ".getBaseInfo&param=47mzNr5n39MPtV6NP4AIDtNFDfevk" +
            "%2FspU8Tzgz74COFHTEPqY66l6O7eSwxrQ7EahS%2BcJhfh1N3qQPTsoIIrQgkq6CI5fcxVkghFp3i" +
            "%2FA2q2NyLpJ6iKuvlxcS65YVJB5HIZENW9uyANONPauxXvMA%3D%3D&timestamp=1478003971&sign" +
            "=6349632da1a19cb19cb585457db9a9ed";
}
