
import java.util.LinkedList;
import java.util.List;

public class DocumentProcessor {

    private static Letter letter = new Letter();
    private static Article article = new Article();
    private static Order order = new Order();
    private static CompositeOrder compositeOrder = new CompositeOrder();
    private static Appendix appendix = new Appendix();
    private static ApprovalSheet approvalSheet = new ApprovalSheet();

    public static void main(String[] args) {


        List<Document> documents = new LinkedList<>();

        documents.add(letter.createDocument()
                .body("Hi! It is my letter.")
                .footer("Best Regards")
                .address("to grandpa's village")
                .sender("Unknown"));

        documents.add(article.createDocument()
                .header("About kitty")
                .body("Very cute.")
                .footer("Author "));

        documents.add(order.createDocument()
                .header("Hiring order")
                .body("Hire new worker")
                .footer("HR Director")
                .signed(true));

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

    }



}
