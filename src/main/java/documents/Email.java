package documents;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class Email extends Document {

    private List<Base64> attachments = new ArrayList<>();

    public List<Base64> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    public void addAttachments(Base64 attachments) {
        this.attachments.add(attachments);
    }

    public Email createDocument() {
        Email doc = new Email();
        doc.type = DocumentTypes.EMAIL;
        doc.haveBody = true;
        doc.haveFooter = true;
        doc.haveHeader = false;
        doc.haveSigne = false;
        doc.haveAddress = true;
        return doc;
    }
}
