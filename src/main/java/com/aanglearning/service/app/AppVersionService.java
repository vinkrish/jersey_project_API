package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.AppVersion;
import com.aanglearning.service.DatabaseUtil;

public class AppVersionService {
	Connection connection;

	public AppVersionService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<AppVersion> getAppVersions() {
		String query = "select * from app_version group by AppName, VersionId";
		List<AppVersion> appVersions = new ArrayList<>();
		try {
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			while (rs.next()){
				AppVersion appVersion = new AppVersion();
				appVersion.setId(rs.getInt("Id"));
				appVersion.setVersionId(rs.getInt("VersionId"));
				appVersion.setVersionName(rs.getString("VersionName"));
				appVersion.setAppName(rs.getString("AppName"));
				appVersion.setStatus(rs.getString("Status"));
				appVersions.add(appVersion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return appVersions;
	}
	
	public AppVersion getAppVersion(int versionId, String appName) {
		String query = "select * from app_version where VersionId = ? and AppName = ?";
		AppVersion appVersion = new AppVersion();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, versionId);
			preparedStatement.setString(2, appName);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				appVersion.setId(rs.getInt("Id"));
				appVersion.setVersionId(rs.getInt("VersionId"));
				appVersion.setVersionName(rs.getString("VersionName"));
				appVersion.setAppName(rs.getString("AppName"));
				appVersion.setStatus(rs.getString("Status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return appVersion;
	}
	
	public void add(AppVersion appVersion) {
		String query = "insert into app_version(Id, VersionId, VersionName, AppName, Status) values (?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, appVersion.getId());
	    	preparedStatement.setInt(2, appVersion.getVersionId());
	    	preparedStatement.setString(3, appVersion.getVersionName());
	    	preparedStatement.setString(4, appVersion.getAppName());
	    	preparedStatement.setString(5, appVersion.getStatus());
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void update(AppVersion appVersion) {
		String query = "update app_version set VersionName = ?, Status = ? where Id = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, appVersion.getVersionName());
	    	preparedStatement.setString(2, appVersion.getStatus());
	    	preparedStatement.setInt(3, appVersion.getId());
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String query = "delete from app_version where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setLong(1, id);
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
}
