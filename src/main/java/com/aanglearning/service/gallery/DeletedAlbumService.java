package com.aanglearning.service.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.gallery.DeletedAlbum;
import com.aanglearning.service.DatabaseUtil;

public class DeletedAlbumService {
	Connection connection;

	public DeletedAlbumService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DeletedAlbum add(DeletedAlbum deletedAlbum) {
		delete(deletedAlbum.getAlbumId());
		
		String query = "insert into deleted_album(SenderId, AlbumId, SchoolId, DeletedAt) "
				+ "values (?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, deletedAlbum.getSenderId());
	    	preparedStatement.setLong(2, deletedAlbum.getAlbumId());
	    	preparedStatement.setLong(3, deletedAlbum.getSchoolId());
	    	preparedStatement.setLong(4, deletedAlbum.getDeletedAt());
	    	preparedStatement.executeUpdate();
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			deletedAlbum.setId(pk);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return deletedAlbum;
	}
	
	public List<DeletedAlbum> getDeletedAlbums(long schoolId) {
		String query = "select * from deleted_album where SchoolId=? order by Id desc";
		List<DeletedAlbum> deletedAlbums = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, schoolId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedAlbum deletedAlbum = new DeletedAlbum();
				deletedAlbum.setId(rs.getLong("Id"));
				deletedAlbum.setSenderId(rs.getLong("SenderId"));
				deletedAlbum.setAlbumId(rs.getLong("AlbumId"));
				deletedAlbum.setSchoolId(rs.getLong("SchoolId"));
				deletedAlbum.setDeletedAt(rs.getLong("DeletedAt"));
				deletedAlbums.add(deletedAlbum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deletedAlbums;
	}
	
	public List<DeletedAlbum> getDeletedAlbumsAboveId(long schoolId, long id) {
		String query = "select * from deleted_album where SchoolId=? and Id>?";
		List<DeletedAlbum> deletedAlbums = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, schoolId);
			preparedStatement.setLong(2, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedAlbum deletedAlbum = new DeletedAlbum();
				deletedAlbum.setId(rs.getLong("Id"));
				deletedAlbum.setSenderId(rs.getLong("SenderId"));
				deletedAlbum.setAlbumId(rs.getLong("AlbumId"));
				deletedAlbum.setSchoolId(rs.getLong("SchoolId"));
				deletedAlbum.setDeletedAt(rs.getLong("DeletedAt"));
				deletedAlbums.add(deletedAlbum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deletedAlbums;
	}
	
	private void delete(long id) {
		String query = "delete from album where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
