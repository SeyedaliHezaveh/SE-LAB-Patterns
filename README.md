# SE-LAB-Patterns
بخش اول - پیاده سازی الگوی Adapter
برای پیاده‌سازی الگوی **Adapter**، از روش **Object Scope** استفاده می‌کنیم زیرا:

1. **انعطاف‌پذیری بیشتر:** می‌توان کتابخانه‌های مختلف (JUNG و JGraphT) را بدون وابستگی مستقیم مدیریت کرد.
2. **کاهش وابستگی:** Adapter به جای ارث‌بری، از یک شیء داخلی استفاده می‌کند که تغییرات کتابخانه‌ها تأثیری بر کد اصلی ندارد.
3. **حل محدودیت ارث‌بری در جاوا:** چون جاوا فقط از یک کلاس می‌تواند ارث‌بری کند، ترکیب (Object Scope) انعطاف‌پذیری بیشتری فراهم می‌کند.

در این روش، یک واسط مشترک تعریف می‌شود و کلاس Adapter با ترکیب اشیاء کتابخانه متدهای مورد نیاز را پیاده‌سازی می‌کند. این انتخاب باعث افزایش نگهداری و مقیاس‌پذیری پروژه می‌شود.


برای پیاده‌سازی الگوی **Adapter** به روش **Object Scope**:
<div style="direction: rtl; text-align: right;">

1. **تعریف واسط (Interface):** یک واسط برای عملیات گراف (مثل افزودن یال و پیمایش) ایجاد می کنیم:
   ```java
   public interface GraphLibrary {
       void addEdge(String vertex1, String vertex2);
       List<String> traverse(String startVertex);
   }
   ```

2. **ایجاد کلاس Adapter:** یک کلاس برای هر کتابخانه (مثل JUNG یا JGraphT) که واسط را پیاده‌سازی کند و از شیء کتابخانه به‌عنوان یک فیلد داخلی استفاده کند:
   ```java
   public class JGraphTAdapter implements GraphLibrary {
       private Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

       @Override
       public void addEdge(String vertex1, String vertex2) {
           graph.addVertex(vertex1);
           graph.addVertex(vertex2);
           graph.addEdge(vertex1, vertex2);
       }

       @Override
       public List<String> traverse(String startVertex) {
           // پیاده‌سازی BFS یا DFS
           return new ArrayList<>();
       }
   }
   ```
</div>
3. **جایگزینی آسان کتابخانه‌ها:** با تغییر کلاس Adapter، کتابخانه جدید بدون تغییر در کد اصلی استفاده می‌شود.

این روش وابستگی به کتابخانه‌ها را جدا و تغییرات را ساده می‌کند.


بخش دوم - تغییر کتابخانه
مهاجرت از کتابخانه **JUNG** به **JGraphT** شامل تغییرات زیر بود:

#### **1. تغییر وابستگی‌ها در فایل Maven (`pom.xml`)**
- وابستگی JUNG حذف و وابستگی JGraphT اضافه شد:
  ```xml
  <!-- حذف وابستگی JUNG -->
  <dependency>
      <groupId>net.sf.jung</groupId>
      <artifactId>jung-graph-impl</artifactId>
      <version>2.1.1</version>
  </dependency>

  <!-- اضافه کردن وابستگی JGraphT -->
  <dependency>
      <groupId>org.jgrapht</groupId>
      <artifactId>jgrapht-core</artifactId>
      <version>1.5.2</version>
  </dependency>
  ```


#### **2. تغییر در کلاس Adapter**
- کلاس Adapter برای پشتیبانی از JGraphT بازنویسی شد. به جای متدهای JUNG، از API‌های JGraphT استفاده کردیم:
  ```java
  // Adapter برای JGraphT
  public class JGraphTAdapter implements GraphLibrary {
      private Graph<String, DefaultEdge> graph;

      public JGraphTAdapter() {
          graph = new SimpleGraph<>(DefaultEdge.class);
      }

      @Override
      public void addEdge(String vertex1, String vertex2) {
          graph.addVertex(vertex1);
          graph.addVertex(vertex2);
          graph.addEdge(vertex1, vertex2);
      }

      @Override
      public List<String> traverse(String startVertex) {
          // پیاده‌سازی روش پیمایش (BFS یا DFS)
          return new ArrayList<>();
      }
  }
  ```

#### **3. چالش‌های مهاجرت**
- **تفاوت در API‌ها:** 
  - JUNG برای یال‌ها از رشته‌ها استفاده می‌کند، اما JGraphT از کلاس DefaultEdge.
  - نیاز به بازنویسی بخش‌هایی از کد برای هماهنگی با JGraphT.
- **تغییر رفتار:** 
  - برخی از متدهای پیمایش در JGraphT متفاوت عمل می‌کنند و به تطبیق نیاز داشتند.


#### **نتیجه**
با استفاده از الگوی **Adapter**، تغییر کتابخانه‌ها به صورت شفاف انجام شد و نیازی به تغییر در بخش‌های اصلی پروژه نبود. مهاجرت تنها با بازنویسی کلاس Adapter و تغییر وابستگی‌ها در Maven مدیریت شد.


