package sk.stuba.fei.uim.vsa.pr2.services;

import java.util.Base64;

public class Authentification {

    private static String _email;
    private static Long _id;

    public static void startAuth(String email, Long id) {
        _email = email;
        _id = id;
    }

    private static String getAuthEmail(String auth){
        String base64Encoded = auth.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(base64Encoded));

        return decoded.split(":")[0];
    }

    private static Long getAuthId(String auth) {
        String base64Encoded = auth.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(base64Encoded));
        String id = decoded.split(":")[1];

        return Long.parseLong(id, 10);
    }

    public static boolean isAuthorised(String auth) {
        if(auth == null) {
            return false;
        }

        String userEmail = getAuthEmail(auth);
        Long userId = getAuthId(auth);

        if(_email.equals(userEmail) && _id.equals(userId)){
            return true;

        } else {
            return false;
        }
    }

}
