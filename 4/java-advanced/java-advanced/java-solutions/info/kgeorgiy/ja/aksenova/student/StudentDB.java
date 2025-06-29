package info.kgeorgiy.ja.aksenova.student;

import info.kgeorgiy.java.advanced.student.GroupName;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentQuery;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {
    private final Comparator<Student> comp = Comparator.comparing(Student::firstName)
            .thenComparing(Student::lastName)
            .thenComparing(Student::id);

    private <T> List<T> getStudents(List<Student> students, Function<Student, T> function) {
        return students.stream()
                .map(function)
                .toList();
    }

    private <T extends Comparable<T>> List<Student> sortStudents(Collection<Student> students, Function<Student, T> function) {
        return students.stream()
                .sorted(Comparator.comparing(function)
                        .thenComparing(comp))
                .toList();
    }

    private <T> List<Student> findStudents(Collection<Student> students, Function<Student, T> function, T toCompare) {
        return students.stream()
                .filter(s -> function.apply(s).equals(toCompare))
                .sorted(comp)
                .toList();
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getStudents(students, Student::firstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getStudents(students, Student::lastName);
    }

    @Override
    public List<GroupName> getGroupNames(List<Student> students) {
        return getStudents(students, Student::groupName);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return getStudents(students, s -> s.firstName() + " " + s.lastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return new TreeSet<>(getFirstNames(students));
    }

    @Override
    public String getMaxStudentFirstName(List<Student> students) {
        return students.stream()
                .max(Comparator.comparing(Student::id))
                .map(Student::firstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortStudents(students, Student::id);
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortStudents(students, Student::firstName);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return findStudents(students, Student::firstName, name);
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return findStudents(students, Student::lastName, name);
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, GroupName group) {
        return findStudents(students, Student::groupName, group);
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, GroupName group) {
        return findStudentsByGroup(students, group).stream()
                .collect(Collectors.toMap(Student::lastName, Student::firstName, BinaryOperator.minBy(Comparator.naturalOrder())));
    }
}
