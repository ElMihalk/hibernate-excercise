package pl.edu.agh.mwo.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class Main {

    Session session;

    public static void main(String[] args) {
        Main main = new Main();

//		main.addNewData();
//		main.printSchools();
//		main.executeQueries();
//		main.updateObject();
        //1. Dalszy rozwój
//        main.printTeachers();
//        main.printClassTeachers();
        //2. Kaskadowanie
//        main.cascadeTest();
//        main.deleteSchool();
//        main.cascadeTest_2();
        main.deleteClass();
        main.close();
    }

    public Main() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
        HibernateUtil.shutdown();
    }
    
    private void executeQueries() {
		 query0();
//		 query1();
//		 query2();
//		 query3();
//		 query4();
//		 query5();
//		 query6();
    }
    
    private void query0() {
        String hql = "from School";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        System.out.println(results);
    }
    
    private void query1() {
        String hql = "from School s where s.name='UE'";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        System.out.println(results);
    }
    
    private void query2() {
        String hql = "from School s where s.name='UE'";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (School s : results) {
            session.delete(s);
        }
        transaction.commit();
    }

    private void query3() {
        String hql = "select count(s) from School s";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long schoolsCount = query.uniqueResult();
        System.out.println("Schools count: " + schoolsCount);
    }

    private void query4() {
        String hql = "select count(s) from Student s";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long schoolsCount = query.uniqueResult();
        System.out.println("Students count: " + schoolsCount);
    }

    private void query5() {
        String hql = "select count(s) from School s where size(s.classes) >= 2";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long schoolsCount = query.uniqueResult();
        System.out.println("Schools count: " + schoolsCount);
    }

    private void query6() {
        String hql = "select s from School s inner join s.classes c where c.profile = 'mat-fiz' and c.currentYear >= 2";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        System.out.println(results);
    }

    private void updateObject() {
        Query<School> query = session.createQuery("from School where id = :id", School.class);
        query.setParameter("id", 2L);
        School school = query.uniqueResult();
        school.setAddress("Nowy adres2");

        Transaction transaction = session.beginTransaction();
        session.save(school);
        transaction.commit();
    }

    private void addNewData() {
        School newSchool = new School();
        newSchool.setName("Nowa szkoła");
        newSchool.setAddress("ul. Ulica 0, Miasto");

        SchoolClass newClass = new SchoolClass();
        newClass.setProfile("profil");
        newClass.setStartYear(2020);
        newClass.setCurrentYear(1);

        Student newStudent = new Student();
        newStudent.setName("Jan");
        newStudent.setSurname("Kowalski");
        newStudent.setPesel("12345678901");

        newClass.addStudent(newStudent);
        newSchool.addClass(newClass);

        Transaction transaction = session.beginTransaction();
        session.save(newSchool);
        transaction.commit();
    }


    private void printSchools() {
        Query<School> query = session.createQuery("from School", School.class);
        List<School> schools = query.list();

        System.out.println("### Schools");
        for (School school : schools) {
            System.out.println(school);
            System.out.println("    ### SchoolClasses");
            for (SchoolClass schoolClass : school.getClasses()) {
                System.out.println("    " + schoolClass);
                System.out.println("        ### Students");
                for (Student student : schoolClass.getStudents()) {
                    System.out.println("        " + student);
                }
            }
        }
    }

    private void printTeachers(){
        Query<Teacher> query = session.createQuery("from Teacher", Teacher.class);
        List<Teacher> teachers = query.list();

        System.out.println("### Teachers");
        for(Teacher teacher : teachers){
            System.out.println(teacher);
        }
    }

    private void printClassTeachers(){
        Query<SchoolClass> query = session.createQuery("from SchoolClass", SchoolClass.class);
        List<SchoolClass> sClasses = query.list();

        System.out.println("### Classes");
        for(SchoolClass sClass : sClasses){
            System.out.println(sClass);
            for(Teacher teacher : sClass.getTeachers()){
                System.out.print("     ");
                System.out.println(teacher);
            }

        }
    }

    private void deleteSchool(){
        String hql = "from School s where s.name='UJ'";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (School s : results) {
            session.delete(s);
        }
        transaction.commit();
    }

    private void deleteClass(){
        String hql = "from SchoolClass s where s.profile='human.' and s.startYear = 2008";
        Query<SchoolClass> query = session.createQuery(hql, SchoolClass.class);
        List<SchoolClass> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (SchoolClass s : results) {
            session.delete(s);
        }
        transaction.commit();
    }

    private void cascadeTest(){
        School school = new School();
        school.setAddress("ul. Gołębia 24");
        school.setName("UJ");

        SchoolClass newClass = new SchoolClass();
        newClass.setProfile("human.");
        newClass.setCurrentYear(3);
        newClass.setStartYear(2008);

        Student newStudent = new Student();
        newStudent.setName("Jan");
        newStudent.setSurname("Kowalski");
        newStudent.setPesel("12345678901");

        Teacher teacher = new Teacher();
        teacher.setName("Jan");
        teacher.setSurname("Nowak");
        teacher.setPesel("12345678912");

        newClass.addTeacher(teacher);
        newClass.addStudent(newStudent);
        teacher.addClass(newClass);
        school.addClass(newClass);

        Transaction transaction = session.beginTransaction();
        session.save(school);
        session.save(teacher);
        transaction.commit();

    }

    private void cascadeTest_2(){

        School school = new School();
        school.setAddress("ul. Gołębia 24");
        school.setName("UJ");

        School school1 = new School();
        school1.setAddress("ul. Podchorążych 2");
        school1.setName("UP");

        SchoolClass newClass = new SchoolClass();
        newClass.setProfile("human.");
        newClass.setCurrentYear(3);
        newClass.setStartYear(2008);

        SchoolClass newClass1 = new SchoolClass();
        newClass1.setProfile("human.");
        newClass1.setCurrentYear(2);
        newClass1.setStartYear(2009);

        Student newStudent = new Student();
        newStudent.setName("Jan");
        newStudent.setSurname("Kowalski");
        newStudent.setPesel("12345678901");

        Student newStudent1 = new Student();
        newStudent1.setName("Janusz");
        newStudent1.setSurname("Kowalski");
        newStudent1.setPesel("12345678901");

        Teacher teacher = new Teacher();
        teacher.setName("Jan");
        teacher.setSurname("Nowak");
        teacher.setPesel("12345678912");

        Teacher teacher1 = new Teacher();
        teacher1.setName("Janusz");
        teacher1.setSurname("Nowak");
        teacher1.setPesel("12345678912");



        teacher.addClass(newClass);
        teacher1.addClass(newClass1);
        teacher1.addClass(newClass);
        teacher.addClass(newClass1);

        newClass1.addTeacher(teacher);
        newClass1.addTeacher(teacher1);
        newClass.addTeacher(teacher1);
        newClass.addTeacher(teacher);

        newClass.addStudent(newStudent);
        newClass1.addStudent(newStudent1);

        school.addClass(newClass);
        school1.addClass(newClass1);

        Transaction transaction = session.beginTransaction();
        session.save(school);
        session.save(school1);
        session.save(teacher);
        session.save(teacher1);
        transaction.commit();

    }
}
