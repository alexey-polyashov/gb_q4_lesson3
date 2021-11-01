package mappers;

import common.MapperRegistry;
import common.ResourceNotFound;
import identitymap.IdentityMap;
import users.User;

import java.sql.*;

public class UserMapper {
    private final Connection connection;

    private final MapperRegistry mapperRegistry;
    private final IdentityMap identityMap;

    public UserMapper(Connection connection) {
        this.connection = connection;
        mapperRegistry = MapperRegistry.getInstance();
        identityMap = mapperRegistry.getIdentityMap();
    }

    public User findByName(String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id, name, position FROM users WHERE name = ?");
        statement.setString(1,name);
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)

                );
                return user;
            }
        }
        throw new ResourceNotFound(String.format("User with name '%s' not found", name));
    }

    public User findById(Long id) throws SQLException {
        User fUser = identityMap.getUser(id);
        if(fUser!=null){
            return fUser;
        }
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id, name, position FROM users WHERE id = ?");
        statement.setLong(1,id);
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                return user;
            }
        }
        throw new ResourceNotFound(String.format("User with id '%i' not found", id));
    }

    public void update(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET name=?, position=? WHERE id = ?");
        statement.setString(1,user.getName());
        statement.setString(2, user.getPosition());
        statement.setLong(3,user.getId());
        statement.executeUpdate();
    }

    public void insert(User user) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (name, position) " +
                        "VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,user.getName());
        statement.setString(2, user.getPosition());
        int affectedRows = statement.executeUpdate();
        if(affectedRows==1){
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }
        }

        identityMap.setUser(user);

    }

    public void delete(User user) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?");
        statement.setLong(1, user.getId());
        statement.executeUpdate();

        identityMap.removeUser(user.getId());

    }

}
