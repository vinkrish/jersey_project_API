package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		String query = "insert into album(Name, CreatedBy, CreatorName, CreatorRole, CreatedAt, SchoolId) "
				+ "values (?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, album.getName());
	    	preparedStatement.setLong(2, album.getCreatedBy());
	    	preparedStatement.setString(3, album.getCreatorName());
	    	preparedStatement.setString(4, album.getCreatorRole());
	    	preparedStatement.setLong(5, album.getCreatedAt());
	    	preparedStatement.setLong(6, album.getSchoolId());
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
		String query = "select * from album where SchoolId = " + schoolId;
		return getAlbums(query);
	}
	
	public List<Album> getAlbumsAboveId(long schoolId, long albumId) {
		String query = "select * from album where SchoolId = " + schoolId + " and Id > " + albumId;
		return getAlbums(query);
	}
	
	private List<Album> getAlbums(String query) {
		List<Album> albums = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			while (rs.next()){
				Album album = new Album();
				album.setId(rs.getLong("Id"));
				album.setName(rs.getString("Name"));
				album.setCreatedBy(rs.getLong("CreatedBy"));
				album.setCreatorName(rs.getString("CreatorName"));
				album.setCreatorRole(rs.getString("CreatorRole"));
				album.setCreatedAt(rs.getLong("CreatedAt"));
				album.setSchoolId(rs.getLong("SchoolId"));
				albums.add(album);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return albums;
	}

}
