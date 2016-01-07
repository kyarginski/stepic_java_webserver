package accounts;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService {

//    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UsersDataSet> sessionIdToProfile;

    DBService dbService;

    public AccountService() {
//        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();

        dbService = new DBService();
        dbService.printConnectInfo();

    }

    public void addNewUser(String login, String pass, String email) throws DBException {
//        loginToProfile.put(userProfile.getLogin(), userProfile);
        long userId = dbService.addUser(login,pass,email);

    }

    public UsersDataSet getUserByLogin(String login) throws DBException {
        //return loginToProfile.get(login);
        return dbService.getUser(login);
    }

    public UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UsersDataSet usersDataSet) {
        sessionIdToProfile.put(sessionId, usersDataSet);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
