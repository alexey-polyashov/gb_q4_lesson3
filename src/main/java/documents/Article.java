package documents;

public class Article extends Document {

    @Override
    public void show() {
        System.out.println("<-----show " + type);
        System.out.println(getHeader());
        System.out.println(getBody());
        System.out.println(getFooter());
    }

    @Override
    public void plot() {
        System.out.println("<-----plot");
        System.out.println("plot article");
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
        return "";
    }

    public Article createDocument() {
        Article doc = new Article();
        doc.type = DocumentTypes.ARTICLE;
        doc.haveBody = true;
        doc.haveFooter = true;
        doc.haveHeader = true;
        doc.haveSigne = false;
        doc.haveAddress = false;
        return doc;
    }

}
