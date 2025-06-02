import java.util.List;

public class JdbcOperationsExample {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();

        // Insert new student
        System.out.println("--- Inserting New Student ---");
        Student newStudent = new Student("Eve", 19);
        studentDAO.insertStudent(newStudent);
        displayAllStudents(studentDAO);

        // Update an existing student (assuming ID 1 exists)
        System.out.println("\n--- Updating Student with ID 1 ---");
        Student existingStudent = new Student(1, "Alice Smith", 21);
        studentDAO.updateStudent(existingStudent);
        displayAllStudents(studentDAO);

        // Update a student that might not exist
        System.out.println("\n--- Attempting to Update Non-existent Student (ID 99) ---");
        Student nonExistentStudent = new Student(99, "Non Existent", 30);
        studentDAO.updateStudent(nonExistentStudent);
        displayAllStudents(studentDAO);
    }

    private static void displayAllStudents(StudentDAO studentDAO) {
        System.out.println("\n--- Current Students in DB ---");
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }
}