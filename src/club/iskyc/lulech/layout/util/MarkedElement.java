package club.iskyc.lulech.layout.util;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

public class MarkedElement<M, E> implements Comparable<MarkedElement<M, E>>, java.io.Serializable {

    private M mark;

    private E data;

    public MarkedElement(@NotNull E data) {
        if (this.data.getClass().equals(this.mark.getClass()))
            this.mark = (M)data;
        this.data = data;
    }

    public MarkedElement(@NotNull M mark, E data) {
        this.mark = mark;
        this.data = data;
    }

    public M getMark() {
        return mark;
    }

    public void setMark(@NotNull M mark) {
        this.mark = mark;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setMarkedData(@NotNull M mark, E data) {
        this.mark = mark;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkedElement<?, ?> that = (MarkedElement<?, ?>) o;
        return Objects.equals(mark, that.mark) &&
                Objects.equals(data, that.data);
    }

    @Override
    public String toString() {
        return "MarkedElement{" +
                "mark=" + mark +
                ", data=" + data +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark);
    }

    @Override
    public int compareTo(MarkedElement<M, E> o) {
        if (null == o) return 1;
        if (null == this.mark && null == o.mark) return 0;
        if (null == this.mark) return -1;
        if (this.mark.getClass().isInstance(Comparable.class)) {
            Comparable<M> v1 = (Comparable<M>) this.mark;
            M v2 = (M) o.mark;
            return v1.compareTo(v2);
        }
        return this.mark.toString().compareTo(o.mark.toString());
    }

    private static final long serialVersionUID = -5683452581122892188L;
}
