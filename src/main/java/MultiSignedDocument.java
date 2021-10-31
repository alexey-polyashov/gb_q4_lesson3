import java.util.ArrayList;
import java.util.List;

//Decorator
public abstract class MultiSignedDocument extends Document{

    private List<String> signers = new ArrayList<>();

    MultiSignedDocument(){
        super();
    }

    MultiSignedDocument(Document doc){
        if(doc.isSigned()){
            this.signed(true);
            this.signers.add(doc.getSigner());
        }
    }

    @Override
    public String getSigner() {
        return signers.
                stream().
                reduce((s1,s2)->s1 + "\n" + s2).orElse("");
    }

    @Override
    public void setSigner(String str) {
        this.signed(true);
        signers.add(str);
    }

    @Override
    public Document signed(boolean val){
        return super.signed(val);
    }

    public MultiSignedDocument signer(String str){
        setSigner(str);
        return this;
    }
    @Override
    public void sign() {
        this.signed(true);
    }


    public void clearSigners(){
        signed(false);
        signers.clear();
    }

}
