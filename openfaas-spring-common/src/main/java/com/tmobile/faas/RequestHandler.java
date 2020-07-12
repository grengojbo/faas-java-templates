package com.tmobile.faas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.platform.commons.util.StringUtils;

public interface RequestHandler {

	String handle(byte[] requestPayload);

	String handle();

	public static String readSecret(String key) {
		String basePath = "/var/openfaas/secrets/";
		if (StringUtils.isNotBlank(System.getenv("secret_mount_path"))) {
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
		
		String userHomeDir = System.getProperty("user.home");
		String keyFilePath = userHomeDir + "/.ssh/id_rsa";
		File tempFile = new File(keyFilePath);
		if (tempFile.exists()) {
			return keyFilePath;
		} else {
			String basePath = "/var/openfaas/secrets/";
			if (StringUtils.isNotBlank(System.getenv("secret_mount_path"))) {
				basePath = System.getenv("secret_mount_path");
			}
			return basePath + "gitSshPrivateKey";
		}

	}
}
