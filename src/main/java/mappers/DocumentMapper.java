package mappers;

import common.MapperRegistry;
import common.ResourceNotFound;
import documents.Document;
import documents.DocumentStates;
import documents.DocumentTypes;
import documents.MultiSignedDocument;
import identitymap.IdentityMap;
import users.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumentMapper {

    private final Connection connection;

    private final MapperRegistry mapperRegistry;
    private final IdentityMap identityMap;

    public DocumentMapper(Connection connection) {
        this.connection = connection;
        mapperRegistry = MapperRegistry.getInstance();
        identityMap = mapperRegistry.getIdentityMap();
    }

    public List<Optional<Document>> findByParentId(Long id, DocumentTypes docType){

        List<Optional<Document>> result = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, type, state, header, body, footer, address, sender, signed, signer, signers.signer FROM documents " +
                            "LEFT JOIN signers ON documents.id = signers.document_id " +
                            "WHERE parent_id = ? AND type = ?");
            statement.setLong(1, id);
            statement.setString(2, docType.name());

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Document docFabric = (Document) docType.getDocumentClass().getDeclaredConstructor().newInstance();
                    Document newDoc = docFabric.createDocument()
                            .state(DocumentStates.valueOf(resultSet.getString(3)))
                            .header(resultSet.getString(4))
                            .body(resultSet.getString(5))
                            .footer(resultSet.getString(6))
                            .address(resultSet.getString(7))
                            .sender(resultSet.getString(8))
                            .signed(resultSet.getBoolean(9))
                            .signer(resultSet.getString(10));

                    newDoc.setId(resultSet.getLong(1));

                    if (newDoc instanceof MultiSignedDocument) {
                        while (resultSet.next()) {
                            newDoc.setSigner(resultSet.getString(11));
                        }
                    }

                    newDoc.optionalLoad();

                    result.add(Optional.of(newDoc));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

    public Document findById(Long id) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Document fDocument = identityMap.getDocument(id);
        if(fDocument!=null){
            return fDocument;
        }

        PreparedStatement statement = connection.prepareStatement(
                "SELECT id, type, state, header, body, footer, address, sender, signed, signer, signers.signer FROM documents " +
                        "LEFT JOIN signers ON documents.id = signers.document_id " +
                        "WHERE id = ?");
        statement.setLong(1, id);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {

                String stringDocType = resultSet.getString(2);
                DocumentTypes docType = DocumentTypes.valueOf(stringDocType);
                Document docFabric = (Document)docType.getDocumentClass().getDeclaredConstructor().newInstance();
                Document newDoc = docFabric.createDocument()
                        .state(DocumentStates.valueOf(resultSet.getString(3)))
                        .header(resultSet.getString(4))
                        .body(resultSet.getString(5))
                        .footer(resultSet.getString(6))
                        .address(resultSet.getString(7))
                        .sender(resultSet.getString(8))
                        .signed(resultSet.getBoolean(9))
                        .signer(resultSet.getString(10));

                newDoc.setId(resultSet.getLong(1));

                if(newDoc instanceof MultiSignedDocument){
                    while (resultSet.next()) {
                        newDoc.setSigner(resultSet.getString(11));
                    }
                }

                newDoc.optionalLoad();

                return newDoc;
            }
        }
        throw new ResourceNotFound(String.format("Document with id '%i' not found", id));
    }

    public void update(Document document) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "UPDATE documents SET type=?, state=?, header=?, body=?, footer=?, address=?, sender=?, signed=?, signer=? WHERE id = ?");
        statement.setString(1, document.getType().name());
        statement.setString(2, document.getState().name());
        statement.setString(3, document.getHeader());
        statement.setString(4, document.getBody());
        statement.setString(5, document.getFooter());
        statement.setString(6, document.getAddress());
        statement.setString(7, document.getSender());
        statement.setBoolean(8, document.isSigned());
        statement.setString(9, document.getSigner());
        statement.executeUpdate();

        if(document instanceof MultiSignedDocument){

            List<String> signers = ((MultiSignedDocument)document).getSigners();
            saveSigners(document.getId(), signers);

        }

        document.optionalSave();

    }

    public void insert(Document document) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO documents (type, state, header, body, footer, address, sender, signed, signer) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, document.getType().name());
        statement.setString(2, document.getState().name());
        statement.setString(3, document.getHeader());
        statement.setString(4, document.getBody());
        statement.setString(5, document.getFooter());
        statement.setString(6, document.getAddress());
        statement.setString(7, document.getSender());
        statement.setBoolean(8, document.isSigned());
        statement.setString(9, document.getSigner());

        int affectedRows = statement.executeUpdate();
        if(affectedRows==1){
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                document.setId(generatedKeys.getLong(1));
            }
        }
        identityMap.setDocument(document);

        if(document instanceof MultiSignedDocument){

            List<String> signers = ((MultiSignedDocument)document).getSigners();
            saveSigners(document.getId(), signers);

        }

        document.optionalSave();

    }

    public void delete(Document document) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?");
        statement.setLong(1, document.getId());
        statement.executeUpdate();

        document.optionalDelete();

    }

    private void saveSigners(Long documentId, List<String> signers) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM signers WHERE document_id = ?");
        statement.setLong(1, documentId);
        statement.executeUpdate();

        statement = connection.prepareStatement(
                "INSERT INTO signers (signer) " +
                        "VALUES (?) " +
                        "WHERE document_id = ?");

        for (String signer: signers) {
            statement.setString(1, signer);
            statement.setLong(2, documentId);
            statement.executeUpdate();
        }

    }

    public void deleteAllSlaves(Long parentId) {

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM documents WHERE parent_id = ?");
            statement.setLong(1, parentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertSlave(Long parentId, Document document) {

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO documents (type, state, header, body, footer, address, sender, signed, signer, parent_id) " +
                            "VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, document.getType().name());
            statement.setString(2, document.getState().name());
            statement.setString(3, document.getHeader());
            statement.setString(4, document.getBody());
            statement.setString(5, document.getFooter());
            statement.setString(6, document.getAddress());
            statement.setString(7, document.getSender());
            statement.setBoolean(8, document.isSigned());
            statement.setString(9, document.getSigner());
            statement.setLong(10, parentId);

            int affectedRows = statement.executeUpdate();
            if(affectedRows==1){
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    document.setId(generatedKeys.getLong(1));
                }
            }
            identityMap.setDocument(document);

            if(document instanceof MultiSignedDocument){

                List<String> signers = ((MultiSignedDocument)document).getSigners();
                saveSigners(document.getId(), signers);

            }

            document.optionalSave();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
