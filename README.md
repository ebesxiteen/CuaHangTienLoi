 # CuaHangTienLoi

Mục tiêu ngắn: Ứng dụng Java Swing quản lý cửa hàng nhỏ; đã được điều chỉnh để dễ chạy trên nhiều IDE và từ dòng lệnh.

Tóm tắt dự án
- Ngôn ngữ: Java (OpenJDK 21+)
- Giao diện: Swing (form tạo bằng NetBeans)
- Kiến trúc: phân lớp — `DTO` (model), `DAL` (truy cập dữ liệu), `BLL` (logic nghiệp vụ), `GUI` (giao diện)

Luồng chính
- Người dùng thao tác trên `GUI` → `BLL` xử lý nghiệp vụ và validate → `DAL` tương tác CSDL qua JDBC → `DTO` truyền dữ liệu.
- Điểm bắt đầu: lớp `GUI.Start`.

Yêu cầu môi trường
- Java 21 (JDK) trở lên
- MySQL 5.7+/8.x hoặc tương đương
- `lib/mysql-connector-j-8.3.0.jar` có trong thư mục `lib/`

Cấu hình cơ sở dữ liệu
- File cấu hình: `src/Database/db.properties`
- Định dạng mẫu:
  db.url=jdbc:mysql://HOST:PORT/DBNAME?useSSL=false&serverTimezone=UTC
  db.user=your_user
  db.password=your_password

Ví dụ mặc định (nếu không có file cấu hình):
- URL: `jdbc:mysql://localhost:3306/store?useSSL=false&serverTimezone=UTC`
- User: `root` — Password: `123456789`

Chạy ứng dụng
- VS Code: mở workspace gốc → Run and Debug → chọn cấu hình `Run CuaHangTienLoi`.
- NetBeans: mở project loại Ant và chạy (cấu hình bảo toàn trong `nbproject`).
- Dòng lệnh (biên dịch + chạy):
```bash
# biên dịch (tạo build/classes)
javac -encoding UTF-8 -cp "lib/mysql-connector-j-8.3.0.jar" -d build/classes src/**/**/*.java

# chạy
java -cp "lib/mysql-connector-j-8.3.0.jar;build/classes" GUI.Start
```

Lưu ý kỹ thuật
- Đã thêm shim nhỏ `src/org/netbeans/lib/awtextra` (AbsoluteLayout/AbsoluteConstraints) để các form NetBeans biên dịch và chạy trên môi trường không có NetBeans.
- Các file `.form` và code do NetBeans sinh vẫn được giữ; nếu muốn refactor giao diện, cân nhắc chuyển giao diện sang code thủ công hoặc chuyển sang build tool như Gradle.

Cấu trúc thư mục chính
- `src/DTO` — lớp dữ liệu
- `src/DAL` — truy cập dữ liệu (JDBC)
- `src/BLL` — logic nghiệp vụ
- `src/GUI` — giao diện
- `lib/` — thư viện JAR (JDBC driver)
- `nbproject/` — cấu hình Ant/NetBeans

Vấn đề thường gặp & cách khắc phục nhanh
- Lỗi kết nối DB: kiểm tra `db.properties`, đảm bảo MySQL đang chạy và schema `store` đã tồn tại.
- Lỗi classpath: kiểm tra `lib/mysql-connector-j-8.3.0.jar` đã ở trong classpath khi biên dịch/chạy.

Demo tái tạo nhanh (Docker)

- Khởi tạo MySQL cục bộ và nạp schema mẫu (dùng `store.sql` trong repo):

```bash
# tạo branch để giữ lịch sử
git checkout -b modernized

# khởi MySQL và nạp schema (chỉ cần chạy lần đầu)
docker compose up -d

# dừng khi xong
docker compose down
```

- `docker-compose.yml` trong repo sẽ mount `CuaHangTienLoi/src/Database/store.sql` vào container để tự động tạo schema và dữ liệu mẫu.
