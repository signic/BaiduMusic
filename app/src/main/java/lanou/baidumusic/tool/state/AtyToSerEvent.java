package lanou.baidumusic.tool.state;

/**
 * Created by dllo on 16/11/8.
 */
public class AtyToSerEvent {
    private int state;
    private int position;

    public AtyToSerEvent(int state, int position) {
        this.state = state;
        this.position = position;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
