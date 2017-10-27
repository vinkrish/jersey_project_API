package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	public DeletedSubAlbumImage add(DeletedSubAlbumImage subAlbumImage) {
		delete(subAlbumImage.getSubAlbumImageId());
		
		String query = "insert into deleted_subalbum_image(SenderId, SubAlbumId, SubAlbumImageId, DeletedAt) "
				+ "values (?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, subAlbumImage.getSenderId());
	    	preparedStatement.setLong(2, subAlbumImage.getSubAlbumId());
	    	preparedStatement.setLong(3, subAlbumImage.getSubAlbumImageId());
	    	preparedStatement.setLong(4, subAlbumImage.getDeletedAt());
	    	preparedStatement.executeUpdate();
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			subAlbumImage.setId(pk);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return subAlbumImage;
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
				subAlbumImage.setDeletedAt(rs.getLong("DeletedAt"));
				subAlbumImages.add(subAlbumImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subAlbumImages;
	}
	
	private void delete(long id) {
		String query = "delete from subalbum_image where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
