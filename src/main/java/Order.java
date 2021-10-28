public class Order extends Document{

    @Override
    public void show() {
        System.out.println("<-----show " + type);
        System.out.println(getHeader());
        System.out.println(getBody());
        System.out.println(getFooter());
        if(isSigned()){
            System.out.println("--- SIGNED! ---");
        }
    }

    @Override
    public void plot() {
        System.out.println("<-----plot");
        System.out.println("plot order");
    }

    @Override
    public void send() {
        System.out.println("<-----send");
        System.out.println("send order");
    }

    @Override
    public void sign() {
        this.signed(true);
    }

    public Document createDocument() {
        Document doc = new Order();
        doc.type = DocumentTypes.ORDER;
        doc.haveBody = true;
        doc.haveFooter = true;
        doc.haveHeader = true;
        doc.haveSigne = true;
        doc.haveAddress = false;
        return doc;
    }
}
