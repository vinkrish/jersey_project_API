package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aanglearning.model.gallery.DeletedAlbum;
import com.aanglearning.service.DatabaseUtil;

public class DeletedAlbumService {
	Connection connection;

	public DeletedAlbumService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DeletedAlbum add(DeletedAlbum deletedAlbum) {
		delete(deletedAlbum.getAlbumId());
		String query = "insert into deleted_album(SenderId, AlbumId, SchoolId, DeletedAt) "
				+ "values (?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, deletedAlbum.getSenderId());
	    	preparedStatement.setLong(2, deletedAlbum.getAlbumId());
	    	preparedStatement.setLong(3, deletedAlbum.getSchoolId());
	    	preparedStatement.setLong(4, deletedAlbum.getDeletedAt());
	    	preparedStatement.executeUpdate();
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			deletedAlbum.setId(pk);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return deletedAlbum;
	}
	
	public DeletedAlbum addNew(DeletedAlbum deletedAlbum) {
		delete(deletedAlbum.getAlbumId());
		String query = "insert into deleted_album(SenderId, AlbumId, SchoolId, ClassId, SectionId, DeletedAt) "
				+ "values (?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, deletedAlbum.getSenderId());
	    	preparedStatement.setLong(2, deletedAlbum.getAlbumId());
	    	preparedStatement.setLong(3, deletedAlbum.getSchoolId());
	    	preparedStatement.setLong(4, deletedAlbum.getClassId());
	    	preparedStatement.setLong(5, deletedAlbum.getSectionId());
	    	preparedStatement.setLong(6, deletedAlbum.getDeletedAt());
	    	preparedStatement.executeUpdate();
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			deletedAlbum.setId(pk);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return deletedAlbum;
	}
	
	public List<DeletedAlbum> getDeletedAlbums(long schoolId) {
		String query = "select * from deleted_album where SchoolId=" + schoolId +" order by Id desc";
		return getDeletedAlbums(query);
	}
	
	public List<DeletedAlbum> getDeletedAlbumsAboveId(long schoolId, long id) {
		String query = "select * from deleted_album where SchoolId=" + schoolId +" and Id>" + id;
		return getDeletedAlbums(query);
	}
	
	public List<DeletedAlbum> getAllDelAlb(long schoolId) {
		String query1 = "select * from deleted_album where SchoolId = " + schoolId;
		String query2 = "select * from deleted_album where ClassId in (select Id from class where SchoolId="+ schoolId + ")";
		String query3 = "select * from deleted_album where SectionId in (select Id from section where ClassId in "
				+ "(select Id from class where SchoolId="+ schoolId + "))";
		Set<DeletedAlbum> set = new HashSet<DeletedAlbum>(getDeletedAlbums(query1));
		set.addAll(getDeletedAlbums(query2));
		set.addAll(getDeletedAlbums(query3));
		return new ArrayList<DeletedAlbum>(set);
	}
	
	public List<DeletedAlbum> getAllDelAlbAboveId(long schoolId, long albumId) {
		String query1 = "select * from deleted_album where SchoolId = " + schoolId + " and Id > " + albumId;
		String query2 = "select * from deleted_album where ClassId in (select Id from class where SchoolId="+ schoolId + ") and Id > " + albumId;
		String query3 = "select * from deleted_album where SectionId in (select Id from section where ClassId in "
				+ "(select Id from class where SchoolId="+ schoolId + ")) and Id > " + albumId;
		Set<DeletedAlbum> set = new HashSet<DeletedAlbum>(getDeletedAlbums(query1));
		set.addAll(getDeletedAlbums(query2));
		set.addAll(getDeletedAlbums(query3));
		return new ArrayList<DeletedAlbum>(set);
	}
	
	public List<DeletedAlbum> getTeacherDelAlb(long schoolId, long teacherId) {
		String query1 = "select * from deleted_album where SchoolId = " + schoolId;
		String query2 = "select * from deleted_album where ClassId in (select ClassId from section where Id in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId))";
		String query3 = "select * from deleted_album where SectionId in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId)";
		Set<DeletedAlbum> set = new HashSet<DeletedAlbum>(getDeletedAlbums(query1));
		set.addAll(getDeletedAlbums(query2));
		set.addAll(getDeletedAlbums(query3));
		return new ArrayList<DeletedAlbum>(set);
	}
	
	public List<DeletedAlbum> getTeacherDelAlbAboveId(long schoolId, long teacherId, long albumId) {
		String query1 = "select * from deleted_album where SchoolId = " + schoolId + " and Id > " + albumId;
		String query2 = "select * from deleted_album where ClassId in (select ClassId from section where Id in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId)) and Id > " + albumId;
		String query3 = "select * from deleted_album where SectionId in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId) and Id > " + albumId;
		Set<DeletedAlbum> set = new HashSet<DeletedAlbum>(getDeletedAlbums(query1));
		set.addAll(getDeletedAlbums(query2));
		set.addAll(getDeletedAlbums(query3));
		return new ArrayList<DeletedAlbum>(set);
	}
	
	public List<DeletedAlbum> getStudDelAlb(long schoolId, long classId, long sectionId) {
		String query1 = "select * from deleted_album where SchoolId = " + schoolId;
		String query2 = "select * from deleted_album where ClassId = " + classId;
		String query3 = "select * from deleted_album where SectionId = " + sectionId;
		Set<DeletedAlbum> set = new HashSet<DeletedAlbum>(getDeletedAlbums(query1));
		set.addAll(getDeletedAlbums(query2));
		set.addAll(getDeletedAlbums(query3));
		return new ArrayList<DeletedAlbum>(set);
	}
	
	public List<DeletedAlbum> getStudDelAlbAboveId(long schoolId, long classId, long sectionId, long albumId) {
		String query1 = "select * from deleted_album where SchoolId = " + schoolId + " and Id > " + albumId;
		String query2 = "select * from deleted_album where ClassId = " + classId + " and Id > " + albumId;
		String query3 = "select * from deleted_album where SectionId = " + sectionId + " and Id > " + albumId;
		Set<DeletedAlbum> set = new HashSet<DeletedAlbum>(getDeletedAlbums(query1));
		set.addAll(getDeletedAlbums(query2));
		set.addAll(getDeletedAlbums(query3));
		return new ArrayList<DeletedAlbum>(set);
	}
	
	private List<DeletedAlbum> getDeletedAlbums(String query) {
		List<DeletedAlbum> deletedAlbums = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			while (rs.next()){
				DeletedAlbum deletedAlbum = new DeletedAlbum();
				deletedAlbum.setId(rs.getLong("Id"));
				deletedAlbum.setSenderId(rs.getLong("SenderId"));
				deletedAlbum.setAlbumId(rs.getLong("AlbumId"));
				deletedAlbum.setSchoolId(rs.getLong("SchoolId"));
				deletedAlbum.setClassId(rs.getLong("ClassId"));
				deletedAlbum.setSectionId(rs.getLong("SectionId"));
				deletedAlbum.setDeletedAt(rs.getLong("DeletedAt"));
				deletedAlbums.add(deletedAlbum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deletedAlbums;
	}
	
	private void delete(long id) {
		String query = "delete from album where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
