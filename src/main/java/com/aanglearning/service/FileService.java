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
import java.util.HashMap;
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
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.aanglearning.model.entity.Attendance;
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
		LocalDate localDate = new LocalDate(homeworkDate);
		LocalDate firstDate = localDate.withDayOfWeek(DateTimeConstants.MONDAY);
		LocalDate lastDate = localDate.withDayOfWeek(DateTimeConstants.SUNDAY);
		
		//File dir = new File("C:" + File.separator + "file");
		File dir = new File("/home/ec2-user/efs");
		File file = new File(dir, 
				className+"-"+sectionName+","+getFormattedDate(firstDate.toString())+"#"+getFormattedDate(lastDate.toString())+".xlsx");
		if(file.exists()) {
			file.delete();
		}
		
		long classId = 0;
		String query = "select ClassId from section where Id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, sectionId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				classId = rs.getLong("ClassId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "select SubjectName from subject where Id in "
				+ "(select Id from subject_group_subject where SubjectGroupId in "
				+ "(select SubjectGroupId from class_subject_group where ClassId = ?));";
		List<String> subjects = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, classId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				subjects.add(rs.getString("SubjectName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Homework");
		
		int rowNum = 0;
        Row roww = sheet.createRow(rowNum++);
        int colNum = 1;
        for (String subject: subjects){
			Cell cel = roww.createCell(colNum++);
			cel.setCellValue(subject);
		}
        
        
        for (LocalDate date = firstDate; date.isBefore(lastDate); date = date.plusDays(1)) {
        	HashMap<String, String> homeworkMap = new HashMap<>();
        	String homeworkQuery = "select * from homework where SectionId = ? and HomeworkDate = ?";
    		try {
    			PreparedStatement preparedStatement = connection.prepareStatement(homeworkQuery);
    			preparedStatement.setLong(1, sectionId);
    			preparedStatement.setString(2, date.toString());
    			ResultSet rs = preparedStatement.executeQuery();
    			while (rs.next()) {
    				homeworkMap.put(rs.getString("SubjectName"), rs.getString("HomeworkMessage"));
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    		Row row = sheet.createRow(rowNum++);
			colNum = 0;
			Cell cell = row.createCell(colNum++);
			cell.setCellValue((String) getFormattedDate(date.toString()));
			for (String subject: subjects){
				Cell cel = row.createCell(colNum++);
				if(homeworkMap.containsKey(subject)) {
					cel.setCellValue(homeworkMap.get(subject));
				} else {
					cel.setCellValue("-");
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
        responseBuilder.header("Content-Disposition", 
        		"attachment; filename=\""+ className+"-"+sectionName+","+getFormattedDate(firstDate.toString())+"#"+getFormattedDate(lastDate.toString()) +".xlsx" + "\"");
        return responseBuilder.build();
	}
	
	private String getDate(String dateString) {
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
	
	private String getFormattedDate(String dateString) {
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
	
	private String getMonth(String dateString) {
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
