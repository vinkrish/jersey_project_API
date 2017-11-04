package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.gallery.AlbumImage;
import com.aanglearning.service.DatabaseUtil;

public class AlbumImageService {
Connection connection;
	
	public AlbumImageService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void add(List<AlbumImage> albumImages) {
		String query = "insert into album_image(Name, AlbumId, CreatedBy, CreatorName, CreatorRole, CreatedAt) "
				+ "values (?,?,?,?,?,?)";
		for(AlbumImage albumImage: albumImages) {
			try{
			    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setString(1, albumImage.getName());
		    	preparedStatement.setLong(2, albumImage.getAlbumId());
		    	preparedStatement.setLong(3, albumImage.getCreatedBy());
		    	preparedStatement.setString(4, albumImage.getCreatorName());
		    	preparedStatement.setString(5, albumImage.getCreatorRole());
		    	preparedStatement.setLong(6, albumImage.getCreatedAt());
		    	preparedStatement.executeUpdate();
			} catch(Exception e) {
			    e.printStackTrace();
			}
		}
	}
	
	public List<AlbumImage> getAlbumImages(long albumId) {
		String query = "select * from album_image where AlbumId = " + albumId;
		return getAlbumImages(query);
	}
	
	public List<AlbumImage> getAlbumImagesAboveId(long albumId, long id) {
		String query = "select * from album_image where AlbumId = " + albumId + " and Id > " + id;
		return getAlbumImages(query);
	}
	
	private List<AlbumImage> getAlbumImages(String query) {
		List<AlbumImage> albumImages = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			while (rs.next()){
				AlbumImage albumImage = new AlbumImage();
				albumImage.setId(rs.getLong("Id"));
				albumImage.setName(rs.getString("Name"));
				albumImage.setAlbumId(rs.getLong("AlbumId"));
				albumImage.setCreatedBy(rs.getLong("CreatedBy"));
				albumImage.setCreatorName(rs.getString("CreatorName"));
				albumImage.setCreatorRole(rs.getString("CreatorRole"));
				albumImage.setCreatedAt(rs.getLong("CreatedAt"));
				albumImages.add(albumImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return albumImages;
	}
}
