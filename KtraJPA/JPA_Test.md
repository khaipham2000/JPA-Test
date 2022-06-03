**1. Thuộc tính name trong annotation @Entity khác với thuộc tính name trong @Table như thế nào? Hãy giải thích rõ cần thì minh hoạ**

@Entity (name= ”XYZ”)
Điều này sẽ thay đổi tên nhận dạng thành XYZ. Theo mặc định, hibernate tạo tên bảng giống như tên lớp chỉ có chú thích @Enity. Với @Entity (name = ”XYZ”) và trong trường hợp không có chú thích @Table để thay đổi tên bảng, tên thực thể sẽ được sử dụng để tạo bảng thay vì tên lớp.

@Table (name = ”XYZ”))
@Table (name = ”XYZ”)) chú thích (javax.persistence.Table /org.hibernate.annotations.Table) yêu cầu hibernate tạo bảng với tên XYZ thay vì tên lớp. @Table (name = ”XYZ”)) khác với @Entity (name = ”XYZ”) ở chỗ @Table chỉ thay đổi tên bảng và tên thực thể vẫn được giữ nguyên theo mặc định, là tên lớp. Một trong những ứng dụng của sự khác biệt này giữa tên thực thể và tên bảng là việc sử dụng chúng trong HQL(Hibernate Query Language).

**2. Để debug câu lệnh SQL mà Hibernate sẽ sinh ra trong quá trình thực thi, cần phải bổ sung lệnh nào vào file application.properties?**

```
spring.jpa.show-sql=true;
```

**3. Khi sử dụng H2, làm thế nào để xem được cơ sở dữ liệu và viết câu truy vấn?**

Cơ sở dữ liệu H2 có bảng điều khiển GUI được nhúng để duyệt nội dung của cơ sở dữ liệu và chạy các truy vấn SQL. Theo mặc định, bảng điều khiển H2 không được bật trong Spring. Sau đó, sau khi khởi động ứng dụng, chúng ta có thể điều hướng đến http: // localhost: 8080 / h2-console, trang này sẽ hiển thị cho chúng ta một trang đăng nhập.
Sau đó nhập username và password để xem cơ sở dữ liệu. Mặc định Spring Boot sẽ cấu hình tự động cho H2 hoạt động trên memory với username là sa và một password rỗng.
Tuy nhiên chúng ta có thể thay đổi các cấu hình mặc định này bằng cách thêm các thuộc tính sau vào file application.properties.

```
spring.datasource.url=jdbc:h2:mem:bookdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

Để truy vấn chúng ta viết các lệnh truy vấn vào ô chữ lớn trên màn hình và ấn Run

**4. Khi viết mô tả một model, những thuộc tính chúng ta không muốn lưu xuống CSDL thì cần đánh dấu bằng annotation nào?** 

@Transient
@Transientđược sử dụng để đánh dấu các trường không nên tồn tại. Xem xét ví dụ ngắn này:

```java
public enum Gender { MALE, FEMALE, UNKNOWN }

