package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.gallery.SubAlbum;
import com.aanglearning.service.DatabaseUtil;

public class SubAlbumService {
Connection connection;
	
	public SubAlbumService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public SubAlbum add(SubAlbum subAlbum) {
		String query = "insert into sub_album(Name, CoverPic, AlbumId, CreatedBy, CreatorName, CreatorRole, CreatedAt) "
				+ "values (?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, subAlbum.getName());
	    	preparedStatement.setString(2, subAlbum.getCoverPic());
	    	preparedStatement.setLong(3, subAlbum.getAlbumId());
	    	preparedStatement.setLong(4, subAlbum.getCreatedBy());
	    	preparedStatement.setString(5, subAlbum.getCreatorName());
	    	preparedStatement.setString(6, subAlbum.getCreatorRole());
	    	preparedStatement.setLong(7, subAlbum.getCreatedAt());
	    	preparedStatement.executeUpdate();
	    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			subAlbum.setId(pk);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return subAlbum;
	}
	
	public List<SubAlbum> getSubAlbums(long albumId) {
		String query = "select * from sub_album where AlbumId = " + albumId;
		return getSubAlbums(query);
	}
	
	public List<SubAlbum> getSubAlbumsAboveId(long albumId, long subAlbumId) {
		String query = "select * from sub_album where AlbumId = " + albumId + " and Id > " + subAlbumId;
		return getSubAlbums(query);
	}
	
	private List<SubAlbum> getSubAlbums(String query) {
		List<SubAlbum> subAlbums = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			while (rs.next()){
				SubAlbum subAlbum = new SubAlbum();
				subAlbum.setId(rs.getLong("Id"));
				subAlbum.setName(rs.getString("Name"));
				subAlbum.setCoverPic(rs.getString("CoverPic"));
				subAlbum.setAlbumId(rs.getLong("AlbumId"));
				subAlbum.setCreatedBy(rs.getLong("CreatedBy"));
				subAlbum.setCreatorName(rs.getString("CreatorName"));
				subAlbum.setCreatorRole(rs.getString("CreatorRole"));
				subAlbum.setCreatedAt(rs.getLong("CreatedAt"));
				subAlbums.add(subAlbum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subAlbums;
	}
	
	public void updateSubAlbum(SubAlbum subAlbum) {
		String query = "update sub_album set CoverPic = '?' where Id = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, subAlbum.getCoverPic());
	    	preparedStatement.setLong(2, subAlbum.getId());
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
