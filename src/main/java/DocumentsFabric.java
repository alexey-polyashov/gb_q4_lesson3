public class DocumentsFabric {

    public Document createDocument(DocumentTypes type){
        Document d;
        switch (type){
            case ORDER:
                d = new Order();
                break;
            case LETTER:
                d = new Letter();
                break;
            default: d = new Article();
        }
        return d;
    };
}
