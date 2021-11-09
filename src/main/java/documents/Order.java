package documents;

public class Order extends Document {

    @Override
    public void send() {
        System.out.println("Can't send");
    }

    public Order createDocument() {
        Order doc = new Order();
        doc.type = DocumentTypes.ORDER;
        doc.haveBody = true;
        doc.haveFooter = true;
        doc.haveHeader = true;
        doc.haveSigne = true;
        doc.haveAddress = false;
        return doc;
    }
}
