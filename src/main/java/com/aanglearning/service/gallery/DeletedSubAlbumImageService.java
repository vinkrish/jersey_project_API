package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.gallery.DeletedSubAlbumImage;
import com.aanglearning.service.DatabaseUtil;

public class DeletedSubAlbumImageService {
	Connection connection;

	public DeletedSubAlbumImageService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void add(List<DeletedSubAlbumImage> subAlbumImages) {
		delete(subAlbumImages);
		
		String query = "insert into deleted_subalbum_image(SenderId, SubAlbumImageId, Name, SubAlbumId, DeletedAt) "
				+ "values (?,?,?,?,?)";
		for(DeletedSubAlbumImage subAlbumImage: subAlbumImages) {
			try{
			    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, subAlbumImage.getSenderId());
		    	preparedStatement.setLong(2, subAlbumImage.getSubAlbumImageId());
		    	preparedStatement.setString(3, subAlbumImage.getName());
		    	preparedStatement.setLong(4, subAlbumImage.getSubAlbumId());
		    	preparedStatement.setLong(5, subAlbumImage.getDeletedAt());
		    	preparedStatement.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<DeletedSubAlbumImage> getDeletedSubAlbumImages(long subAlbumId) {
		String query = "select * from deleted_subalbum_image where SubAlbumId=? order by Id desc";
		List<DeletedSubAlbumImage> subAlbumImages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, subAlbumId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedSubAlbumImage subAlbumImage = new DeletedSubAlbumImage();
				subAlbumImage.setId(rs.getLong("Id"));
				subAlbumImage.setSenderId(rs.getLong("SenderId"));
				subAlbumImage.setSubAlbumId(rs.getLong("SubAlbumId"));
				subAlbumImage.setSubAlbumImageId(rs.getLong("SubAlbumImageId"));
				subAlbumImage.setName(rs.getString("Name"));
				subAlbumImage.setDeletedAt(rs.getLong("DeletedAt"));
				subAlbumImages.add(subAlbumImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subAlbumImages;
	}
	
	public List<DeletedSubAlbumImage> getDeletedSubAlbumImagesAboveId(long subAlbumId, long id) {
		String query = "select * from deleted_subalbum_image where SubAlbumId=? and Id>?";
		List<DeletedSubAlbumImage> subAlbumImages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, subAlbumId);
			preparedStatement.setLong(2, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedSubAlbumImage subAlbumImage = new DeletedSubAlbumImage();
				subAlbumImage.setId(rs.getLong("Id"));
				subAlbumImage.setSenderId(rs.getLong("SenderId"));
				subAlbumImage.setSubAlbumId(rs.getLong("SubAlbumId"));
				subAlbumImage.setSubAlbumImageId(rs.getLong("SubAlbumImageId"));
				subAlbumImage.setName(rs.getString("Name"));
				subAlbumImage.setDeletedAt(rs.getLong("DeletedAt"));
				subAlbumImages.add(subAlbumImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subAlbumImages;
	}
	
	private void delete(List<DeletedSubAlbumImage> subAlbumImages) {
		String query = "delete from subalbum_image where Id=?";
		for(DeletedSubAlbumImage subAlbumImage: subAlbumImages) {
			try{
			    PreparedStatement preparedStatement = connection.prepareStatement(query);
			    preparedStatement.setLong(1, subAlbumImage.getSubAlbumImageId());
			    preparedStatement.executeUpdate();
			} catch(Exception e) {
			    e.printStackTrace();
			}
		}
	}
}
