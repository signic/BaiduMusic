package lanou.baidumusic.tool.state;

/**
 * Created by dllo on 16/11/11.
 */
public class SerToAtyEvent {
    private int position;
    private String pic;
    private String title;
    private String albumTitle;
    private String author;

    public SerToAtyEvent(int position, String pic, String title, String albumTitle, String author) {
        this.position = position;
        this.pic = pic;
        this.title = title;
        this.albumTitle = albumTitle;
        this.author = author;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
