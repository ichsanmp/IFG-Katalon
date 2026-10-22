import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.api.helpers.ApiKeyword

// 1. Mengambil data khusus "Create Product" dari Test Data
def testData = findTestData("Data Files/Test Data")

for (def i = 1; i <= testData.getRowNumbers(); i++) {
    def testCaseId = testData.getValue("testCaseId", i)
    def collectionName = testData.getValue("collectionName", i)
    def itemData = testData.getValue("itemData", i)
    def expectedStatusCode = testData.getValue("expectedStatusCode", i) as Integer
    def testType = testData.getValue("testType", i)
    
    println("Menjalankan ${testCaseId} [${testType}] untuk Collection: ${collectionName}")
    
    // 2. Eksekusi Create Product menggunakan ApiKeyword (API Key & Token sudah otomatis terpasang)
    def response = ApiKeyword.sendCreateProductRequest(collectionName, itemData)
    
    // 3. Verifikasi status code
    WS.verifyResponseStatusCode(response, expectedStatusCode)
}