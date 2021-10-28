
import java.util.LinkedList;
import java.util.List;

public class DocumentProcessor {

    private static Letter letter = new Letter();
    private static Article article = new Article();
    private static Order order = new Order();

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
                .header("Hiring")
                .body("Hir new worker")
                .footer("HR Director")
                .signed(true));

        for (Document doc: documents) {
            doc.show();
        }

    }



}
