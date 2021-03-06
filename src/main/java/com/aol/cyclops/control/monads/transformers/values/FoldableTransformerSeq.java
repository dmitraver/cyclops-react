package com.aol.cyclops.control.monads.transformers.values;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.types.FilterableFunctor;
import com.aol.cyclops.types.anyM.NestedCollectable;
import com.aol.cyclops.types.anyM.NestedFoldable;

public interface FoldableTransformerSeq<T> extends TransformerSeq<T>, NestedFoldable<T>, NestedCollectable<T>, FilterableFunctor<T> {

    /* (non-Javadoc)
     * @see com.aol.cyclops.control.monads.transformers.values.TransformerSeq#stream()
     */
    @Override
    default ReactiveSeq<T> stream() {
        return TransformerSeq.super.stream();
    }

}
