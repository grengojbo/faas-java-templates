package com.tmobile.faas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface RequestHandler {

	String handle(byte[] requestPayload);

	String handle();

	public static String readSecret(String key) {
		String basePath = "/var/openfaas/secrets/";
		if (!System.getenv("secret_mount_path").isEmpty()) {
			basePath = System.getenv("secret_mount_path");
		}

		String readPath = basePath + key;
		String content = "";
		 
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(readPath) ) );
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return "";
        }
 
        return content;

		

	}
	
	public static String getPrivateKeyPath() {
		String basePath = "/var/openfaas/secrets/";
		if (!System.getenv("secret_mount_path").isEmpty()) {
			basePath = System.getenv("secret_mount_path");
		}
		String userHomeDir = System.getProperty("user.home");
		File tempFile = new File(userHomeDir + ".ssh/id_rsa");
		if (tempFile.exists()) {
			return userHomeDir + ".ssh/id_rsa";
		} else {
			return basePath + "gitSshPrivateKey";
		}

	}
}
