package hib;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class HistoryPK implements Serializable {
    private long userid;
    private long mid;
    private Timestamp viewtime;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public Timestamp getViewtime() {
        return viewtime;
    }

    public void setViewtime(Timestamp viewtime) {
        this.viewtime = viewtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryPK historyPK = (HistoryPK) o;
        return userid == historyPK.userid &&
                mid == historyPK.mid &&
                Objects.equals(viewtime, historyPK.viewtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, mid, viewtime);
    }
}
