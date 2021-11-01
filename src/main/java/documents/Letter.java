package documents;

public class Letter extends Document {

    @Override
    public void show() {
        System.out.println("<-----show " + type);
        System.out.println(getBody());
        System.out.println(getFooter());
        System.out.println("ADDRESS: " + getAddress());
        System.out.println("SENDER: " + getSender());
    }

    @Override
    public void plot() {
        System.out.println("<-----plot");
        System.out.println("plot letter");
    }

    @Override
    public void send() {
        System.out.println("<-----send");
        System.out.println("send letter");
    }

    @Override
    public void sign() {
        this.signed(true);
    }

    @Override
    public String getSigner() {
        return "";
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
