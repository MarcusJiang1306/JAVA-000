# 作业

写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）
---

Autowired，Resource和Qualifier
~~~
public class School implements ISchool {
    
    // Resource 
    @Autowired(required = true) //primary
    Klass class1;

    @Resource(name = "st2")
//    @Autowired
//    @Qualifier("st2")
    Student student100;

    @Override
    public void ding(){
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
    }
    
}
~~~

xml配置，bean注入和集合类注入以及配置文件定义数据
~~~
   <bean id="st1" class="com.marcus.configs.xml.Student">
        <constructor-arg name="id" value="${student.config.st1.id}"/>
        <constructor-arg name="name" value="${student.config.st1.name}"/>
    </bean>

    <bean id="st2" class="com.marcus.configs.xml.Student">
        <constructor-arg name="id" value="233"/>
        <constructor-arg name="name" value="st233"/>
    </bean>

    <bean id="school1" class="com.marcus.configs.xml.School">
        <property name="student100" ref="st1"/>
    </bean>
    <bean id="school2" class="com.marcus.configs.xml.School">
        <property name="student100" ref="st2"/>
    </bean>

    <bean id="class1" class="com.marcus.configs.xml.Klass">
        <property name="students">
            <list>
                <ref bean="st1"/>
                <ref bean="st2"/>
                <bean class="com.marcus.configs.xml.Student">
                    <constructor-arg name="id" value="80"/>
                    <constructor-arg name="name" value="st80"/>
                </bean>
            </list>
        </property>
    </bean>

    <bean id="school" class="com.marcus.configs.xml.School"/>
~~~

JavaConfig类装配
~~~
@Configuration
@ComponentScan
public class ApplicationConfig {

    @Bean
    public Student student1() {
        return new Student(88, "zhangsan");
    }

    @Bean("cfgClass")
    public Klass klass(Student student1) {
        Klass klass = new Klass();
        ArrayList<Student> students = new ArrayList<>();
        students.add(student1);
        klass.setStudents(students);
        return klass;
    }
~~~

value注入数值
~~~
@Data
@NoArgsConstructor
@ToString
@Component("studentCfg1")
public class ValueStudent implements Serializable {

    @Value("1")
    private int id;

    @Value("Value name")
    private String name;

    public ValueStudent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void init() {
        System.out.println("hello...........");
    }

    public ValueStudent create() {
        return new ValueStudent(101, "KK101");
    }
~~~

