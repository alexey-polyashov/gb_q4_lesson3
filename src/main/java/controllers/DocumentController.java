package controllers;

import common.DocumentException;
import common.MapperRegistry;
import documents.Document;
import documents.MultiSignedDocument;
import mappers.DocumentMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class DocumentController<T extends Document> {

    private Class documentClass;
    private final MapperRegistry mapperRegistry;

    public DocumentController(Class docClass) {
        this.documentClass = docClass;
        this.mapperRegistry = MapperRegistry.getInstance();
    }

    public Document addNewDocument() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = documentClass.getConstructor();
        T doc = constructor.newInstance();
        return doc.createDocument();
    }

    public void delDocument(T document) throws SQLException {
        DocumentMapper documentMapper = mapperRegistry.getDocumentMapper();
        documentMapper.delete(document);
    }

    public void saveDocument(T document) throws SQLException {
        DocumentMapper documentMapper = mapperRegistry.getDocumentMapper();
        Long docId = document.getId();
        if(docId!=null && docId>0){
            documentMapper.insert(document);
        }else{
            documentMapper.update(document);
        }
        document.setOnEdit(false);
    }

    public void signDocument(T document, String signer) throws DocumentException{
        if(document instanceof MultiSignedDocument){
            List<String> signers = ((MultiSignedDocument) document).getSigners();
            if(signers.indexOf(signer)>-1){
                throw new DocumentException("The document already signed");
            }
        }else if(document.isSigned()){
            throw new DocumentException(String.format("The document is already signed '%s'", document.getSigner()));
        }
        document.setSigner(signer);
    }

    public void editDocument(T document) throws DocumentException{
        if(!document.isEdit()){
            throw new DocumentException("The document is already being edited");
        }
        document.setOnEdit(true);
    }

    public T updateDocument(T document) throws DocumentException, SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Long docId = document.getId();
        if(docId==null || docId==0) {
            throw new DocumentException("The document has not been saved yet");
        }
        DocumentMapper documentMapper = mapperRegistry.getDocumentMapper();
        return (T)documentMapper.findById(document.getId());
    }
}
