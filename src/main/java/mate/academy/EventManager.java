package mate.academy;

import java.util.List;
import java.util.concurrent.Callable;
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
        try {
            executorService.invokeAll(listeners.stream()
                    .map(l -> (Callable<EventListener>) () -> {
                        l.onEvent(event);
                        return l;
                    })
                    .toList());
        } catch (InterruptedException e) {
            throw new RuntimeException("Exception during notifyEvent:", e);
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
