Katalon RESTful API Automation

Proyek otomatisasi pengujian API menggunakan **Katalon Studio** dengan pendekatan *Data-Driven Testing* (DDT), *Custom Keywords*, dan *Test Listeners* untuk manajemen autentikasi dinamis.

## 🛠️ Tech Stack & Tools
* **Katalon Studio** (Automation Testing Engine)
* **Groovy** (Scripting Language)
* **RESTful API** (Endpoints: Register, Login, Create Object/Product)
* **Git & GitHub** (Version Control)

## 📁 Project Structure
* `Keywords/com/api/helpers/`: Berisi helper untuk autentikasi dinamis (`AuthHelper`) dan manajemen request API (`ApiKeyword`).
* `Test Listeners/`: Berisi `AuthListener` untuk otomatisasi *setup* token sebelum test case berjalan.
* `Test Cases/`: Berisi skrip utama pengujian (`TC_API_Automation_Flow`).
* `Data Files/`: Sumber data pengujian eksternal (*Data-Driven Testing*).
* `Profiles/`: Berisi konfigurasi GlobalVariable (`env_QA` dan `default`).

## 🚀 How to Run
1. Clone repository ini ke komputer lokal Anda menggunakan Katalon Studio.
2. Pastikan *Global Variables* pada profil Anda sudah terkonfigurasi dengan benar (`apiKey`).
3. Buka **Test Cases > TC_API_Automation_Flow**.
4. Klik tombol **Run** untuk memulai eksekusi otomatis.
