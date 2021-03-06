package com.aol.cyclops.types;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.types.stream.ConvertableSequence;

public interface ExtendedTraversable<T> extends Traversable<T>, TransformerTraversable<T>, Foldable<T>, Iterable<T>, ConvertableSequence<T> {

    /**
     * Generate the permutations based on values in the ReactiveSeq Makes use of
     * Streamable to store intermediate stages in a collection
     * 
     * 
     * @return Permutations from this ReactiveSeq
     */
    default ExtendedTraversable<ReactiveSeq<T>> permutations() {
        return stream().permutations();
    }

    /**
     * <pre>
     * {@code
     *   ReactiveSeq.of(1,2,3).combinations(2)
     *   
     *   //ReactiveSeq[ReactiveSeq[1,2],ReactiveSeq[1,3],ReactiveSeq[2,3]]
     * }
     * </pre>
     * 
     * 
     * @param size
     *            of combinations
     * @return All combinations of the elements in this stream of the specified
     *         size
     */
    default ExtendedTraversable<ReactiveSeq<T>> combinations(int size) {
        return stream().combinations(size);
    }

    /**
     * <pre>
     * {@code
     *   ReactiveSeq.of(1,2,3).combinations()
     *   
     *   //ReactiveSeq[ReactiveSeq[],ReactiveSeq[1],ReactiveSeq[2],ReactiveSeq[3].ReactiveSeq[1,2],ReactiveSeq[1,3],ReactiveSeq[2,3]
     *   			,ReactiveSeq[1,2,3]]
     * }
     * </pre>
     * 
     * 
     * @return All combinations of the elements in this stream
     */
    default ExtendedTraversable<ReactiveSeq<T>> combinations() {
        return stream().combinations();
    }

    /* (non-Javadoc)
     * @see com.aol.cyclops.types.Traversable#stream()
     */
    @Override
    default ReactiveSeq<T> stream() {

        return ConvertableSequence.super.stream();
    }

}
