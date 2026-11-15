**Arsitektur**

api-gateway :8085 
│                      │                        │
▼                      ▼                        ▼
auth-service :8081   product-service :8082 ◄─Feign─ scheduler-service :8083 ──Feign──► DummyJSON ("https://dummyjson.com/products/category/smartphones")
│                      │   
└────────┬─────────────┘
         ▼
PostgreSQL :5432

**Urutan Run**
1. EurekaServerApplication
2. AuthServiceApplication & ProductServiceApplication (terserah mana yang duluan)
3. ApiGatewayApplication
4. SchedulerServiceApplication -> tidak wajib, ini kalau perlu jalanin scheduler import aja baru di-run, kalau sudah tidak perlu import matikan aja.

**Urutan Postman**
1. Karena api-gateway di port 8085 semua api base-urlnya http://localhost:8085
2. Register user jika belum punya user-> http://localhost:8085/auth/api/v1/register
   {
   "username": "usernameKamu",
   "password": "PasswordKamu"
   }
3. Login user jika punya punya user -> http://localhost:8085/auth/api/v1/login
   {
   "username": "usernameKamu",
   "password": "PasswordKamu"
   }
  Ambil response tokennya copas ke Authorization->Bearer Token untuk semua api selain auth
4. (Contoh hit api create product) http://localhost:8085/product/api/v1/create
- Authorization -> type : Bearer Token, isi token dari response token api auth/api/v1/login
- Body : 

   {
   "titleProduct": "Test Phone Seri X",
   "brandProduct": "TestBrand",
   "skuProduct": "TEST-SKU-001-001",
   "descProduct": "Produk percobaan testing",
   "stock": 30,
   "price": 499.99,
   "rating": 4.5
   }


**Catatan Keputusan**
1. Port gateway pakai 8085 karena 8080 sudah dipakai dan tidak bisa ditaskkill
2. JWT Token expired dalam 300000 miliseconds alias 5 menit. Durasi 5–15 menit adalah standar industri yang paling disarankan untuk meminimalkan risiko jika token dicuri. 
3. Pada scheduler, Produk yang sudah pernah diimpor tidak tersimpan dua kali walaupun impor jalan berulang, karena ada penjagaan duplikat by SKU.
4. Feign timeout 3 detik connect ke tujuan, 10 detik read response. Standar rekomendasi connect-timeout = 1-3 detik, read-timeout crud = 5-10 detaik
5. Pada scheduler jika ambil dari DummyJSON nya gagal maka tidak panggil product-service sama sekali