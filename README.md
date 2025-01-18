```markdown

# SE-LAB-Patterns

## بخش اول: پیاده‌سازی الگوی Adapter

### چرا از **Object Scope** برای پیاده‌سازی الگوی Adapter استفاده می‌کنیم؟
1. **انعطاف‌پذیری بیشتر:** می‌توانیم کتابخانه‌های مختلف (مانند JUNG و JGraphT) را بدون وابستگی مستقیم مدیریت کنیم.
2. **کاهش وابستگی:** به‌جای ارث‌بری مستقیم، از یک شیء داخلی استفاده می‌کنیم که تغییرات کتابخانه‌ها تأثیری بر کد اصلی ندارد.
3. **حل محدودیت ارث‌بری در جاوا:** به دلیل محدودیت ارث‌بری تک‌کلاسی در جاوا، استفاده از ترکیب (Object Scope) انعطاف بیشتری ایجاد می‌کند.

### مراحل پیاده‌سازی الگوی Adapter:
1. **تعریف واسط مشترک:** واسطی برای عملیات گراف تعریف می‌کنیم که قابلیت افزودن یال و پیمایش را فراهم کند:
   ```java
   public interface GraphLibrary {
       void addEdge(String vertex1, String vertex2);
       List<String> traverse(String startVertex);
   }
   ```

2. **ایجاد کلاس Adapter:** یک کلاس Adapter برای هر کتابخانه ایجاد می‌کنیم که واسط تعریف‌شده را پیاده‌سازی کند:
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

3. **جایگزینی آسان کتابخانه‌ها:** با تغییر کلاس Adapter، می‌توان از کتابخانه دیگری استفاده کرد، بدون اینکه کد اصلی تغییر کند.

### مزایا:
- **کاهش وابستگی به کتابخانه‌ها:** تغییرات در کتابخانه‌ها به سادگی انجام می‌شود.
- **افزایش قابلیت نگهداری:** کد خواناتر و انعطاف‌پذیرتر می‌شود.

---

## بخش دوم: تغییر کتابخانه از JUNG به JGraphT

### تغییرات انجام‌شده:

#### 1. **به‌روزرسانی وابستگی‌ها در `pom.xml`:**
- حذف وابستگی JUNG و اضافه کردن JGraphT:
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

#### 2. **بازنویسی کلاس Adapter:**
- کلاس Adapter برای استفاده از JGraphT بازنویسی شد:
   ```java
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

#### 3. **چالش‌های مهاجرت:**
- **تفاوت API‌ها:**
  - JUNG برای یال‌ها از رشته‌ها استفاده می‌کرد، اما JGraphT از `DefaultEdge` استفاده می‌کند.
  - نیاز به بازنویسی بخش‌هایی از کد برای سازگاری با JGraphT.
- **تغییر رفتار برخی متدها:** متدهای پیمایش در JGraphT متفاوت هستند و نیاز به تطبیق داشتند.

### نتیجه:
با استفاده از الگوی Adapter، مهاجرت به JGraphT به‌آسانی انجام شد و تغییرات تنها محدود به کلاس Adapter و فایل پیکربندی Maven بود.

---

## بخش سوم: تحلیل الگوی Strategy

### **مزایای استفاده از الگوی Strategy:**
1. **جداسازی منطق الگوریتم‌ها:** با جدا کردن الگوریتم‌های پیمایش (مانند DFS و BFS) از ساختار گراف، انعطاف‌پذیری افزایش می‌یابد.
2. **افزودن یا تغییر آسان الگوریتم‌ها:** تغییر یا اضافه کردن الگوریتم‌های جدید بدون تغییر در کد اصلی امکان‌پذیر است.
3. **افزایش خوانایی و نگهداری کد:** کد خواناتر، تست‌پذیرتر و قابل نگهداری‌تر می‌شود.

### **مراحل پیاده‌سازی الگوی Strategy:**
1. **تعریف واسط (Interface):**
   یک واسط برای الگوریتم‌های پیمایش تعریف کنید:
   ```java
   public interface TraversalStrategy {
       List<String> traverse(Graph<String, DefaultEdge> graph, String startVertex);
   }
   ```

2. **ایجاد کلاس‌های استراتژی:** برای هر الگوریتم (مانند DFS و BFS)، یک کلاس جداگانه پیاده‌سازی کنید:
   ```java
   public class DFSTraversal implements TraversalStrategy {
       @Override
       public List<String> traverse(Graph<String, DefaultEdge> graph, String startVertex) {
           // پیاده‌سازی DFS
           return new ArrayList<>();
       }
   }

   public class BFSTraversal implements TraversalStrategy {
       @Override
       public List<String> traverse(Graph<String, DefaultEdge> graph, String startVertex) {
           // پیاده‌سازی BFS
           return new ArrayList<>();
       }
   }
   ```

3. **استفاده از الگوی Strategy در کد اصلی:** در زمان اجرا، استراتژی پیمایش دلخواه را تعیین کنید:
   ```java
   public class GraphTraversalContext {
       private TraversalStrategy strategy;

       public void setStrategy(TraversalStrategy strategy) {
           this.strategy = strategy;
       }

       public List<String> traverse(Graph<String, DefaultEdge> graph, String startVertex) {
           return strategy.traverse(graph, startVertex);
       }
   }
   ```

### نتیجه:
الگوی Strategy باعث شد که تغییر الگوریتم‌های پیمایش به‌آسانی انجام شود و منطق الگوریتم‌ها از کد اصلی جدا گردد. این رویکرد کدی انعطاف‌پذیر، مقیاس‌پذیر و قابل نگهداری ارائه داد.

</div>
```
