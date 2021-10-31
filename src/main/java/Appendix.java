public class Appendix extends Document{

    @Override
    public void show() {
        System.out.println("<-----show " + type);
        System.out.println(getBody());
        if(isSigned()){
            System.out.println("--- SIGNED! ---");
        }
    }

    @Override
    public void plot() {
        System.out.println("<-----plot");
        System.out.println("plot appendix");
    }

    @Override
    public void send() {
        System.out.println("Can't send");
    }

    @Override
    public void sign() {
        this.signed(true);
    }

    @Override
    public String getSigner() {
        return signer;
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
