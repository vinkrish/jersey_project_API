package com.aanglearning.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDate;

import com.aanglearning.model.entity.Attendance;
import com.aanglearning.model.entity.Homework;
import com.aanglearning.model.entity.Student;

public class FileService {
	Connection connection;
	
	public FileService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Response downloadAttendance(String className, String sectionName, long sectionId, String attendanceDate) {
		LocalDate localDate = new LocalDate(attendanceDate);
		String firstDate = localDate.withDayOfMonth(1).toString();
		String lastDate = localDate.dayOfMonth().withMaximumValue().toString();
		String month = getMonth(attendanceDate);
		
		File dir = new File("/home/ec2-user/efs");
		File file = new File(dir, className+"-"+sectionName+","+month+".xlsx");
		if(file.exists()) {
			file.delete();
		}
		
		String studentsQuery = "select Id, Name, RollNo from student where SectionId = ? order by RollNo";
		List<Student> studentList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(studentsQuery);
			preparedStatement.setLong(1, sectionId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Student student = new Student();
				student.setId(rs.getLong("Id"));
				student.setName(rs.getString("Name"));
				student.setRollNo(rs.getInt("RollNo"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String absenteesQuery = "select StudentId, DateAttendance from attendance "
				+ "where SectionId = ? and DateAttendance>? and DateAttendance<? "
				+ "order by DateAttendance asc";
		List<Attendance> attendanceList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(absenteesQuery);
			preparedStatement.setLong(1, sectionId);
			preparedStatement.setString(2, firstDate);
			preparedStatement.setString(3, lastDate);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Attendance attendance = new Attendance();
				attendance.setStudentId(rs.getLong("StudentId"));
				attendance.setDateAttendance(rs.getString("DateAttendance"));
				attendanceList.add(attendance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String previousDate = "";
		ArrayList<Long> studentIds = new ArrayList<>();
		LinkedHashMap<String, List<Long>> attendanceMap = new LinkedHashMap<>();  
		for(Attendance attendance: attendanceList) {
			if(previousDate.equals(attendance.getDateAttendance())) {
				studentIds.add(attendance.getStudentId());
			} else {
				if(previousDate.equals("")) {
					previousDate = attendance.getDateAttendance();
					studentIds.add(attendance.getStudentId());
				} else {
					attendanceMap.put(previousDate, studentIds);
					studentIds = new ArrayList<>();
					studentIds.add(attendance.getStudentId());
					previousDate = attendance.getDateAttendance();
				}
			}
		}
		attendanceMap.put(previousDate, studentIds);
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Attendance");
		
        int rowNum = 0;
        Row roww = sheet.createRow(rowNum++);
        int colNum = 1;
        for (Map.Entry<String, List<Long>> entry : attendanceMap.entrySet()){
			Cell cel = roww.createCell(colNum++);
			cel.setCellValue(getDate(entry.getKey()));
		}
        
		for(Student student: studentList) {
			Row row = sheet.createRow(rowNum++);
			colNum = 0;
			Cell cell = row.createCell(colNum++);
			cell.setCellValue((String) student.getName());
			for (Map.Entry<String, List<Long>> entry : attendanceMap.entrySet()){
				Cell cel = row.createCell(colNum++);
				List<Long> absentList = entry.getValue();
				if(absentList.contains(student.getId())) {
					cel.setCellValue("Absent");
				} else {
					cel.setCellValue("P");
				}
			}
		}
		
		try {
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		 
        ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\""+ className+"-"+sectionName+","+month+".xlsx" + "\"");
        return responseBuilder.build();
	}
	
	public Response downloadHomework(String className, String sectionName, long sectionId, String homeworkDate) {
		File dir = new File("/home/ec2-user/efs");
		File file = new File(dir, className+"-"+sectionName+","+getFormattedDate(homeworkDate)+".xlsx");
		if(file.exists()) {
			file.delete();
		}
		
		String query = "select * from homework where SectionId = ? and HomeworkDate = ?";
		List<Homework> hwList = new ArrayList<Homework>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, sectionId);
			preparedStatement.setString(2, homeworkDate);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Homework homework = new Homework();
				homework.setId(rs.getLong("Id"));
				homework.setSectionId(rs.getLong("SectionId"));
				homework.setSubjectId(rs.getLong("SubjectId"));
				homework.setSubjectName(rs.getString("SubjectName"));
				homework.setHomeworkMessage(rs.getString("HomeworkMessage"));
				homework.setHomeworkDate(rs.getString("HomeworkDate"));
				hwList.add(homework);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Homework");
        
        int rowNum = 0;
        Row roww = sheet.createRow(rowNum++);
        
        Cell cell0 = roww.createCell(0);
		cell0.setCellValue("Subjeect");
		Cell cell1 = roww.createCell(0);
		cell1.setCellValue("Homework");
        
		for(Homework homework: hwList) {
			Row row = sheet.createRow(rowNum++);
			Cell cel0 = row.createCell(0);
			cel0.setCellValue(homework.getSubjectName());
			Cell cel1 = row.createCell(1);
			cel1.setCellValue(homework.getHomeworkMessage());
		}
		
		try {
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\""+ className+"-"+sectionName+", "+getFormattedDate(homeworkDate)+".xlsx" + "\"");
        return responseBuilder.build();
	}
	
	public String getDate(String dateString) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
    }
	
	public String getFormattedDate(String dateString) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
    }
	
	public String getMonth(String dateString) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
    }

}
