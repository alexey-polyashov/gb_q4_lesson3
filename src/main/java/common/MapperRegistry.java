package common;

import identitymap.IdentityMap;
import mappers.DocumentMapper;
import mappers.UserMapper;

import java.sql.Connection;

public class MapperRegistry {

    private static Connection connection;
    private static MapperRegistry instance;

    protected DocumentMapper documentMapper;
    protected UserMapper userMapper;
    protected IdentityMap identityMap;

    public static synchronized MapperRegistry getInstance() {
        if (instance == null) {
            instance = new MapperRegistry();
        }
        return instance;
    }

    public static void setConnection(Connection connection) {
        MapperRegistry.connection = connection;
    }

    public synchronized UserMapper getUserMapper() {
        if (userMapper == null) {
            userMapper = new UserMapper(connection);
        }
        return userMapper;
    }
    public IdentityMap getIdentityMap() {
        if (identityMap == null) {
            identityMap = new IdentityMap();
        }
        return identityMap;
    }
    public DocumentMapper getDocumentMapper() {
        if (documentMapper == null) {
            documentMapper = new DocumentMapper(connection);
        }
        return documentMapper;
    }

}

