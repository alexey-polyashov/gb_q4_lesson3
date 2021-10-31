import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Composition
public class CompositeOrder extends Document implements Serializable {

    Order order;
    List<Appendix> appendixList= new ArrayList<>();
    ApprovalSheet approvalSheet;

    public CompositeOrder() {
        super();

    }

    public CompositeOrder(Order order, List<Appendix> appendixList, ApprovalSheet approvalSheet) {
        this.order = order;
        this.appendixList = appendixList;
        this.approvalSheet = approvalSheet;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Appendix> getAppendixList() {
        return Collections.unmodifiableList(appendixList);
    }
    public void addAppendix(Appendix appendix) {
        appendix(appendix);
    }
    public void clearAppendixList(){
        this.appendixList.clear();
    }

    public ApprovalSheet getApprovalSheet() {
        return approvalSheet;
    }

    public void setApprovalSheet(ApprovalSheet approvalSheet) {
        this.approvalSheet = approvalSheet;
    }

    @Override
    public void show() {
        System.out.println("<-----show " + type);
        if(order!=null){
            System.out.println("<Order>");
            order.show();
        }
        if(appendixList!=null){
            int i = 1;
            for (Appendix apdx: appendixList) {
                System.out.println("<Appendix " + i++ + ">");
                apdx.show();
            }
        }
        if(order!=null){
            System.out.println("<Approval sheet>");
            approvalSheet.show();
        }
    }

    @Override
    public void plot() {
        System.out.println("<-----plot " + type);
        if(order!=null){
            System.out.println("<Order>");
            order.plot();
        }
        if(appendixList!=null){
            int i=1;
            for (Appendix apdx: appendixList) {
                System.out.println("<Appendix " + i++ + ">");
                apdx.plot();
            }
        }
        if(order!=null){
            System.out.println("<Approval sheet>");
            approvalSheet.plot();
        }
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

    public CompositeOrder createDocument() {
        CompositeOrder doc = new CompositeOrder();
        doc.type = DocumentTypes.COMPOSITE_ORDER;
        doc.haveBody = true;
        doc.haveFooter = true;
        doc.haveHeader = true;
        doc.haveSigne = true;
        doc.haveAddress = false;
        return doc;
    }

    public static ByteArrayOutputStream  zip(CompositeOrder co) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(co);
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    public static CompositeOrder unZip(ByteArrayOutputStream zipped) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(zipped.toByteArray()));
        CompositeOrder co = (CompositeOrder) objectInputStream.readObject();
        objectInputStream.close();
        return co;
    }

    //Builder
    CompositeOrder order(Order order){
        this.order = order;
        return this;
    }
    CompositeOrder appendix(Appendix appendix){
        this.appendixList.add(appendix);
        return this;
    }
    CompositeOrder approvalSheet(ApprovalSheet approvalSheet){
        this.approvalSheet = approvalSheet;
        return this;
    }
}
