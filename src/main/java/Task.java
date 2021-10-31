import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Task {

    List<Document> documents = new ArrayList<>();
    TaskChain process;

    String comment;
    TaskStates state;
    String describe;
    Date createDate;
    Date dateOfCompletion;
    User executor;
    String executorPosition;
    TaskTypes type;

    public Task(TaskChain process, List<Document> documents, TaskTypes type, String describe, String executorPosition) {
        this.process= process;
        this.documents= documents;
        this.describe = describe;
        this.type = type;
        this.executorPosition = executorPosition;
        this.createDate = new Date();
    }

    public void setState(TaskStates state, String comment, User executor) {
        this.state = state;
        setComment(comment);
        this.executor = executor;
        this.dateOfCompletion = new Date();
        if(state!=TaskStates.NEW){
            System.out.println("TASK: task '" + this.describe + "' is done with status '" + state +"'");
            process.nextStep();
        }
    }

    public TaskTypes getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDateOfCompletion() {
        return dateOfCompletion;
    }

    public User getExecutor() {
        return executor;
    }

    public String getExecutorPosition() {
        return executorPosition;
    }

    public void setExecutorPosition(String executorPosition) {
        this.executorPosition = executorPosition;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TaskStates getState() {
        return state;
    }


}
