package com.aol.cyclops.types.futurestream;

import java.util.Map;
import java.util.function.Function;

import com.aol.cyclops.data.async.Queue;
import com.aol.cyclops.data.async.QueueFactory;

/**
 * 
 * interface that defines the conversion of an object to a queue
 * 
 * @author johnmcclean
 *
 * @param <U> Data type
 */
public interface ToQueue<U> {
    /**
     * @return Data in a queue
     */
    abstract Queue<U> toQueue();

    /**
     * Sharded data in queues
     * 
     * @param shards Map of Queues sharded by key K
     * @param sharder Sharder function
     */
    abstract <K> void toQueue(Map<K, Queue<U>> shards, Function<? super U, ? extends K> sharder);

    /**
     * @return Factory for creating Queues to be populated
     */
    abstract QueueFactory<U> getQueueFactory();

    /**
     * Method to create a Queue that can be modified by supplied funciton
     * 
     * @param modifier Function to modify default Queue
     * @return Populated Queue.
     */
    abstract Queue<U> toQueue(Function<Queue, Queue> modifier);

    void addToQueue(Queue queue);

}
