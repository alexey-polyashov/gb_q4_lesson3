package documents;

public class ApprovalSheet extends MultiSignedDocument {

    public ApprovalSheet(Document doc) {
        super(doc);
    }

    public ApprovalSheet() {
        super();
    }

    @Override
    public void send() {
        System.out.println("Can't send");
    }

    public ApprovalSheet createDocument() {
        ApprovalSheet doc = new ApprovalSheet();
        doc.type = DocumentTypes.APPROVAL_SHEET;
        doc.haveBody = true;
        doc.haveFooter = false;
        doc.haveHeader = false;
        doc.haveSigne = true;
        doc.haveAddress = false;
        return doc;
    }

}
