package identitymap;

import documents.Document;
import users.User;

import java.util.HashMap;
import java.util.Map;

public class IdentityMap {

    private Map<Long, Document> documentMap = new HashMap<>();
    private Map<Long, User> userMap = new HashMap<>();

    public User getUser(Long id){
        return userMap.get(id);
    }

    public Document getDocument(Long id){
        return documentMap.get(id);
    }

    public void setUser(User user){
        userMap.put(user.getId(), user);
    }

    public void setDocument(Document document){
        documentMap.put(document.getId(), document);
    }

    public void removeUser(Long id){
        if(userMap.containsKey(id)){
            userMap.remove(id);
        }
    }

    public void removeDocument(Long id){
        if(documentMap.containsKey(id)){
            documentMap.remove(id);
        }
    }

}
