package documents;

import java.io.Serializable;

public abstract class Document implements Serializable{

    protected DocumentTypes type;
    protected DocumentStates state;
    protected Long id;
    protected boolean edit;

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

    public void show(){
        System.out.println("<-----show " + type);
        if(this.haveHeader) {
            System.out.println(getHeader());
        }
        if(this.haveBody) {
            System.out.println(getBody());
        }
        if(this.haveFooter) {
            System.out.println(getFooter());
        }
        if(this.haveAddress) {
            System.out.println("ADDRESS: " + getAddress());
        }
        if(this.haveSigne) {
            if(isSigned()){
                System.out.println("--- SIGNED! ---");
            }
            System.out.println("    SENDER: " + getSender());
        }

    };
    public void plot(){
        System.out.println("<-----plot");
        System.out.println("plot " + type);
    }
    public void send(){
        System.out.println("<-----send");
        System.out.println("send " + type);
    }
    public void sign(){
        if(haveSigne) {
            this.signed(true);
        }
    }

    public Document(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public DocumentStates getState() {
        return state;
    }

    public boolean isSigned() {
        if(haveSigne){
            return signed;
        }else{
            return false;
        }
    }

    public void optionalLoad(){
    }

    public void optionalSave(){
    }

    public void optionalDelete(){
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
    public Document state(DocumentStates state){
        this.state = state;
        return this;
    }

    public Boolean isEdit() {
        return edit;
    }

    public void setOnEdit(Boolean edit) {
        this.edit = edit;
    }
}
