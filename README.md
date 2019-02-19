# Example Back-end Server Project For Android Client

## Dependencies

- Spring Boot 2.1.2
> spring-boot-starter-data-jpa<br>
> spring-boot-starter-security (csrf disabled)<br>
> spring-boot-starter-web (tomcat9 embed)<br>

<p>

- DB
> mysql-connector-java<br>

<p>

- Test
> spring-boot-starter-test<br>
> spring-security-test<br>

<p>

- Optional
> lombok<br>

<p>

- For Android
> gson (parse json text)<br>

<p>

## application.properties

```yaml
spring.datasource.url=jdbc:mysql://localhost:3306/ils?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=csedbadmin
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.current_session_context_class=org.springframework.orm.hibernate5.SpringSessionContext

server.port=80

### SSL settings ###
#server.ssl.enabled=true
#server.ssl.key-store=/home/ec2-user/ils_web/ils_web.jks
#server.ssl.key-store-password=csedbadmin
#server.ssl.key-password=csedbadmin
#server.ssl.key-alias=ils_web
#server.ssl.trust-store=/home/ec2-user/ils_web/ils_web.ts
#server.ssl.trust-store-password=csedbadmin
```

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.2.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>kr.ac.hansung</groupId>
	<artifactId>IndoorLocationServiceServer</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<name>IndoorLocationServiceServer</name>
	<description>Spring Boot Back-end for Android Client</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- preference dependency -->	
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
		</dependency>
	
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<!-- preference dependency -->

	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## View
- Main Page showing location data saved on Android Client App (ILS).<br>
![main](https://postfiles.pstatic.net/MjAxOTAyMThfMjUy/MDAxNTUwNDcxMzA5OTgw.XEy2Gh1hLPLHaHJeeKk0sCOMAyzUMVR5ifixKIP1KoYg.llECQ1z-fNFHSMawSzJ_rSXRGPbyJPZ2_7VdLkxb6vQg.PNG.dragon20002/%EA%B7%B8%EB%A6%BC7.png?type=w580)

- Each row has Wi-Fi AP data nearby the location.<br>
![large](https://postfiles.pstatic.net/MjAxOTAyMThfMTQx/MDAxNTUwNDcxMzA5OTc5.nAsy8J-Lg9csWvHK44XCF57MUoJb0wHQBHcCl3SE8dsg.ZhdDBAnNY9aFKux6Ic5derh5euurWS3UAYeSS0OkurQg.PNG.dragon20002/%EA%B7%B8%EB%A6%BC8.png?type=w580)
