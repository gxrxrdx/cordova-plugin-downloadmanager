package downloadmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * This class echoes a string called from JavaScript.
 */
public class DownloadManager extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("download")) {
            String descargaURL = args.getString(0);
            String nombreArchivo = args.getString(1);
            this.startDownload(downloadURL, nombreArchivo, callbackContext);
            return true;
        }
        return false;
    }

    private void startDownload(final String message,final String nombreArchivo,final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                if (message != null && message.length() > 0) {                    
                    android.app.DownloadManager downloadManager = (android.app.DownloadManager) cordova.getActivity().getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);            
                    Uri Download_Uri = Uri.parse("https://segura.mx/segura.jpg"/*message*/);
                    Log.i("CORDOVA","---------------------------------");
                    Log.i("CORDOVA","URI DESCARGA: "+Download_Uri.toString());
                    Log.i("CORDOVA","---------------------------------");
                    android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(Download_Uri);
                    //Restrict the types of networks over which this download may proceed.
                    request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI | android.app.DownloadManager.Request.NETWORK_MOBILE);
                    //Set whether this download may proceed over a roaming connection.
                    request.setAllowedOverRoaming(false);
                    //Set the title of this download, to be displayed in notifications (if enabled).
                    request.setTitle(nombreArchivo);
                    //Set a description of this download, to be displayed in notifications (if enabled)
                    request.setDescription("DataSync File Download.");
                    //Set the local destination for the downloaded file to a path within the application's external files directory            
                    request.setDestinationInExternalPublicDir(cordova.getActivity().getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, nombreArchivo);
                    //Set visiblity after download is complete
                    request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    long downloadReference = downloadManager.enqueue(request);
                    callbackContext.success(message);
                } else {
                    callbackContext.error("Expected one non-empty string argument.");
                }
            }
        });        
    }
}