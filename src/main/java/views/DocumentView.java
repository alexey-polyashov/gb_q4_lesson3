package views;

import common.DocumentException;
import controllers.DocumentController;
import documents.Document;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Scanner;

public class DocumentView<T extends Document> {

    public enum Commands{
        SIGN("Sign document", new String[]{"Signer"}),
        EDIT("Edit document", new String[0]),
        DEL("Delete document", new String[0]),
        SAVE("Save document", new String[0]),
        UPDATE("Update view", new String[0]);
        private String describe;
        private int paramsCount;
        private String[] params;

        Commands(String describe, String[] params) {
            this.describe = describe;
            this.params = params;
            this.paramsCount = params.length;
        }

        public String getDescribe() {
            return describe;
        }

        public String[] getParams() {
            return params;
        }

        public int getParamsCount() {
            return paramsCount;
        }
    }

    private T document;

    public DocumentView(T document) {
        this.document = document;
    }

    public void showDocument(){
        document.show();
    }
    public void showCommands(){
        System.out.println("Commands:");
        System.out.println("9 - Exit");
        for (Commands cmd: Commands.values()) {
            System.out.println(cmd.ordinal() + " - " + cmd.getDescribe());
        }
    }
    public void showView(){
        showDocument();
        showCommands();
    }
    public boolean invokeCommand(Commands cmd, String ... params) throws SQLException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        DocumentController<T> docController = new DocumentController<>(document.getClass());
        switch (cmd){
            case SIGN:
                docController.signDocument(document, params[0]);
                break;
            case EDIT:
                docController.editDocument(document);
                break;
            case DEL:
                docController.delDocument(document);
                break;
            case SAVE:
                docController.saveDocument(document);
                break;
            case UPDATE:
                document = docController.updateDocument(document);
                break;
            default:
                System.out.println("Command not correct");
                return false;
        }
        return true;
    }


}
