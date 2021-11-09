package documents;

public class Letter extends Document {

    @Override
    public void send() {
        System.out.println("<-----send");
        System.out.println("send letter");
    }

    public Letter createDocument() {
        Letter doc = new Letter();
        doc.type = DocumentTypes.LETTER;
        doc.haveBody = true;
        doc.haveFooter = true;
        doc.haveHeader = false;
        doc.haveSigne = false;
        doc.haveAddress = true;
        return doc;
    }
}
