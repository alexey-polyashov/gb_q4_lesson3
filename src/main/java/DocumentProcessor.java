
import common.MapperRegistry;
import users.User;
import documents.*;
import process.*;

import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.util.LinkedList;
import java.util.List;

public class DocumentProcessor {

    private static Letter letter = new Letter();
    private static Article article = new Article();
    private static Order order = new Order();
    private static CompositeOrder compositeOrder = new CompositeOrder();
    private static Appendix appendix = new Appendix();
    private static ApprovalSheet approvalSheet = new ApprovalSheet();

    private static Connection initConnection(){
        //connection init
        return null;
    };

    public static void main(String[] args) {

        MapperRegistry mapperRegistry = MapperRegistry.getInstance();
        MapperRegistry.setConnection(initConnection());

        User author = new User("Aleksey", "Journalist");
        User director = new User("Ivan", "Director");

        List<Document> documents = new LinkedList<>();

        documents.add(letter.createDocument()
                .body("Hi! It is my letter.")
                .footer("Best Regards")
                .address("to grandpa's village")
                .sender("Unknown"));

        Document article1 = article.createDocument()
                .header("About kitty")
                .body("Very cute.")
                .footer("Author ");

        documents.add(article1);

        Document order1 = order.createDocument()
                .header("Hiring order")
                .body("Hire new worker")
                .footer("HR Director")
                .signed(true);
        documents.add(order1);

        documents.add(compositeOrder.createDocument()
                .order((Order)order.createDocument()
                        .header("Dismiss order")
                        .body("Dismiss old worker. List in appendix 1")
                        .footer("HR Director")
                        .signer("Petrova Irina"))
                .appendix((Appendix)appendix.createDocument()
                        .body("Pupkin Vasiliy \n"+
                                "Foo Bar")
                        .signer("Petrova Irina"))
                .approvalSheet((ApprovalSheet)approvalSheet.createDocument()
                        .body("Reviewing")
                        .signer("Foo Bar")
                        .signer("Pupkin Vasiliy"))
        );

        for (Document doc: documents) {
            doc.show();
            System.out.println();
        }

        PublishArticle process1 = new PublishArticle(article1, author);
        process1.start();
        while(process1.getState()!= TaskChainStates.STOPPED){
            Task task = process1.getCurrentTask();
            if(task.getType()== TaskTypes.AGREEMENT){
                task.setState(TaskStates.AGREED, "ok!", director);
            }else{
                task.setState(TaskStates.DONE, "ok!", author);
            }
        }
        System.out.println();

        IssueOrder process2 = new IssueOrder(order1, author);
        process2.start();
        while(process2.getState()!=TaskChainStates.STOPPED){
            Task task = process2.getCurrentTask();
            if(task.getType()==TaskTypes.AGREEMENT){
                task.setState(TaskStates.AGREED, "ok!", director);
            }else{
                task.setState(TaskStates.DONE, "ok!", author);
            }
        }

    }



}
