package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.gallery.DeletedAlbumImage;
import com.aanglearning.service.DatabaseUtil;

public class DeletedAlbumImageService {
	Connection connection;

	public DeletedAlbumImageService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void add(List<DeletedAlbumImage> albumImages) {
		delete(albumImages);
		
		String query = "insert into deleted_album_image(SenderId, AlbumId, AlbumImageId, DeletedAt) "
				+ "values (?,?,?,?)";
		for(DeletedAlbumImage albumImage: albumImages) {
			try{
			    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, albumImage.getSenderId());
		    	preparedStatement.setLong(2, albumImage.getAlbumId());
		    	preparedStatement.setLong(3, albumImage.getAlbumImageId());
		    	preparedStatement.setLong(4, albumImage.getDeletedAt());
		    	preparedStatement.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<DeletedAlbumImage> getDeletedAlbumImages(long albumId) {
		String query = "select * from deleted_album_image where AlbumId=? order by Id desc";
		List<DeletedAlbumImage> albumImages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, albumId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedAlbumImage albumImage = new DeletedAlbumImage();
				albumImage.setId(rs.getLong("Id"));
				albumImage.setSenderId(rs.getLong("SenderId"));
				albumImage.setAlbumId(rs.getLong("AlbumId"));
				albumImage.setAlbumImageId(rs.getLong("AlbumImageId"));
				albumImage.setDeletedAt(rs.getLong("DeletedAt"));
				albumImages.add(albumImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return albumImages;
	}
	
	public List<DeletedAlbumImage> getDeletedAlbumImagesAboveId(long albumId, long id) {
		String query = "select * from deleted_album_image where AlbumId=? and Id>?";
		List<DeletedAlbumImage> albumImages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, albumId);
			preparedStatement.setLong(2, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedAlbumImage albumImage = new DeletedAlbumImage();
				albumImage.setId(rs.getLong("Id"));
				albumImage.setSenderId(rs.getLong("SenderId"));
				albumImage.setAlbumId(rs.getLong("AlbumId"));
				albumImage.setAlbumImageId(rs.getLong("AlbumImageId"));
				albumImage.setDeletedAt(rs.getLong("DeletedAt"));
				albumImages.add(albumImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return albumImages;
	}
	
	private void delete(List<DeletedAlbumImage> albumImages) {
		String query = "delete from album_image where Id=?";
		for(DeletedAlbumImage albumImage: albumImages) {
			try{
			    PreparedStatement preparedStatement = connection.prepareStatement(query);
			    preparedStatement.setLong(1, albumImage.getAlbumImageId());
			    preparedStatement.executeUpdate();
			} catch(Exception e) {
			    e.printStackTrace();
			}
		}
	}
}
