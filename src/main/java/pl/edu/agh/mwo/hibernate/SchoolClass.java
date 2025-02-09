package pl.edu.agh.mwo.hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schoolClasses")
public class SchoolClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private int startYear;
    
    @Column
    private int currentYear;
    
    @Column
    private String profile;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "classes", cascade = {CascadeType.ALL})
    private Set<Teacher> teachers = new HashSet<>();

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public int getStartYear() {
        return startYear;
    }
    
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
    
    public int getCurrentYear() {
        return currentYear;
    }
    
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
    
    public String getProfile() {
        return profile;
    }
    
    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    public Set<Student> getStudents() {
        return students;
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void removeStudent(Student student) {
        students.remove(student);
    }
    
    @Override
    public String toString() {
        return "SchoolClass: " + profile + " (start year: " + startYear + ", current year: " + currentYear + ")";
    }
}