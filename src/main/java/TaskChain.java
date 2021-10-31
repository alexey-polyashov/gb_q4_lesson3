public interface TaskChain {
    public void stop();
    public void nextStep();
    public void start();
    public Task getCurrentTask();
    public TaskChainStates getState();
}
