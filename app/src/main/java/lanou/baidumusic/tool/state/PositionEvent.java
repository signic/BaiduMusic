package lanou.baidumusic.tool.state;

/**
 * Created by dllo on 16/11/11.
 */
public class PositionEvent {
    private int position;

    public PositionEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
