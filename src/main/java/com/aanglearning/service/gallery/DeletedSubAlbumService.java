package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.gallery.DeletedSubAlbum;
import com.aanglearning.service.DatabaseUtil;

public class DeletedSubAlbumService {
	Connection connection;

	public DeletedSubAlbumService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DeletedSubAlbum add(DeletedSubAlbum subAlbum) {
		delete(subAlbum.getSubAlbumId());
		
		String query = "insert into deleted_subalbum(SenderId, AlbumId, SubAlbumId, DeletedAt) "
				+ "values (?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, subAlbum.getSenderId());
	    	preparedStatement.setLong(2, subAlbum.getAlbumId());
	    	preparedStatement.setLong(3, subAlbum.getSubAlbumId());
	    	preparedStatement.setLong(4, subAlbum.getDeletedAt());
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
	
	public List<DeletedSubAlbum> getDeletedSubAlbums(long schoolId) {
		String query = "select * from deleted_subalbum where SchoolId=? order by Id desc";
		List<DeletedSubAlbum> subAlbums = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, schoolId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedSubAlbum subAlbum = new DeletedSubAlbum();
				subAlbum.setId(rs.getLong("Id"));
				subAlbum.setSenderId(rs.getLong("SenderId"));
				subAlbum.setAlbumId(rs.getLong("AlbumId"));
				subAlbum.setSubAlbumId(rs.getLong("SubAlbumId"));
				subAlbum.setDeletedAt(rs.getLong("DeletedAt"));
				subAlbums.add(subAlbum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subAlbums;
	}
	
	public List<DeletedSubAlbum> getDeletedSubAlbumsAboveId(long schoolId, long id) {
		String query = "select * from deleted_subalbum where SchoolId=? and Id>?";
		List<DeletedSubAlbum> subAlbums = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, schoolId);
			preparedStatement.setLong(2, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedSubAlbum subAlbum = new DeletedSubAlbum();
				subAlbum.setId(rs.getLong("Id"));
				subAlbum.setSenderId(rs.getLong("SenderId"));
				subAlbum.setAlbumId(rs.getLong("AlbumId"));
				subAlbum.setSubAlbumId(rs.getLong("SubAlbumId"));
				subAlbum.setDeletedAt(rs.getLong("DeletedAt"));
				subAlbums.add(subAlbum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subAlbums;
	}
	
	private void delete(long id) {
		String query = "delete from sub_album where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
