package common;

import java.sql.SQLDataException;

public class ResourceNotFound extends SQLDataException {
    public ResourceNotFound(String reason) {
        super(reason);
    }
}
