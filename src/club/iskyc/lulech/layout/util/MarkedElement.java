package club.iskyc.lulech.layout.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This is a data type for marking a data. With making type as {@code M}, data type as {@code E}.
 *
 * @author lulec
 * @since 1.0
 * */
public class MarkedElement<M, E> implements Comparable<MarkedElement<M, E>>, java.io.Serializable {
    /**
     * Marking for the data.
     * */
    private M mark;
    /**
     * Data need to store in.
     * */
    private E data;

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

    /**
     * Only return hash code mark.
     * */
    @Override
    public int hashCode() {
        return Objects.hash(mark);
    }

    /**
     * If marking is instance of {@code Comparable} use marking's
     * {@code compareTo(Object o)} to compare two object.
     * Else compare {@code toString()} values.
     * */
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
}
