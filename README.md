# QLBH - Sales MIS (Java Swing + JPA/Hibernate)

Du an nay la ung dung desktop quan ly ban hang (QLBH) viet bang Java, theo mo hinh nhieu lop gom `view -> controller -> service -> dao -> database`.

## 1) Tong quan du an

- **Ten artifact:** `sales-mis`
- **Ngon ngu:** Java 17
- **Build tool:** Maven
- **UI:** Java Swing
- **ORM/Persistence:** Jakarta Persistence + Hibernate ORM
- **CSDL:** MySQL
- **Muc tieu hien tai:** quan ly don hang va chi tiet don hang, bao gom tao/sua/xoa/tim kiem.

## 2) Cong nghe va phu thuoc

Khai bao trong `pom.xml`:

- `org.hibernate.orm:hibernate-core:7.3.0.Final`
- `jakarta.persistence:jakarta.persistence-api:3.2.0`
- `com.mysql:mysql-connector-j:9.6.0`
- `org.slf4j:slf4j-simple:2.0.17`

## 3) Cau truc thu muc chinh

```text
src/main/java/com/example/salesmis
|- AppLauncher.java
|- config/
|  \- JpaUtil.java
|- controller/
|  \- OrderController.java
|- service/
|  |- OrderService.java
|  |- LookupService.java
|  \- impl/
|     |- OrderServiceImpl.java
|     \- LookupServiceImpl.java
|- dao/
|  |- SalesOrderDAO.java
|  |- CustomerDAO.java
|  |- ProductDAO.java
|  |- OrderDetailDAO.java
|  |- ReportDAO.java
|  \- impl/
|     |- SalesOrderDAOImpl.java
|     |- CustomerDAOImpl.java
|     |- ProductDAOImpl.java
|     |- OrderDetailDAOImpl.java
|     \- ReportDAOImpl.java
|- model/
|  |- entity/
|  |  |- Customer.java
|  |  |- Product.java
|  |  |- SalesOrder.java
|  |  \- OrderDetail.java
|  |- dto/
|  |  |- OrderLineInput.java
|  |  |- ProductSalesDTO.java
|  |  |- CustomerRevenueDTO.java
|  |  |- MonthlyRevenueDTO.java
|  |  \- StatusCountDTO.java
|  \- enumtype/
|     \- OrderStatus.java
\- view/
   |- MainFrame.java
   \- OrderManagementPanel.java
```

## 4) Kien truc va trach nhiem tung lop

### 4.1 View (Swing)

- `MainFrame`: cua so chinh, host panel quan ly don hang.
- `OrderManagementPanel`:
  - Form thong tin don hang (order no, ngay, khach hang, status, ghi chu)
  - Form them dong chi tiet (san pham, so luong, don gia)
  - Bang don hang va bang chi tiet
  - Nut thao tac: `New`, `Save`, `Update`, `Delete`, `Search`
  - Hien thi loi bang dialog (`JOptionPane`) khi validate hoac xu ly that bai.

### 4.2 Controller

- `OrderController` la lop trung gian giua View va Service.
- Parse du lieu UI:
  - Chuyen `orderDateText` -> `LocalDate`
  - Chuyen `statusText` -> `OrderStatus`
- Goi cac use-case:
  - Lay danh sach don
  - Tim kiem
  - Tao, cap nhat, xoa don
  - Lay danh sach khach hang/san pham cho combobox.

### 4.3 Service

- `OrderService` + `OrderServiceImpl`:
  - Chua logic nghiep vu don hang.
  - Validate input:
    - `orderNo`, `orderDate`, `customerId` khong rong/null
    - Don hang phai co it nhat 1 dong chi tiet
    - So luong > 0, don gia hop le
  - Kiem tra trung `orderNo` khi tao/cap nhat.
  - Tinh `lineTotal` va `totalAmount`.
  - Quan ly danh sach `OrderDetail` thong qua `addDetail()` va `clearDetails()`.
- `LookupService` + `LookupServiceImpl`:
  - Cung cap du lieu danh muc:
    - Toan bo `Customer`
    - Toan bo `Product` dang active.

### 4.4 DAO

- DAO interface + implementation su dung `EntityManager` tu `JpaUtil`.
- `SalesOrderDAOImpl`:
  - CRUD don hang
  - Search keyword theo `orderNo` hoac `customer.fullName`
  - Dung `JOIN FETCH` de lay kem customer/detail/product
  - Quan ly transaction cho `save/update/delete`.
- `CustomerDAOImpl`, `ProductDAOImpl`, `OrderDetailDAOImpl`:
  - Truy van danh muc va chi tiet.
