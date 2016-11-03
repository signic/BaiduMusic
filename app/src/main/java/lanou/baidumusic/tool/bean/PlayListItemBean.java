package lanou.baidumusic.tool.bean;

import java.util.List;

/**
 * Created by dllo on 16/11/2.
 */
public class PlayListItemBean {



    private int error_code;


    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private int comment_num;


        private InfoBean info;
        private int iscollect;
        private int song_num;
        private String share_url;
        private int have_more;
        private int listen_num;
        private int share_num;
        private int collect_num;


        private List<CollectorBean> collector;


        private List<SonglistBean> songlist;

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public int getIscollect() {
            return iscollect;
        }

        public void setIscollect(int iscollect) {
            this.iscollect = iscollect;
        }

        public int getSong_num() {
            return song_num;
        }

        public void setSong_num(int song_num) {
            this.song_num = song_num;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public int getHave_more() {
            return have_more;
        }

        public void setHave_more(int have_more) {
            this.have_more = have_more;
        }

        public int getListen_num() {
            return listen_num;
        }

        public void setListen_num(int listen_num) {
            this.listen_num = listen_num;
        }

        public int getShare_num() {
            return share_num;
        }

        public void setShare_num(int share_num) {
            this.share_num = share_num;
        }

        public int getCollect_num() {
            return collect_num;
        }

        public void setCollect_num(int collect_num) {
            this.collect_num = collect_num;
        }

        public List<CollectorBean> getCollector() {
            return collector;
        }

        public void setCollector(List<CollectorBean> collector) {
            this.collector = collector;
        }

        public List<SonglistBean> getSonglist() {
            return songlist;
        }

        public void setSonglist(List<SonglistBean> songlist) {
            this.songlist = songlist;
        }

        public static class InfoBean {
            private String list_id;
            private String list_tag;
            private int status;
            private String list_pic;
            private int is_recommend;
            private int createtime;
            /**
             * userpic : http://musicugc.cdn.qianqian.com/ugcdiy/pic/06ec0f70de73206cb6a2168af7d98d41.jpg@w_150,h_150,o_1
             * flag : 010
             * userpic_small : http://musicugc.cdn.qianqian.com/ugcdiy/pic/06ec0f70de73206cb6a2168af7d98d41.jpg@w_55,h_55,o_1
             * userid : 2698311508
             * username : icon迷妹
             */

            private UserinfoBean userinfo;
            private int lastmodify;
            private String list_desc;
            private String list_title;
            private String list_pic_large;
            private String list_pic_small;
            private String list_pic_huge;
            private String list_pic_middle;

            public String getList_id() {
                return list_id;
            }

            public void setList_id(String list_id) {
                this.list_id = list_id;
            }

            public String getList_tag() {
                return list_tag;
            }

            public void setList_tag(String list_tag) {
                this.list_tag = list_tag;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getList_pic() {
                return list_pic;
            }

            public void setList_pic(String list_pic) {
                this.list_pic = list_pic;
            }

            public int getIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(int is_recommend) {
                this.is_recommend = is_recommend;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public UserinfoBean getUserinfo() {
                return userinfo;
            }

            public void setUserinfo(UserinfoBean userinfo) {
                this.userinfo = userinfo;
            }

            public int getLastmodify() {
                return lastmodify;
            }

            public void setLastmodify(int lastmodify) {
                this.lastmodify = lastmodify;
            }

            public String getList_desc() {
                return list_desc;
            }

            public void setList_desc(String list_desc) {
                this.list_desc = list_desc;
            }

            public String getList_title() {
                return list_title;
            }

            public void setList_title(String list_title) {
                this.list_title = list_title;
            }

            public String getList_pic_large() {
                return list_pic_large;
            }

            public void setList_pic_large(String list_pic_large) {
                this.list_pic_large = list_pic_large;
            }

            public String getList_pic_small() {
                return list_pic_small;
            }

            public void setList_pic_small(String list_pic_small) {
                this.list_pic_small = list_pic_small;
            }

            public String getList_pic_huge() {
                return list_pic_huge;
            }

            public void setList_pic_huge(String list_pic_huge) {
                this.list_pic_huge = list_pic_huge;
            }

            public String getList_pic_middle() {
                return list_pic_middle;
            }

            public void setList_pic_middle(String list_pic_middle) {
                this.list_pic_middle = list_pic_middle;
            }

            public static class UserinfoBean {
                private String userpic;
                private String flag;
                private String userpic_small;
                private String userid;
                private String username;

                public String getUserpic() {
                    return userpic;
                }

                public void setUserpic(String userpic) {
                    this.userpic = userpic;
                }

                public String getFlag() {
                    return flag;
                }

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public String getUserpic_small() {
                    return userpic_small;
                }

                public void setUserpic_small(String userpic_small) {
                    this.userpic_small = userpic_small;
                }

                public String getUserid() {
                    return userid;
                }

                public void setUserid(String userid) {
                    this.userid = userid;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }
            }
        }

        public static class CollectorBean {
            private String userpic;
            private String flag;
            private String userpic_small;
            private String userid;
            private String username;

            public String getUserpic() {
                return userpic;
            }

            public void setUserpic(String userpic) {
                this.userpic = userpic;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getUserpic_small() {
                return userpic_small;
            }

            public void setUserpic_small(String userpic_small) {
                this.userpic_small = userpic_small;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class SonglistBean {
            private String resource_type_ext;
            private String ting_uid;
            private String mv_provider;
            private String del_status;
            private String source;
            private String author;
            private String pic_s130;
            private String share;
            private String has_mv;
            private String album_title;
            private String title;
            private String high_rate;
            private String artist_id;
            private String distribution;
            private String relate_status;
            private int learn;
            private String pic_big;
            private String album_id;
            private String is_ksong;
            private String bitrate_fee;
            private String song_source;
            private String all_artist_id;
            private String all_artist_ting_uid;
            private String pic_radio;
            private String all_rate;
            private String copy_type;
            private String album_no;
            private String korean_bb_song;
            private String song_id;
            private int has_mv_mobile;
            private String resource_type;
            private String versions;
            private String pic_s500;
            private String toneid;
            private String is_charge;

            public String getResource_type_ext() {
                return resource_type_ext;
            }

            public void setResource_type_ext(String resource_type_ext) {
                this.resource_type_ext = resource_type_ext;
            }

            public String getTing_uid() {
                return ting_uid;
            }

            public void setTing_uid(String ting_uid) {
                this.ting_uid = ting_uid;
            }

            public String getMv_provider() {
                return mv_provider;
            }

            public void setMv_provider(String mv_provider) {
                this.mv_provider = mv_provider;
            }

            public String getDel_status() {
                return del_status;
            }

            public void setDel_status(String del_status) {
                this.del_status = del_status;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getPic_s130() {
                return pic_s130;
            }

            public void setPic_s130(String pic_s130) {
                this.pic_s130 = pic_s130;
            }

            public String getShare() {
                return share;
            }

            public void setShare(String share) {
                this.share = share;
            }

            public String getHas_mv() {
                return has_mv;
            }

            public void setHas_mv(String has_mv) {
                this.has_mv = has_mv;
            }

            public String getAlbum_title() {
                return album_title;
            }

            public void setAlbum_title(String album_title) {
                this.album_title = album_title;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getHigh_rate() {
                return high_rate;
            }

            public void setHigh_rate(String high_rate) {
                this.high_rate = high_rate;
            }

            public String getArtist_id() {
                return artist_id;
            }

            public void setArtist_id(String artist_id) {
                this.artist_id = artist_id;
            }

            public String getDistribution() {
                return distribution;
            }

            public void setDistribution(String distribution) {
                this.distribution = distribution;
            }

            public String getRelate_status() {
                return relate_status;
            }

            public void setRelate_status(String relate_status) {
                this.relate_status = relate_status;
            }

            public int getLearn() {
                return learn;
            }

            public void setLearn(int learn) {
                this.learn = learn;
            }

            public String getPic_big() {
                return pic_big;
            }

            public void setPic_big(String pic_big) {
                this.pic_big = pic_big;
            }

            public String getAlbum_id() {
                return album_id;
            }

            public void setAlbum_id(String album_id) {
                this.album_id = album_id;
            }

            public String getIs_ksong() {
                return is_ksong;
            }

            public void setIs_ksong(String is_ksong) {
                this.is_ksong = is_ksong;
            }

            public String getBitrate_fee() {
                return bitrate_fee;
            }

            public void setBitrate_fee(String bitrate_fee) {
                this.bitrate_fee = bitrate_fee;
            }

            public String getSong_source() {
                return song_source;
            }

            public void setSong_source(String song_source) {
                this.song_source = song_source;
            }

            public String getAll_artist_id() {
                return all_artist_id;
            }

            public void setAll_artist_id(String all_artist_id) {
                this.all_artist_id = all_artist_id;
            }

            public String getAll_artist_ting_uid() {
                return all_artist_ting_uid;
            }

            public void setAll_artist_ting_uid(String all_artist_ting_uid) {
                this.all_artist_ting_uid = all_artist_ting_uid;
            }

            public String getPic_radio() {
                return pic_radio;
            }

            public void setPic_radio(String pic_radio) {
                this.pic_radio = pic_radio;
            }

            public String getAll_rate() {
                return all_rate;
            }

            public void setAll_rate(String all_rate) {
                this.all_rate = all_rate;
            }

            public String getCopy_type() {
                return copy_type;
            }

            public void setCopy_type(String copy_type) {
                this.copy_type = copy_type;
            }

            public String getAlbum_no() {
                return album_no;
            }

            public void setAlbum_no(String album_no) {
                this.album_no = album_no;
            }

            public String getKorean_bb_song() {
                return korean_bb_song;
            }

            public void setKorean_bb_song(String korean_bb_song) {
                this.korean_bb_song = korean_bb_song;
            }

            public String getSong_id() {
                return song_id;
            }

            public void setSong_id(String song_id) {
                this.song_id = song_id;
            }

            public int getHas_mv_mobile() {
                return has_mv_mobile;
            }

            public void setHas_mv_mobile(int has_mv_mobile) {
                this.has_mv_mobile = has_mv_mobile;
            }

            public String getResource_type() {
                return resource_type;
            }

            public void setResource_type(String resource_type) {
                this.resource_type = resource_type;
            }

            public String getVersions() {
                return versions;
            }

            public void setVersions(String versions) {
                this.versions = versions;
            }

            public String getPic_s500() {
                return pic_s500;
            }

            public void setPic_s500(String pic_s500) {
                this.pic_s500 = pic_s500;
            }

            public String getToneid() {
                return toneid;
            }

            public void setToneid(String toneid) {
                this.toneid = toneid;
            }

            public String getIs_charge() {
                return is_charge;
            }

            public void setIs_charge(String is_charge) {
                this.is_charge = is_charge;
            }
        }
    }
}
