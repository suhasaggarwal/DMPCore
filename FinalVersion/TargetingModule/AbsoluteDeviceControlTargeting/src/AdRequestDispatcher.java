



import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class AdRequestDispatcher {
    private AdResource resource;
    private ConcurrentLinkedQueue<AdRequest> queue = new ConcurrentLinkedQueue<AdRequest>();
    private int maxParallelFetches;
    private ExecutorService executor = null;

    AdRequestDispatcher(AdResource resource, int maxParallelFetches) {
        this.resource = resource;
        this.maxParallelFetches = maxParallelFetches;
    }

    synchronized void dispatch(AdRequest e) {
        queue.add(e);
        startExecutor();
    }

    /**
     * Creates and starts a new executor if there is no existing one
     * or if the existing one is shut down.
     */
    private synchronized void startExecutor() {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(maxParallelFetches);
            executor.submit(new DispatchWorker());
        }
    }

    private class DispatchWorker implements Runnable {
        private static final String TAG = "Tapad/AdDispatchWorker";

        public void run() {
            AdRequest req;
            while ((req = queue.poll()) != null) {
                try {
                    

                    AdResponse response = resource.get(req);
                    req.onResponse(response);
                    
                } catch (Exception e)  {
                   System.out.println("error");
                }
            }

            synchronized (AdRequestDispatcher.this) {
                // If we're done. Shut down the executor service for now.
                if (queue.isEmpty()) executor.shutdown();
            }
        }
    }

}