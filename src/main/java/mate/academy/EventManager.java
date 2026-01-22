package mate.academy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class EventManager {
    private final ExecutorService executorService = ForkJoinPool.commonPool();

    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    public void deregisterListener(EventListener listener) {
        listeners.remove(listener);
    }

    public void notifyEvent(Event event) {
        listeners.forEach(e -> e.onEvent(event));
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
