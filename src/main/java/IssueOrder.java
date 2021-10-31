import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class IssueOrder implements TaskChain{

    List<Document> documents = new ArrayList<>();
    List<Task> tasks = new ArrayList<>();
    Task currentTask;
    TaskChainStates state;
    Date startDate;
    User initiator;

    public IssueOrder(Document doc, User initiator) {
        super();
        state = TaskChainStates.NEW;
        startDate = new Date();
        documents.add(doc);
        tasks.add(new Task(this, documents, TaskTypes.EXECUTION, "drafting", "author"));
        tasks.add(new Task(this, documents, TaskTypes.AGREEMENT, "agreement", "director"));
        tasks.add(new Task(this, documents, TaskTypes.EXECUTION, "register", "clerk"));
        tasks.add(new Task(this, documents, TaskTypes.EXECUTION, "publishing", "clerk"));
        tasks.add(new Task(this, documents, TaskTypes.EXECUTION, "send in archive", "clerk"));
    }

    @Override
    public void stop() {
        state = TaskChainStates.STOPPED;
    }

    @Override
    public void nextStep() {
        if(state == TaskChainStates.STARTED){
            if(currentTask==null){
                currentTask = tasks.get(0);
            }else {
                int curInd = tasks.indexOf(currentTask);
                if (currentTask.getDescribe().equals("agreement") && currentTask.getState() == TaskStates.REJECT) {
                    tasks.add(curInd + 1, new Task(this, documents, TaskTypes.AGREEMENT, "agreement", "director"));
                    tasks.add(curInd + 1, new Task(this, documents, TaskTypes.EXECUTION, "fix remarks", "author"));
                    currentTask = tasks.get(curInd + 1);
                } else if (curInd < tasks.size()-1) {
                    currentTask = tasks.get(curInd + 1);
                } else {
                    stop();
                    currentTask = null;
                    System.out.println("PROCESS: process completed");
                }
            }
            if(currentTask!=null) {
                System.out.println("PROCESS: current task '" + currentTask.getDescribe() + "' for '" + currentTask.getExecutorPosition() + "'");
            }
        }
    }

    @Override
    public void start() {
        state = TaskChainStates.STARTED;
        System.out.println("--> PROCESS Issue order: process started.");
        nextStep();
    }

    @Override
    public Task getCurrentTask() {
        return currentTask;
    }

    @Override
    public TaskChainStates getState() {
        return state;
    }

    public List<Document> getDocuments() {
        return Collections.unmodifiableList(documents);
    }

    public void addDocument(Document document) {
        this.documents.add(document) ;
    }

    public void delDocument(Document document) {
        int docIdx = this.documents.indexOf(document) ;
        if(docIdx>0){
            documents.remove(docIdx);
        }
    }

}
