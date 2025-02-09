package pl.edu.agh.mwo.hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String pesel;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "schoolClasses_teachers",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "schoolClass_id")
    )
    Set<SchoolClass> classes = new HashSet<>();


    public Set<SchoolClass> getClasses() {
        return classes;
    }

    public void addClass(SchoolClass schoolClass) {
        classes.add(schoolClass);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return "Teacher: " + name + " " + surname + " (" + pesel + ")";
    }
}
