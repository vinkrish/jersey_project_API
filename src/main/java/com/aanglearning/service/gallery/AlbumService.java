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
				album.setCoverPic(rs.getString("CoverPic"));
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
	
	public void updateAlbum(Album album) {
		String query = "update album set CoverPic = '?' where Id = ?";
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
