# CCRM (Campus Course and Records Manager)

## Project Overview
The CCRM project is a *Java-based application* to manage campus courses and student records. It allows:  
- Adding and managing *students* and *courses*  
- Registering students to courses  
- Tracking *grades* and *attendance*  
- Demonstrating *OOP concepts*: inheritance, polymorphism, encapsulation  

### How to Run
- *JDK Version:* Java 17  
- *Compile:*  
```bash
javac -d out src/**/*.java
```
```bash
java -cp out Main
```
## Evolution of Java

- 1995: Java 1.0 – basic libraries, applets
- 1998: Java 1.2 – Collections framework
- 2004: Java 5 – Generics, enums, annotations
- 2011: Java 7 – try-with-resources, NIO improvements
- 2014: Java 8 – Lambda expressions, Stream API
- 2017: Java 9 – Module system
- 2021: Java 17 – LTS version

## Java ME vs SE vs EE comparison
| Feature | Java ME | Java SE | Java EE |
|:---|:---:|:---:|---:|
| Target Platform | Mobile/Embedded devices | Desktop/General purpose | Enterprise servers |
| API Scope | Limited libraries | Full Java libraries | Web services, Servlets, EJB |
| Memory Requiremnet | Low | Moderate | High |
| Use Case | IoT, mobile apps | Desktop apps |Web/Enterprise applications

## JDK / JRE / JVM Explanation

- JVM (Java Virtual Machine): Runs Java bytecode on any platform.
- JRE (Java Runtime Environment): JVM + libraries to run Java programs.
- JDK (Java Development Kit): JRE + tools to compile, debug, and package Java programs.

## Windows Installation Steps
1. Install JDK

- Download JDK from Oracle
- Install and set JAVA_HOME to C:\Program Files\Java\jdk-17
- Add %JAVA_HOME%\bin to PATH
- Verify installation:
```bash
java -version
javac -version
```

Example Screenshot:
![Java_Download_Page](https://github.com/user-attachments/assets/e2cd33fa-5a5a-40ad-b3fd-d5840e526ea8)
<img width="1024" height="1024" alt="Java_setup" src="https://github.com/user-attachments/assets/124c6474-65fc-48db-a543-34697da98aaf" />
<img width="1024" height="1024" alt="Java_Progress" src="https://github.com/user-attachments/assets/aeac79cb-2aeb-43eb-ad4b-cefbe1098799" />
<img width="1024" height="1024" alt="JDK_Installed" src="https://github.com/user-attachments/assets/17e3a472-7b61-4fc7-9b34-4b00d35259ae" />

2. Eclipse Setup

- Download Eclipse IDE for Java Developers
- Install and open Eclipse
- Set Java JDK:
   Windows -> Preferences -> Java -> Installed JREs -> Add -> Select JDK folder
- Create a new Java project and import your CCRM source files

Example Screenshot:
<img width="1024" height="1024" alt="Download_Eclipse" src="https://github.com/user-attachments/assets/dbef38d7-b0d9-486a-8395-5d6a7da87f20" />
<img width="1024" height="1024" alt="Eclipse_Installer" src="https://github.com/user-attachments/assets/5a90e90d-9c40-424a-bf37-1e7be6fbf0db" />
<img width="1024" height="1024" alt="Eclipse_Workspace_Launcher" src="https://github.com/user-attachments/assets/4404f479-bc67-4da2-88f9-1648ec4f3b0b" />
<img width="1024" height="1024" alt="Eclipse_Set_JDK" src="https://github.com/user-attachments/assets/2e929949-d005-417b-9f2e-a8d35e045ac7" />
<img width="1024" height="1024" alt="Eclipse_New_Project" src="https://github.com/user-attachments/assets/ea42d74f-f8ad-492d-a1dc-203a6875060f" />

## Mapping Table: Syllabus Topic → File/Class/Method 
| Syllabus Topic | File/Class/Method |
|:---|---:|
| Classes & Objects | src/models/Student.java |
| Inheritance | src/models/GraduateStudent.java |
| Polymorphism | src/controllers/CCRMController.java -> registerStudent() |
| Collections(ArrayList,Map) | src/data/CourseRepository.java |
| Exception Handling | src/utils/Validation.java|
| File I/O | src/utils/FileHandler.java |

## Enabling Assertions

1. Compile normally:
```bash
javac -d out src//*.java
```
2. Run with assertions enabled:
```bash
java -ea -cp out Main
```
3. Example assertion:
```java
assert student != null : "Student object must not be null";
```
### Interface vs Class Inheritance 
In this project, I deliberately used class inheritance only when there was a true “is-a” relationship and code reuse made sense. For example:

- Student and Instructor both extend the abstract class Person. This works because every student and instructor is a person and shares common fields like id, fullName, and email. Here, class inheritance avoids code duplication.
- Course does not extend Person because it is not a person; instead, it is modeled separately.

On the other hand, I used interfaces when I wanted to express capabilities or behaviors without forcing an inheritance hierarchy:

- Persistable marks classes that can be serialized to a CSV line.
- Searchable<T> defines objects that can be searched (with a matches method).
- Readable demonstrates the diamond problem when combined with Searchable, which I resolved by overriding the info() method in Course.
