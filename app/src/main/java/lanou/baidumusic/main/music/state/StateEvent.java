package lanou.baidumusic.main.music.state;

/**
 * Created by dllo on 16/11/8.
 */
public class StateEvent {
    private int state;

    public StateEvent(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
