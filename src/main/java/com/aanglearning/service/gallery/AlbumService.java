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

import com.aanglearning.model.gallery.Album;
import com.aanglearning.service.DatabaseUtil;

public class AlbumService {
	
Connection connection;
	
	public AlbumService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Album add(Album album) {
		String query = "insert into album(Name, CoverPic, CreatedBy, CreatorName, CreatorRole, CreatedAt, SchoolId) "
				+ "values (?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, album.getName());
	    	preparedStatement.setString(2, album.getCoverPic());
	    	preparedStatement.setLong(3, album.getCreatedBy());
	    	preparedStatement.setString(4, album.getCreatorName());
	    	preparedStatement.setString(5, album.getCreatorRole());
	    	preparedStatement.setLong(6, album.getCreatedAt());
	    	preparedStatement.setLong(7, album.getSchoolId());
	    	preparedStatement.executeUpdate();
	    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			album.setId(pk);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return album;
	}
	
	public Album addNew(Album album) {
		String query = "insert into album(Name, CoverPic, CreatedBy, CreatorName, CreatorRole, CreatedAt, SchoolId, ClassId, SectionId) "
				+ "values (?,?,?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, album.getName());
	    	preparedStatement.setString(2, album.getCoverPic());
	    	preparedStatement.setLong(3, album.getCreatedBy());
	    	preparedStatement.setString(4, album.getCreatorName());
	    	preparedStatement.setString(5, album.getCreatorRole());
	    	preparedStatement.setLong(6, album.getCreatedAt());
	    	preparedStatement.setLong(7, album.getSchoolId());
	    	preparedStatement.setLong(8, album.getClassId());
	    	preparedStatement.setLong(9, album.getSectionId());
	    	preparedStatement.executeUpdate();
	    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			album.setId(pk);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return album;
	}
	
	public List<Album> getAlbums(long schoolId) {
		String query = "select * from album where SchoolId = " + schoolId + " order by Id asc";
		return getAlbums(query);
	}
	
	public List<Album> getAlbumsAboveId(long schoolId, long albumId) {
		String query = "select * from album where SchoolId = " + schoolId + " and Id > " + albumId + " order by Id asc";
		return getAlbums(query);
	}
	
	public List<Album> getAllAlbums(long schoolId) {
		String query1 = "select * from album where SchoolId = " + schoolId + " order by Id asc";
		String query2 = "select * from album where ClassId in (select Id from class where SchoolId="+ schoolId + ") order by Id asc";
		String query3 = "select * from album where SectionId in (select Id from section where ClassId in "
				+ "(select Id from class where SchoolId="+ schoolId + ")) order by Id asc";
		Set<Album> set = new HashSet<Album>(getAlbums(query1));
		set.addAll(getAlbums(query2));
		set.addAll(getAlbums(query3));
		return new ArrayList<Album>(set);
	}
	
	public List<Album> getAllAlbumsAboveId(long schoolId, long albumId) {
		String query1 = "select * from album where SchoolId = " + schoolId + " and Id > " + albumId + " order by Id asc";
		String query2 = "select * from album where ClassId in (select Id from class where SchoolId="+ schoolId + ") and Id > " + albumId + " order by Id asc";
		String query3 = "select * from album where SectionId in (select Id from section where ClassId in "
				+ "(select Id from class where SchoolId="+ schoolId + ")) and Id > " + albumId + " order by Id asc";
		Set<Album> set = new HashSet<Album>(getAlbums(query1));
		set.addAll(getAlbums(query2));
		set.addAll(getAlbums(query3));
		return new ArrayList<Album>(set);
	}
	
	public List<Album> getTeacherAlbums(long schoolId, long teacherId) {
		String query1 = "select * from album where SchoolId = " + schoolId + " order by Id asc";
		String query2 = "select * from album where ClassId in (select ClassId from section where Id in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId)) order by Id asc";
		String query3 = "select * from album where SectionId in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId) order by Id asc";
		Set<Album> set = new HashSet<Album>(getAlbums(query1));
		set.addAll(getAlbums(query2));
		set.addAll(getAlbums(query3));
		return new ArrayList<Album>(set);
	}
	
	public List<Album> getTeacherAlbumsAboveId(long schoolId, long teacherId, long albumId) {
		String query1 = "select * from album where SchoolId = " + schoolId + " and Id > " + albumId + " order by Id asc";
		String query2 = "select * from album where ClassId in (select ClassId from section where Id in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId)) and Id > " + albumId + " order by Id asc";
		String query3 = "select * from album where SectionId in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId) and Id > " + albumId + " order by Id asc";
		Set<Album> set = new HashSet<Album>(getAlbums(query1));
		set.addAll(getAlbums(query2));
		set.addAll(getAlbums(query3));
		return new ArrayList<Album>(set);
	}
	
	public List<Album> getStudentAlbums(long schoolId, long classId, long sectionId) {
		String query1 = "select * from album where SchoolId = " + schoolId + " order by Id asc";
		String query2 = "select * from album where ClassId = " + classId + " order by Id asc";
		String query3 = "select * from album where SectionId = " + sectionId + " order by Id asc";
		Set<Album> set = new HashSet<Album>(getAlbums(query1));
		set.addAll(getAlbums(query2));
		set.addAll(getAlbums(query3));
		return new ArrayList<Album>(set);
	}
	
	public List<Album> getStudentAlbumsAboveId(long schoolId, long classId, long sectionId, long albumId) {
		String query1 = "select * from album where SchoolId = " + schoolId + " and Id > " + albumId + " order by Id asc";
		String query2 = "select * from album where ClassId = " + classId + " and Id > " + albumId + " order by Id asc";
		String query3 = "select * from album where SectionId = " + sectionId + " and Id > " + albumId + " order by Id asc";
		Set<Album> set = new HashSet<Album>(getAlbums(query1));
		set.addAll(getAlbums(query2));
		set.addAll(getAlbums(query3));
		return new ArrayList<Album>(set);
	}
	
	private List<Album> getAlbums(String query) {
		List<Album> albums = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			while (rs.next()){
				Album album = new Album();
				album.setId(rs.getLong("Id"));
				album.setName(rs.getString("Name"));
				album.setCoverPic(rs.getString("CoverPic"));
				album.setCreatedBy(rs.getLong("CreatedBy"));
				album.setCreatorName(rs.getString("CreatorName"));
				album.setCreatorRole(rs.getString("CreatorRole"));
				album.setCreatedAt(rs.getLong("CreatedAt"));
				album.setSchoolId(rs.getLong("SchoolId"));
				album.setClassId(rs.getLong("ClassId"));
				album.setSectionId(rs.getLong("SectionId"));
				albums.add(album);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return albums;
	}
	
	public Album getAlbum(long id) {
		Album album = new Album();
		try {
			ResultSet rs = connection.createStatement().executeQuery("select * from album where Id = " + id);
			while (rs.next()){
				album.setId(rs.getLong("Id"));
				album.setName(rs.getString("Name"));
				album.setCoverPic(rs.getString("CoverPic"));
				album.setCreatedBy(rs.getLong("CreatedBy"));
				album.setCreatorName(rs.getString("CreatorName"));
				album.setCreatorRole(rs.getString("CreatorRole"));
				album.setCreatedAt(rs.getLong("CreatedAt"));
				album.setSchoolId(rs.getLong("SchoolId"));
				album.setClassId(rs.getLong("ClassId"));
				album.setSectionId(rs.getLong("SectionId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return album;
	}
	
	public void updateAlbum(Album album) {
		String query = "update album set CoverPic = ? where Id = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, album.getCoverPic());
	    	preparedStatement.setLong(2, album.getId());
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

}