@Entity
public Person {
    private Gender g;
    private long id;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Gender getGender() { return g; }
    public void setGender(Gender g) { this.g = g; }

    @Transient
    public boolean isMale() {
        return Gender.MALE.equals(g);
    }

    @Transient
    public boolean isFemale() {
        return Gender.FEMALE.equals(g);
    }
}
```

Khi lớp này được cung cấp cho JPA, nó vẫn tồn tại gendervà idnhưng không cố gắng duy trì các phương thức boolean trợ giúp - mà không có @Transient hệ thống cơ bản sẽ phàn nàn rằng lớp Entity Personbị thiếu setMale()và setFemale()các phương thức và do đó sẽ không tồn Persontại.

**5. Annotation @Column dùng để bổ sung tính chất cho cột ứng với một thuộc tính. Tham số nào trong @Column sẽ đổi lại tên cột nếu muốn khác với tên thuộc tính, tham số nào chỉ định yêu cầu duy nhất, không được trùng lặp dữ liệu, tham số nào buộc trường không được null?**

- name: đặt lại tên bảng, khác với tên entity
- unique: tạo unique constraint
- nullable: cho phép null hay không null

**6. Có 2 sự kiện mà JPA có thể bắt được, viết logic bổ sung**
- Ngay trước khi đối tượng Entity lưu xuống CSDL (ngay trước lệnh INSERT)**
- Ngay trước khi đối tượng Entity cập nhật xuống CSDL (ngay trước lệnh UPDATE)**

**Vậy 2 annotation này là gì**

@PrePersist
@PreUpdate

**7. Tổ hợp các trường thông tin địa chỉ: country, city, county, addressline thường luôn đi cùng
nhau và sử dụng lại trong các Entity khác nhau. Nhóm 2 annotation nào dùng để tái sử dụng,
nhúng một Entity vào một Entity khác?**

Khi có nhiều bảng chung nhau một cấu trúc, thì đánh dấu cấu trúc dùng chung bằng @Embeddable
Những bảng dùng chung cấu trúc đánh dấu bằng @Embedded

**8. JpaRepository là một interface có sẵn trong thư viện JPA, nó cũng cấp các mẫu hàm thuận tiện cho thao tác dữ liệu. Cụ thể JpaRepository kế thừa từ interface nào?**
CRUDRepository
PagingAndSortingRepository
QueryByExampleExecutor

**9. Hãy viết khai báo một interface repository thao tác với một Entity tên là Post, kiểu dữ liệu trường Identity là long, tuân thủ interface JpaRepository.**

```java
@Repository
public interface PostRepo extends JpaRepository<Post, Long>{}
```

**10. Khi đã chọn một cột là Identity dùng @Id để đánh dấu, thì có cần phải dùng xác định unique dùng annotation @Column(unique=true) không?**

Không. @Id để chỉ định khóa chính của một thực thể entity

**11. Khác biệt giữa @Id với @NaturalId là gì?**

@Id primary key cần giữ nguyên không đổi, nhưng @NaturalId có thể được phép thay đổi, miễn đảm bảo duy nhất

**12. Có những cột không phải primary key (@Id) hay @NaturalId, dữ liệu có thể trùng lặp (unique không đảm bảo true), nhưng cần đánh chỉ mục (index) để tìm kiếm nhanh hơn vậy phải dùng annotation gì? Hãy viết 1 ví dụ sử dụng annotation đó với index cho 1 column và 1 ví dụ với index cho tổ hợp nhiều column. Tham khảo tại (https://www.baeldung.com/jpaindexes)**

1.ví dụ sử dụng annotation đó với index cho 1 column

Chỉ mục của chúng ta phải có tên. Nếu chúng ta không chỉ định thì đó là giá trị tự tạo. Khi muốn cấu hình tùy chỉnh, chúng ta chỉ cần thêm thuộc tính name:

```java
@Index(name = "index1", columnList = "name")
```
ta sẽ tạo được chỉ mục với tên do người dùng xác định

```java
[main] DEBUG org.hibernate.SQL -
  create index index1 on Client (name)
```

2.ví dụ với index cho tổ hợp nhiều column

Chúng ta có thể chỉ định nhiều cột cho một chỉ mục.
Chúng ta làm điều đó bằng cách phân tách các tên bằng dấu phẩy
```java
@Index(name = "mulitIndex1", columnList = "firstName, lastName")

@Index(name = "mulitIndex2", columnList = "lastName, firstName")
```

tương tự như trên 
```java
[main] DEBUG org.hibernate.SQL -
  create index mulitIndex1 on Client (firstName, lastName)
   
[main] DEBUG org.hibernate.SQL -
  create index mulitIndex2 on Client (lastName, firstName)
