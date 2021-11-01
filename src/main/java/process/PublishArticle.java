package process;

import users.User;
import documents.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PublishArticle implements TaskChain {

    List<Document> documents = new ArrayList<>();
    List<Task> tasks = new ArrayList<>();
    Task currentTask;
    TaskChainStates state;
    Date startDate;
    User initiator;

    public PublishArticle(Document doc, User initiator) {
        super();
        state = TaskChainStates.NEW;
        startDate = new Date();
        documents.add(doc);
        tasks.add(new Task(this, documents, TaskTypes.EXECUTION, "drafting", "author"));
        tasks.add(new Task(this, documents, TaskTypes.AGREEMENT, "content verification", "content editor"));
        tasks.add(new Task(this, documents, TaskTypes.EXECUTION, "publishing", "designer"));
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
                if (currentTask.getDescribe().equals("content verification") && currentTask.getState() == TaskStates.REJECT) {
                    tasks.add(curInd + 1, new Task(this, documents, TaskTypes.AGREEMENT, "content verification", "content editor"));
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
            if (currentTask != null) {
                System.out.println("PROCESS: current task '" + currentTask.getDescribe() + "' for '" + currentTask.getExecutorPosition() + "'");
            }
        }
    }

    @Override
    public void start() {
        state = TaskChainStates.STARTED;
        System.out.println("--> PROCESS Publish article: process started.");
        nextStep();
    }

    @Override
    public Task getCurrentTask() {
        return currentTask;
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

    public TaskChainStates getState() {
        return state;
    }
}
