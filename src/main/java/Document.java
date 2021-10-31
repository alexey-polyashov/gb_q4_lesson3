import java.io.Serializable;

public abstract class Document implements Serializable{

    protected DocumentTypes type;
    protected DocumentStates state;

    protected boolean haveHeader;
    protected boolean haveBody;
    protected boolean haveFooter;
    protected boolean haveSigne;
    protected boolean haveAddress;

    protected String header;
    protected String body;
    protected String footer;
    protected String address;
    protected String sender;
    protected boolean signed;

    protected String signer;

    public abstract void show();
    public abstract void plot();
    public abstract void send();
    public abstract void sign();

    public Document(){
    }

    public DocumentTypes getType() {
        return type;
    }

    public String getHeader() {
        if(haveHeader){
            return header;
        }else{
            return "";
        }
    }

    public String getBody() {
        if(haveBody){
            return body;
        }else{
            return "";
        }
    }

    public String getFooter() {
        if(haveFooter){
            return footer;
        }else{
            return "";
        }
    }

    public String getAddress() {
        if(haveAddress){
            return address;
        }else{
            return "";
        }
    }

    public String getSender() {
        if(haveAddress){
            return sender;
        }else{
            return "";
        }
    }

    public boolean isSigned() {
        if(haveSigne){
            return signed;
        }else{
            return false;
        }
    }

    public String getSigner(){
        if(signed) {
            return signer;
        }else{
            return "";
        }
    }
    public void setSigner(String str){
        signer(str);
    }

    //Fabric method
    public abstract Document createDocument();

    //Builder
    public Document header(String str){
        this.header = str;
        return this;
    }
    public Document body(String str){
        this.body = str;
        return this;
    }
    public Document footer(String str){
        this.footer = str;
        return this;
    }
    public Document address(String str){
        this.address = str;
        return this;
    }
    public Document sender(String str){
        this.sender = str;
        return this;
    }
    public Document signed(boolean val){
        this.signed = val;
        if(val==false){
            this.signer = "";
        }
        return this;
    }
    public Document signer(String str){
        this.signed = true;
        this.signer = str;
        return this;
    }
}
