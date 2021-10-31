import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class Email extends Document{

    private List<Base64> attachments = new ArrayList<>();

    public List<Base64> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    public void addAttachments(Base64 attachments) {
        this.attachments.add(attachments);
    }

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
        System.out.println("plot email");
    }

    @Override
    public void send() {
        System.out.println("<-----send");
        System.out.println("send email");
    }

    @Override
    public void sign() {
        this.signed(true);
    }

    @Override
    public String getSigner() {
        return "";
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
