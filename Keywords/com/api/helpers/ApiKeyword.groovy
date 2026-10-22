package com.api.helpers

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable

public class ApiKeyword {
    
    /**
     * Untuk mengirim request POST Create Product dengan menyertakan API Key dan Auth Token
     */
    @Keyword
    public static def sendCreateProductRequest(String collectionName, String itemData) {
        // Menggunakan path parameter secara dinamis
        RequestObject request = findTestObject('Object Repository/Endpoint/Collections/Post_CreateObject', [('collectionName') : collectionName])
        
        // Menetapkan header menggunakan ConditionType yang aman
        request.setHttpHeaderProperties([
            new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/json'),
            new TestObjectProperty('x-api-key', ConditionType.EQUALS, GlobalVariable.apiKey),
            new TestObjectProperty('Authorization', ConditionType.EQUALS, 'Bearer ' + GlobalVariable.authToken)
        ])
        
        request.setBodyContent(new HttpTextBodyContent(itemData))
        
        def response = WS.sendRequest(request)
        return response
    }
}