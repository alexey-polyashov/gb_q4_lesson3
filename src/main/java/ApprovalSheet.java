import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ApprovalSheet extends MultiSignedDocument{

    ApprovalSheet(Document doc) {
        super(doc);
    }

    ApprovalSheet() {
        super();
    }

    @Override
    public void show() {
        System.out.println("<-----show " + type);
        System.out.println(getBody());
        if(isSigned()){
            System.out.println("--- SIGNED! ---");
            System.out.println(getSigner());
        }
    }

    @Override
    public void plot() {
        System.out.println("<-----plot");
        System.out.println("plot approval sheet");
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
