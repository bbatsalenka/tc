package com.bbatsalenka.tc.util;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Base64Utils;

import com.bbatsalenka.tc.dtos.UserCredentialsDTO;

public class Utils {

	public static void mergeTwoObjects(Object source, Object target) {
		if (!source.getClass().getName().equals(target.getClass().getName())) {
			throw new RuntimeException("The objects belong to two different classes and cannot be merged!");
		}
		Field[] declaredFields = source.getClass().getDeclaredFields();
		List<String> annotatedFieldsNames = new ArrayList<>();
		for (int i = 0 ; i < declaredFields.length; i++) {
			if (declaredFields[i].isAnnotationPresent(ExcludeFromMerge.class)) {
				annotatedFieldsNames.add(declaredFields[i].getName());
			}
		}
		BeanUtils.copyProperties(source, target, annotatedFieldsNames.toArray(
				new String[annotatedFieldsNames.size()]));
	}
	
	public static String getToken(String headerValue) {
		final String token = headerValue.split("\\s+")[1];
		return token;
	}
	
	public static UserCredentialsDTO decodeBase64Credentials(String credentials) {
		String stringToDecode = credentials.split("\\s+")[1];
		byte[] decodedCredentialsBytes = Base64Utils.decodeFromString(stringToDecode);
		String decodedString = new String(decodedCredentialsBytes, StandardCharsets.UTF_8);
		String[] credentialsArray = decodedString.split(":");
		UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
		userCredentialsDTO.setUsername(credentialsArray[0]);
		userCredentialsDTO.setPassword(credentialsArray[1]);
		return userCredentialsDTO;
	}
}
