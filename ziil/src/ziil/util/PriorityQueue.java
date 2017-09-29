package ziil.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * A priority queue. Queues items by their priority
 * @author Manuel
 *
 * @param <E> The type of the items to store
 */
public class PriorityQueue<E> {
	private final Map<E, Integer> items;
	
	/**
	 * Creates a new priority queue
	 */
	public PriorityQueue() {
		items = new HashMap<>();
	}
	
	/**
	 * Enqueues an item with a priority
	 * If the item already is in the queue and has a lower priority, nothing happens
	 * If the item already is in the queue and has a higher priority, it will be overwritten with the new value
	 * @param item The item to enqueue
	 * @param priority The priority of the item
	 */
	public void enqueue(E item, int priority) {
		Integer existingPriority = items.get(item);
		
		if (existingPriority == null || existingPriority > priority) {
			items.put(item, priority);
		}
	}
	
	/**
	 * Dequeue a random item with the lowest priority
	 * @return The item and its priority
	 */
	public Entry<E, Integer> dequeue() {
		Optional<Entry<E, Integer>> itemToDequeue = items
			.entrySet()
			.stream()
			.sorted((entry1, entry2) -> Integer.compare(entry1.getValue(), entry2.getValue()))
			.findFirst();
		
		if (!itemToDequeue.isPresent()) {
			return null;
		}
		Entry<E, Integer> item = itemToDequeue.get();
		items.remove(item.getKey());
		return item;
	}
	
	/**
	 * Check if this queue is empty
	 * @return True if this queue is empty
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}
}
