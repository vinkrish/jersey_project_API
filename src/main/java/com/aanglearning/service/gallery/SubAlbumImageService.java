package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.gallery.SubAlbumImage;
import com.aanglearning.service.DatabaseUtil;

public class SubAlbumImageService {
Connection connection;
	
	public SubAlbumImageService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public SubAlbumImage add(SubAlbumImage subAlbumImage) {
		String query = "insert into subalbum_image(Name, SubAlbumId, CreatedBy, CreatorName, CreatorRole, CreatedAt) "
				+ "values (?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, subAlbumImage.getName());
	    	preparedStatement.setLong(2, subAlbumImage.getSubAlbumId());
	    	preparedStatement.setLong(3, subAlbumImage.getCreatedBy());
	    	preparedStatement.setString(4, subAlbumImage.getCreatorName());
	    	preparedStatement.setString(5, subAlbumImage.getCreatorRole());
	    	preparedStatement.setLong(6, subAlbumImage.getCreatedAt());
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
	
	public List<SubAlbumImage> getAlbumImages(long subAlbumId) {
		String query = "select * from subalbum_image where SubAlbumId = " + subAlbumId;
		return getSubAlbumImages(query);
	}
	
	public List<SubAlbumImage> getAlbumImagesAboveId(long subAlbumId, long id) {
		String query = "select * from subalbum_image where SubAlbumId = " + subAlbumId + " and Id > " + id;
		return getSubAlbumImages(query);
	}
	
	private List<SubAlbumImage> getSubAlbumImages(String query) {
		List<SubAlbumImage> subAlbumImages = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			while (rs.next()){
				SubAlbumImage subAlbumImage = new SubAlbumImage();
				subAlbumImage.setId(rs.getLong("Id"));
				subAlbumImage.setName(rs.getString("Name"));
				subAlbumImage.setSubAlbumId(rs.getLong("SubAlbumId"));
				subAlbumImage.setCreatedBy(rs.getLong("CreatedBy"));
				subAlbumImage.setCreatorName(rs.getString("CreatorName"));
				subAlbumImage.setCreatorRole(rs.getString("CreatorRole"));
				subAlbumImage.setCreatedAt(rs.getLong("CreatedAt"));
				subAlbumImages.add(subAlbumImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subAlbumImages;
	}
}
