package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Student;
import com.aanglearning.service.DatabaseUtil;

public class StudentService {
	Statement stmt;

	public StudentService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Student> getStudentSection(long sectionId) {
		String query = "select * from student where SectionId = " + sectionId + " order by RollNo";
		return getStudentList(query);
	}
	
	public List<Student> getStudentClass(long classId) {
		String query = "select * from student where ClassId = " + classId;
		return getStudentList(query);
	}
	
	public List<Student> getSectionGroupUsers (long groupId, long sectionId) {
		String query = "select * from student where Id not in "
				+ "(select UserId from user_group where Role='student' and GroupId=" + groupId + ") and SectionId = " + sectionId + " order by RollNo";
		return getStudentList(query);
	}
	
	public List<Student> getClassGroupUsers (long groupId, long classId) {
		String query = "select * from student where Id not in "
				+ "(select UserId from user_group where Role='student' and GroupId=" + groupId + ") and ClassId = " + classId;
		return getStudentList(query);
	}
	
	public List<Student> getStudentList(String query) {
		List<Student> studentList = new ArrayList<Student>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Student student = new Student();
				student.setId(rs.getLong("Id"));
				student.setStudentName(rs.getString("StudentName"));
				student.setSchoolId(rs.getLong("SchoolId"));
				student.setClassId(rs.getLong("ClassId"));
				student.setSectionId(rs.getLong("SectionId"));
				student.setAdmissionNo(rs.getString("AdmissionNo"));
				student.setRollNo(rs.getInt("RollNo"));
				student.setUsername(rs.getString("Username"));
				student.setPassword(rs.getString("Password"));
				student.setImage(rs.getString("Image"));
				student.setFatherName(rs.getString("FatherName"));
				student.setMotherName(rs.getString("MotherName"));
				student.setDateOfBirth(rs.getString("DateOfBirth"));
				student.setGender(rs.getString("Gender"));
				student.setEmail(rs.getString("Email"));
				student.setMobile1(rs.getString("Mobile1"));
				student.setMobile2(rs.getString("Mobile2"));
				student.setStreet(rs.getString("Street"));
				student.setCity(rs.getString("City"));
				student.setDistrict(rs.getString("District"));
				student.setState(rs.getString("State"));
				student.setPincode(rs.getString("Pincode"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;
	}
	
	public Student add(Student student) {
		try {
			String query = "insert into student(StudentId, StudentName, SchoolId, ClassId, SectionId, "
					+ "AdmissionNo, RollNo, Username, Password, Image, FatherName, MotherName, DateOfBirth, "
					+ "Gender, Email, Mobile1, Mobile2, Street, City, District, State, Pincode) "
					+ "values (" 
					+ student.getId() + ",'" 
					+ student.getStudentName() + "',"
					+ student.getSchoolId() + ","
					+ student.getClassId()  + ","
					+ student.getSectionId()  + ",'"
					+ student.getAdmissionNo() + "',"
					+ student.getRollNo() + ",'"
					+ student.getUsername() + "','"
					+ student.getPassword() + "','"
					+ student.getImage() + "','"
					+ student.getFatherName() + "','"
					+ student.getMotherName() + "','"
					+ student.getDateOfBirth() + "','"
					+ student.getGender() + "','"
					+ student.getEmail() + "','"
					+ student.getMobile1() + "','"
					+ student.getMobile2() + "','"
					+ student.getStreet() + "','"
					+ student.getCity() + "','"
					+ student.getDistrict() + "','"
					+ student.getState() + "','"
					+ student.getPincode() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			student.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	public void update(Student student) {
		try {
			String query = "update student set  StudentName = '" + student.getStudentName() 
			+ "', AdmissionNo = '" + student.getAdmissionNo() 
			+ "', RollNo = " + student.getRollNo() 
			+ ", Username = '" + student.getUsername() 
			+ "', Password = '" + student.getPassword() 
			+ "', FatherName = '" + student.getFatherName() 
			+ "', MotherName = '" + student.getMotherName() 
			+ "', DateOfBirth = '" + student.getDateOfBirth() 
			+ "', Gender = '" + student.getGender() 
			+ "', Email = '" + student.getEmail() 
			+ "', Mobile1 = '" + student.getMobile1() 
			+ "', Mobile2 = '" + student.getMobile2() 
			+ "', Street = '" + student.getStreet() 
			+ "', City = '" + student.getCity() 
			+ "', District = '" + student.getDistrict() 
			+ "', State = '" + student.getState() 
			+ "', Pincode = '" + student.getPincode() 
			+ "' where Id=" + student.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long studentId){
		try {
			String query = "delete from student where Id=" + studentId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