```

**13. Annotation @GeneratedValue dùng để chọn cách tự sinh unique id cho primary key phải là trường kiểu int hoặc long. Nếu trường primary key có kiểu là String, chúng ta không thể dùng @GeneratedValue vậy hãy thử liệt kê các cách đảm bảo sinh ra chuỗi có tính duy nhất?**

-Cách 1: Custom ID generator
```java
public class RandomIDGenerator implements IdentifierGenerator {
@Override
public Serializable generate(SharedSessionContractImplementor session, Object obj) 
throws HibernateException {
RandomString randomString = new RandomString(10);//Sinh chuỗi ngẫu nhiên 10 ký tự
return randomString.nextString();
}
}
```
```java
@Data
@Entity(name="bar")
@Table(name="bar")
public class Bar {
@GenericGenerator(name = "random_id", strategy = 
"vn.techmaster.demojpa.model.id.RandomIDGenerator")
@Id @GeneratedValue(generator="random_id")
private String id;
private String name;
}
```

-Cách 2: Composite Primary Key
Định nghĩa composite key
```java
@Data
public class StudentSubjectId implements Serializable
{
private String studentId;
private String subjectId;
}
```

Định nghĩa Entity cho bảng trung gian
```java
Entity
@Data
@AllArgsConstructor
@IdClass(StudentSubjectId.class)
public class StudentSubject {
@Id private String studentId;
@Id private String subjectId;
private int score;
}
```

- Cách 3: @NaturalId
- Cách 4: UUID

**14. Giả sử có 1 class Employee với các fields sau {id, emailAddress, firstName, lastName}. Hãy viết các method trong interface EmployeeRespository để:**
- Tìm tất cả các Employee theo emailAddress và lastName
- Tìm tất cả các Employee khác nhau theo firstName hoặc lastName
- Tìm tất cả các Employee theo lastName và sắp xếp thứ tự theo firstName tăng dần
- Tìm tất cả các Employee theo fistName không phân biệt hoa thường 

**15. Hãy nêu cách sử dụng của @NamedQuery và @Query. Cho ví dụ**
*@NamedQuery*
Nếu bạn không sử dụng repository interface mà chỉ dùng EntityManager để thao tác dữ liệu. Query đó sử dụng ở nhiều nơi khác nhau thì có thể dùng @NamedQuery.
```java
@Entity(name ="oto") //tên entity sẽ sử dụng trong câu lệnh JPQL
@Table(name = "car") //tên table sẽ sử dụng để lưu xuống bảng vật lý trong CSDL
@Data //annotation của Lombok
@NamedQuery(name = "Car.findById", query = "SELECT c FROM oto c WHERE c.id=:id")
public class Car {
@Id private long id; 
private String model;
private String maker;
private int year; 
}
```

*@Query*
Với annotation @Query ta có thể khai báo câu query cho các method trong repository.

Việc khai báo câu query với @Query giúp ta tối ưu câu sql, và xử lý trong những trường hợp mà các method do Spring Data không thể đáp ứng:

Việc sử dụng các method có sẵn khi extends interface JPARepository, CrudRepository  không đáp ứng được yêu cầu.
Việc đặt tên method theo chuẩn Query Creation quá dài hoặc tối nghĩa. (Ví dụ bạn muốn truy vấn theo 5 điều kiện thì tên method của bạn sẽ gồm 5 điều kiện đó => quá dài)

```java
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
  @Query("SELECT e FROM Customer e WHERE e.name = ?1 AND e.address = ?2")
  List<Customer> findByNameAndAddress(String name, String address);
}
```

**16. Làm thế nào để có thể viết custom method implemetations cho Jpa Repository. Nêu ví dụ**

Bạn cần tạo một giao diện riêng cho các phương pháp tùy chỉnh của mình:
```java
public interface AccountRepository 
    extends JpaRepository<Account, Long>, AccountRepositoryCustom { ... }

public interface AccountRepositoryCustom {
    public void customMethod();
}
```
và cung cấp một lớp triển khai cho giao diện đó:
```java
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @Autowired
    @Lazy
    AccountRepository accountRepository;  /* Optional - if you need it */

    public void customMethod() { ... }
}
```