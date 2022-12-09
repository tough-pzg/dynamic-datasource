# Read Me First

dynamic-datasource 是一个动态数据源工具，支持配置多个数据源（目前只支持 Hikari，每个数据源使用单独的连接池），通过注解的方式动态选择当前sql使用的数据源

> 1.可选择使用@DS注解，设置当前类（所有方法）或 某个方法使用的数据源,并且依据就近原则，方法上的注解优先生效

> 2.可选择 DataSourceContextHolder.setDataSource() 手动设置使用的数据源 (这种方式不打印日志，但是需要手动清理数据源)


# Getting Started
## 1.默认的连接池配置参数
```
//约定：数据源无连接池配置时，则会使用如下默认配置
GLOBAL_HIKARI_CP_CONFIG.setMaximumPoolSize(10);
GLOBAL_HIKARI_CP_CONFIG.setIdleTimeout(600000L);
GLOBAL_HIKARI_CP_CONFIG.setAutoCommit(true);
GLOBAL_HIKARI_CP_CONFIG.setMaxLifetime(1800000L);
GLOBAL_HIKARI_CP_CONFIG.setConnectionTimeout(30000L);
```

## 2.配置默认数据源（必须）
```
#使用springboot 约定的数据源前缀（spring.datasource）
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/test
spring.datasource.username=postgres
spring.datasource.password=123456

#默认数据源的连接池，以 spring.datasource.hikari 为前缀
spring.datasource.hikari.maximum-pool-size=14
spring.datasource.hikari.idle-timeout=44
spring.datasource.hikari.auto-commit=true
spring.datasource.hikari.pool-name=financial-input-HikariCP
spring.datasource.hikari.max-lifetime=44
spring.datasource.hikari.connection-timeout=44
```

## 3.配置自定义的数据源（可选）
```
#数据源名称，多个以逗号分隔
spring.datasource.names=first,second

#以spring.datasource.{数据源名称} 为前缀
# first 数据源配置，以spring.datasource.first为前缀
spring.datasource.first.driverClassName=org.postgresql.Driver
spring.datasource.first.url=jdbc:postgresql://localhost:5432/test1
spring.datasource.first.username=postgres
spring.datasource.first.password=123456

#first数据源连接池无配置信息，则会使用默认的连接池配置参数

# second 数据源配置，以spring.datasource.second为前缀
spring.datasource.second.driverClassName=org.postgresql.Driver
spring.datasource.second.url=jdbc:postgresql://localhost:5432/test2
spring.datasource.second.username=postgres
spring.datasource.second.password=123456

#sencond 数据源的连接池，以 spring.datasource.second 为前缀
spring.datasource.second.hikari.maximum-pool-size=44
spring.datasource.second.hikari.idle-timeout=44
spring.datasource.second.hikari.auto-commit=true
spring.datasource.second.hikari.pool-name=financial-input-oa-HikariCP
spring.datasource.second.hikari.max-lifetime=44
spring.datasource.second.hikari.connection-timeout=44
```
## 4.动态切换数据源
### 4.1 添加测试数据
> localhost:5432/test 的 person表新增一条数据，person:{"age":1,"id":1,"name":"默认的数据源"}

> localhost:5432/test1 的 person表新增一条数据，person:{"age":1,"id":1,"name":"数据源1"}

> localhost:5432/test2 的 person表新增一条数据，person:{"age":1,"id":1,"name":"数据源2"}

### 4.2 新建一个mapper接口
```
@Mapper
public interface PersonMapper {
    
    //这里直接听过注解的方式，执行一个查询全表的sql
    @Select("select * from person ")
    List<Person> getAll();

}

```
### 4.3 新建一个Service类
```
@Service
@DS(value = "first")
public class PersonService {
    @Autowired
    private PersonMapper personMapper;
    
    //方法上设置 second
    @DS(value = "second")
    public List<Person> getAllWithSecond(){
        return personMapper.getAll();
    }
    
    //方法上未设置
    public List<Person> getAll(){
        return personMapper.getAll();
    }
    
    //方法上设置 xxx
    @DS(value = "xxx")
    public List<Person> getAllWithXxx(){
        return personMapper.getAll();
    }

    //方法上未设置，但是代码里手动设置
    public List<Person> getAllWithManual(){
        //手动设置接下来的sql使用的数据源 （注意：手动设置的不打印日志）
        DataSourceContextHolder.setDataSource("second");
        List<Person> all = personMapper.getAll();

        DataSourceContextHolder.setDataSource("first");
        List<Person> all2 = personMapper.getAll();

        //手动清理当前使用的数据源
        DataSourceContextHolder.clearDataSource();

        all.addAll(all2);
        return all;
    }

}

```
### 4.4 测试
```
    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() {
        System.out.println("方法上使用注解设置: second");
        System.out.println(JSON.toJSONString(personService.getAllWithSecond()));
        System.out.println();

        System.out.println("方法未使用注解设置，则使用类上注解设置的 first");
        System.out.println(JSON.toJSONString(personService.getAll()));
        System.out.println();

        System.out.println("方法上使用注解设置: xxx ，由于xxx是无效数据源，则会使用默认数据源");
        System.out.println(JSON.toJSONString(personService.getAllWithXxx()));
        System.out.println();

        System.out.println("方法未使用注解设置，但是手动设置，分别使用 second 和 first");
        System.out.println(JSON.toJSONString(personService.getAllWithManual()));
    }

```

### 4.5测试结果
```
控制台输出日志：

方法上使用注解设置: second
当前方法设置数据源：second
[{"age":1,"id":1,"name":"数据源2"}]

方法未使用注解设置，则使用类上注解设置的 first
当前类设置数据源：first
[{"age":1,"id":1,"name":"数据源1"}]

方法上使用注解设置: xxx ，由于xxx是无效数据源，则会使用默认数据源
当前方法设置数据源：xxx
[{"age":1,"id":1,"name":"默认的数据源"}]

方法未使用注解设置，但是手动设置，分别使用 second 和 first
当前类设置数据源：first
[{"age":1,"id":1,"name":"数据源2"},{"age":1,"id":1,"name":"数据源1"}]
```
