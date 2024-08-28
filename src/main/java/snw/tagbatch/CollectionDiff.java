package snw.tagbatch;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;

public record CollectionDiff<T>(ImmutableList<T> added, ImmutableList<T> removed) {
    public static <T> CollectionDiff<T> create(
            Collection<T> origin,
            Collection<T> updated
    ) {
        Collection<T> mutableAdded = new ArrayList<>(updated);
        mutableAdded.removeAll(origin);
        Collection<T> mutableRemoved = new ArrayList<>(origin);
        mutableRemoved.removeAll(updated);
        ImmutableList<T> immutableAdded = ImmutableList.copyOf(mutableAdded);
        ImmutableList<T> immutableRemoved = ImmutableList.copyOf(mutableRemoved);
        return new CollectionDiff<>(immutableAdded, immutableRemoved);
    }
}
