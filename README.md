# CuaHangTienLoi

Ứng dụng Java Swing quản lý cửa hàng tiện lợi nhỏ. Dự án dùng kiến trúc phân lớp `GUI -> BLL -> DAL -> DTO -> Database`, lưu dữ liệu trong MySQL và có thể chạy bằng NetBeans, VS Code hoặc dòng lệnh.

## Chức năng chính

- Đăng nhập và phân quyền theo `login.status`.
- Quản lý hóa đơn, tạo hóa đơn kèm chi tiết sản phẩm.
- Xem doanh thu theo khoảng ngày, thống kê hóa đơn, sản phẩm và danh mục bán chạy.
- Quản lý danh mục, sản phẩm, nhà cung cấp, khách hàng, nhân viên và tài khoản.
- Tìm kiếm keyword realtime và filter theo cột ở các màn quản lý đã hiện đại hóa.
- Tự sinh mã theo định dạng thống nhất:
  - Khách hàng: `kh001`, `kh002`, ...
  - Nhân viên: `nv001`, `nv002`, ...
  - Nhà cung cấp: `ncc001`, `ncc002`, ...
  - Sản phẩm: `sp001`, `sp002`, ...
  - Danh mục: `dm001`, `dm002`, ...

## Phân quyền

- `status = 0`: admin. Được xem và thao tác tất cả tab, bao gồm `QL Nhân Viên` và `Tài Khoản`.
- `status = 1`: nhân viên. Không thấy tab `QL Nhân Viên` và `Tài Khoản`.
- Mật khẩu chỉ được xem/sửa trong tab `Tài Khoản`. Tab `QL Nhân Viên` chỉ quản lý thông tin nhân viên, không hiển thị hoặc thay đổi mật khẩu.

Tài khoản seed mặc định:

```text
nv001 / nv001 / status 0
nv002 / nv002 / status 1
```

## Công nghệ

- Java Swing
- JDBC
- MySQL 8.x
- MySQL Connector/J `8.3.0`
- NetBeans Ant project
- Docker Compose cho database local tùy chọn

## Cấu trúc thư mục

```text
CuaHangTienLoi/
  lib/                         MySQL JDBC driver
  src/BLL/                     Business logic
  src/DAL/                     Data access JDBC
  src/DTO/                     Data transfer objects
  src/GUI/                     Swing UI
  src/Database/Connect.java    Kết nối MySQL
  src/Database/db.properties   Cấu hình database
  src/Database/store.sql       Schema và dữ liệu mẫu
```

## Cấu hình database

File cấu hình:

```text
CuaHangTienLoi/src/Database/db.properties
```

Mặc định:

```properties
db.url=jdbc:mysql://localhost:3306/store?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=root
db.password=""
```

Nếu dùng Docker Compose trong repo, mật khẩu MySQL mặc định trong `docker-compose.yml` là `rootpass`; khi đó cần sửa `db.properties` cho khớp.

## Khởi tạo database

Import schema và dữ liệu mẫu:

```powershell
C:\xampp\mysql\bin\mysql.exe -uroot -p123456789 < CuaHangTienLoi\src\Database\store.sql
```

Hoặc dùng Docker:

```bash
docker compose up -d
```

Lưu ý: Docker chỉ tự import `store.sql` khi volume database còn mới. Nếu đã có volume cũ, cần xóa volume hoặc import SQL thủ công.

## Build và chạy

Từ thư mục `CuaHangTienLoi/CuaHangTienLoi`:

```powershell
$srcList = Join-Path $env:TEMP 'chtl-sources.txt'
Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } | Set-Content -Path $srcList
cmd /c javac -encoding UTF-8 -cp "lib/mysql-connector-j-8.3.0.jar" -d build\classes "@$srcList"
java -cp "build\classes;lib\mysql-connector-j-8.3.0.jar" GUI.Start
```

Trên NetBeans có thể mở project Ant và chạy main class `GUI.Start`.

## Ghi chú hiện trạng

- Một số màn hình Swing cũ được sinh bởi NetBeans, một số màn hình đã được viết lại thủ công để đồng bộ layout.
- Các màn đã hiện đại hóa gồm danh mục, sản phẩm, nhà cung cấp, nhân viên, khách hàng, tài khoản, hóa đơn và doanh thu.
- `sanpham` đã có thêm liên kết `mancc` với nhà cung cấp.
- Dữ liệu khách hàng đã được chuẩn hóa mã theo format `khxxx`.
