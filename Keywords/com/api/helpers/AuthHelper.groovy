package com.api.helpers

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable
import groovy.json.JsonSlurper
import java.util.UUID

public class AuthHelper {
    
    // Function untuk membuat string acak alfanumerik menggunakan UUID dan regex pembersih karakter
    private static String generateRandomAlphanumeric(int length) {
        String uuidStr = UUID.randomUUID().toString().replaceAll("[^a-zA-Z0-9]", "")
        return uuidStr.length() >= length ? uuidStr.substring(0, length) : uuidStr
    }

    // Function untuk menghasilkan nama random berupa string acak (contoh: an118enfq)
    public static String generateRandomName() {
        return generateRandomAlphanumeric(9)
    }
    
    // Function untuk menghasilkan email dari string nama yang sama
    public static String generateRandomEmail(String name) {
        return name + "@example.com"
    }
    
    // Function untuk menghasilkan password dinamis dengan kombinasi alfanumerik dan simbol
    public static String generateRandomPassword() {
        String base = generateRandomAlphanumeric(6)
        return base + "NYS(%21"
    }
    
    // Proses Register dan Login berantai untuk mendapatkan token
    public static void performRegisterAndLogin() {
        String name = generateRandomName()
        String email = generateRandomEmail(name)
        String password = generateRandomPassword()
        
        println("[INFO] Generated Credentials -> Name: ${name}, Email: ${email}, Password: ${password}")
        
        // 1. Hit Register
        def regRequest = findTestObject('Object Repository/Endpoint/Register/Post_Register')
        regRequest.setHttpHeaderProperties([
            new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/json'),
            new TestObjectProperty('x-api-key', ConditionType.EQUALS, GlobalVariable.apiKey)
        ])
        String regPayload = "{\"email\": \"${email}\", \"password\": \"${password}\", \"name\": \"${name}\"}"
        regRequest.setBodyContent(new HttpTextBodyContent(regPayload))
        
        def regResponse = WS.sendRequest(regRequest)
        println("[INFO] Register Status: " + regResponse.getStatusCode())
        
        // 2. Hit Login untuk mendapatkan Token
        def loginRequest = findTestObject('Object Repository/Endpoint/Login/Post_Login')
        loginRequest.setHttpHeaderProperties([
            new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/json'),
            new TestObjectProperty('x-api-key', ConditionType.EQUALS, GlobalVariable.apiKey)
        ])
        String loginPayload = "{\"email\": \"${email}\", \"password\": \"${password}\"}"
        loginRequest.setBodyContent(new HttpTextBodyContent(loginPayload))
        
        def loginResponse = WS.sendRequest(loginRequest)
        println("[INFO] Login Status: " + loginResponse.getStatusCode())
        
        if (loginResponse.getStatusCode() == 200 || loginResponse.getStatusCode() == 201) {
            JsonSlurper slurper = new JsonSlurper()
            def parsedJson = slurper.parseText(loginResponse.getResponseBodyContent())
            if (parsedJson.containsKey("token")) {
                GlobalVariable.authToken = parsedJson.token
                println("[INFO] Auth Token berhasil disimpan ke GlobalVariable.authToken")
            }
        }
    }
}