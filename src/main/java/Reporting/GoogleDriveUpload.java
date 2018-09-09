package Reporting;
/**
 * @author Shubham Jain
 * */
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.drive.Drive;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Note : https://developers.google.com/drive/v3/web/quickstart/java
 * https://console.developers.google.com/
 * */

public class GoogleDriveUpload {
    /** Application name. */
	
	static String pathofgoogleid= System.getProperty("user.dir")+ File.separator +"src" + File.separator+ "Reporting" + File.separator+ "client_secret.json";
	static String ServiceAccountgoogleid= System.getProperty("user.dir")+ File.separator +"src" + File.separator+ "Reporting" + File.separator+ "ShubhamCucumberProject-e45241e32208.p12";
	//System.out.println(pathofgoogleid);
	static File jsonFile = new File(pathofgoogleid);
	
	static Date currentDate = new Date();
    static SimpleDateFormat dateFormatForFileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//dd/MM/yyyy
    static String fileDateFormet = dateFormatForFileName.format(currentDate);
 	static String testoutputfolderpath = System.getProperty("user.dir")+ File.separator +"target"+ File.separator+ "cucumber-html-reports";
	static String zipFilefolderpath = System.getProperty("user.dir")+ File.separator +"reports"+File.separator+"results"+fileDateFormet+".zip";  // Zip Reports Files
	static File folder = new File(testoutputfolderpath);
	static File zipFile = new File(zipFilefolderpath);
	
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";
    
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/drive-java-quickstart");
    
    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
/*    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE_METADATA_READONLY);*/
    
    private static final java.util.Collection<String> SCOPES =
    	    DriveScopes.all();

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.

        InputStream inputStream = new FileInputStream(jsonFile);
        try {
	        GoogleClientSecrets clientSecrets =
	                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));
	        
	        // Build flow and trigger user authorization request.
	        GoogleAuthorizationCodeFlow flow =
	                new GoogleAuthorizationCodeFlow.Builder(
	                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	                .setDataStoreFactory(DATA_STORE_FACTORY)
	                .setAccessType("offline")
	                .build();
	        Credential credential = new AuthorizationCodeInstalledApp(
	            flow, new LocalServerReceiver()).authorize("user");
	        System.out.println(
	                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
	        return credential;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
    	
    	final String localPath =   ZipReports.zipFolder(folder, zipFile);
    	
    	       Drive service = getDriveService();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
             .setPageSize(10)
             .setFields("nextPageToken, files(id, name)")
             .execute();
        List<com.google.api.services.drive.model.File> files = result.getFiles();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (com.google.api.services.drive.model.File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
        
        //Uploading Files on google
    	
    	
    	HttpTransport httpTransport = new NetHttpTransport();
		 JacksonFactory jsonFactory = new JacksonFactory();

		 GoogleCredential credential = null;

		Collection<String> SCOPES = new ArrayList<String>();
		SCOPES.add("https://www.googleapis.com/auth/admin.directory.user");
		SCOPES.add("https://www.googleapis.com/auth/drive");
		SCOPES.add("https://www.googleapis.com/auth/drive.appfolder");
		SCOPES.add("https://www.googleapis.com/auth/drive.file");
		SCOPES.add("https://www.googleapis.com/auth/drive.install");
		
			try {
				credential = new GoogleCredential.Builder().setTransport(httpTransport)
				         .setJsonFactory(jsonFactory)
				         .setServiceAccountId("shubham.jain@cuelogic.co.in")
				         .setServiceAccountUser("ishubhamjain@gmail.com")    //https://developers.google.com/identity/protocols/OAuth2ServiceAccount
				         .setServiceAccountScopes(SCOPES)
				         .setServiceAccountPrivateKeyFromP12File(            //https://www.youtube.com/watch?v=CXPRd8Hv2Y8
				         new java.io.File(ServiceAccountgoogleid))
				        .build();
				
				
				System.out.println(credential.getServiceAccountId());
				System.out.println(credential.getServiceAccountUser());
				Directory directory = new Directory.Builder(httpTransport, JSON_FACTORY, credential).build();

				Directory.Users.List list = directory.users().list();
				list.setDomain("cuelogic.com");
			//	list.execute();	
			//	Users users = 	list.execute();			    
			} catch (GeneralSecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        new Thread(new Runnable() {
			@Override
			public void run() {
		        try {
					com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
					fileMetadata.setName("Cucumber Report");
				//	fileMetadata.setMimeType("application/vnd.google-apps.spreadsheet");
					fileMetadata.setMimeType("application/vnd.google-apps.file");   //https://developers.google.com/drive/v3/web/mime-types

					java.io.File filePath = new java.io.File(localPath);
					FileContent mediaContent = new FileContent("application/zip", filePath);  // https://developers.google.com/drive/v3/web/integrate-open
					com.google.api.services.drive.model.File file = getDriveService().files().create(fileMetadata, mediaContent)
						    .setFields("id")
						    .execute();
						System.out.println("File ID: " + file.getId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();;
    }
}