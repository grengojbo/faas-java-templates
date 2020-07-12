package com.tmobile.faas;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.tmobile.faas.RequestHandler;

class RequestHanlderTest {
	
	protected static void setEnv(Map<String, String> newenv) throws Exception {
		  try {
		    Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
		    Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
		    theEnvironmentField.setAccessible(true);
		    Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
		    env.putAll(newenv);
		    Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
		    theCaseInsensitiveEnvironmentField.setAccessible(true);
		    Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
		    cienv.putAll(newenv);
		  } catch (NoSuchFieldException e) {
		    Class[] classes = Collections.class.getDeclaredClasses();
		    Map<String, String> env = System.getenv();
		    for(Class cl : classes) {
		      if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
		        Field field = cl.getDeclaredField("m");
		        field.setAccessible(true);
		        Object obj = field.get(env);
		        Map<String, String> map = (Map<String, String>) obj;
		        map.clear();
		        map.putAll(newenv);
		      }
		    }
		  }
		}
	
	@Test
	void test() {
		
//		System.out.println(Handler.getPrivateKeyPath());
//		try {
//			setEnv(Collections.singletonMap("secret_mount_path", System.getProperty("user.dir") + "/"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
