package documents;

public class Appendix extends Document {

    @Override
    public void send() {
        System.out.println("Can't send");
    }

    public Appendix createDocument() {
        Appendix doc = new Appendix();
        doc.type = DocumentTypes.APPENDIX;
        doc.haveBody = true;
        doc.haveFooter = false;
        doc.haveHeader = false;
        doc.haveSigne = true;
        doc.haveAddress = false;
        return doc;
    }

}
