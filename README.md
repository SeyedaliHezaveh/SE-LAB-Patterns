# SE-LAB-Patterns
بخش اول - پیاده سازی الگوی Adapter
برای پیاده‌سازی الگوی **Adapter**، از روش **Object Scope** استفاده می‌کنیم زیرا:

1. **انعطاف‌پذیری بیشتر:** می‌توان کتابخانه‌های مختلف (JUNG و JGraphT) را بدون وابستگی مستقیم مدیریت کرد.
2. **کاهش وابستگی:** Adapter به جای ارث‌بری، از یک شیء داخلی استفاده می‌کند که تغییرات کتابخانه‌ها تأثیری بر کد اصلی ندارد.
3. **حل محدودیت ارث‌بری در جاوا:** چون جاوا فقط از یک کلاس می‌تواند ارث‌بری کند، ترکیب (Object Scope) انعطاف‌پذیری بیشتری فراهم می‌کند.

در این روش، یک واسط مشترک تعریف می‌شود و کلاس Adapter با ترکیب اشیاء کتابخانه متدهای مورد نیاز را پیاده‌سازی می‌کند. این انتخاب باعث افزایش نگهداری و مقیاس‌پذیری پروژه می‌شود.


برای پیاده‌سازی الگوی **Adapter** به روش **Object Scope**:
<div dir="rtl">

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
