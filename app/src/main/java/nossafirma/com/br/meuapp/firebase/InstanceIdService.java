package nossafirma.com.br.meuapp.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by Rodrigo on 10/09/2017.
 */

public class InstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //sendRegistrationToServer(refreshedToken);
    }

//    private void sendRegistrationToServer(String token){
//        Log.d(TAG, "Token atualizado: " + token);
//    }
}
