package documents;

public class Article extends Document {

    @Override
    public void send() {
        System.out.println("Can't send");
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