- `ReportDAO` + `ReportDAOImpl`:
  - Co san 15 truy van bao cao (q01 -> q15) nhu doanh thu theo khach, theo thang, top san pham, thong ke trang thai...
  - Hien tai chua duoc noi vao UI.

## 5) Mo hinh du lieu (Entity)

### 5.1 Customer (`customers`)

- Truong chinh: `customer_code`, `full_name`, `phone`, `email`, `address`, `active`
- Quan he: `1 - n` voi `SalesOrder`.

### 5.2 Product (`products`)

- Truong chinh: `sku`, `product_name`, `category`, `unit_price`, `stock_qty`, `active`
- Quan he: `1 - n` voi `OrderDetail`.

### 5.3 SalesOrder (`orders`)

- Truong chinh: `order_no`, `order_date`, `total_amount`, `status`, `note`
- Quan he:
  - `n - 1` voi `Customer`
  - `1 - n` voi `OrderDetail` (`cascade = ALL`, `orphanRemoval = true`).

### 5.4 OrderDetail (`order_details`)

- Truong chinh: `quantity`, `unit_price`, `line_total`
- Quan he:
  - `n - 1` voi `SalesOrder`
  - `n - 1` voi `Product`.

### 5.5 Enum

- `OrderStatus`: `NEW`, `CONFIRMED`, `COMPLETED`, `CANCELLED`.

## 6) Cau hinh persistence va database

File `src/main/resources/META-INF/persistence.xml`:

- Persistence unit: `salesPU`
- Provider: `org.hibernate.jpa.HibernatePersistenceProvider`
- JDBC URL mac dinh:
  - `jdbc:mysql://localhost:3306/sales_mis?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true`
- User/Password hien tai:
  - user: `root`
  - password: `12345`
- Hibernate:
  - `hibernate.dialect=org.hibernate.dialect.MySQLDialect`
  - `hibernate.hbm2ddl.auto=validate`
  - `hibernate.show_sql=true`
  - `hibernate.format_sql=true`

**Luu y quan trong:**
- `hbm2ddl.auto=validate` nghia la Hibernate chi kiem tra schema, **khong tu tao bang**.
- Ban can tao schema/bang dung voi mapping entity truoc khi chay.
- Thong tin DB dang hardcode trong file cau hinh, nen doi theo moi truong thuc te.

## 7) Luong khoi dong ung dung

`AppLauncher` thuc hien:

1. Tao cac DAO implementation.
2. Tao service (`LookupServiceImpl`, `OrderServiceImpl`).
3. Tao `OrderController`.
4. Khoi tao Swing UI trong `SwingUtilities.invokeLater(...)`.

## 8) Chuc nang hien co

- Quan ly don hang:
  - Tao don hang moi
  - Them/xoa dong chi tiet
  - Cap nhat don
  - Xoa don
  - Tim kiem theo ma don hoac ten khach hang
  - Tai lai chi tiet khi chon dong trong bang.
- Lookup:
  - Nap danh sach khach hang
  - Nap danh sach san pham active.

## 9) Huong dan chay du an

### 9.1 Dieu kien tien quyet

- Java 17
- Maven 3.9+
- MySQL 8+
- Da tao schema `sales_mis` va cac bang dung mapping.

### 9.2 Build

```bash
mvn clean package
```

### 9.3 Chay ung dung

Co the chay bang IDE (run `AppLauncher`) hoac Maven:

```bash
mvn exec:java -Dexec.mainClass="com.example.salesmis.AppLauncher"
```

Neu chua cau hinh `exec-maven-plugin`, uu tien chay tu IDE.

## 10) Ghi chu nghiep vu/ky thuat dang can chu y

- `OrderManagementPanel` dang de gia tri ngay mac dinh co dinh (`2026-03-24`) khi tao moi/clear form.
- Service chua cap nhat `stock_qty` khi tao/cap nhat/xoa don (neu day la yeu cau nghiep vu thi can bo sung).
- Co tang `ReportDAO` kha day du, nhung chua expose ra service/controller/view.
- Chua thay test unit/integration trong project.

## 11) Dinh huong mo rong de xuat

- Tach cau hinh DB ra `application.properties` hoac bien moi truong.
- Bo sung migration (`Flyway`/`Liquibase`) de quan ly schema.
- Them test cho:
  - Validation service
  - Dao query
  - Luong tao/cap nhat don.
- Bo sung man hinh bao cao dung `ReportDAO`.
- Them logging va xu ly exception tap trung than thien hon cho UI.

---

Neu ban muon, minh co the viet tiep:
- file SQL tao schema theo dung entity mapping,
- checklist setup moi truong cho thanh vien moi,
- hoac tai lieu API noi bo (service/dao contracts) chi tiet hon.
