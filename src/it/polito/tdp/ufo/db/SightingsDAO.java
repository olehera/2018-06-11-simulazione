package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.Anno;
import it.polito.tdp.ufo.model.Adiacenza;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting WHERE country = 'us' " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	public List<Anno> elencoAnni() {
		
		String sql = "SELECT YEAR(DATETIME) AS anno, COUNT(*) AS avvistamenti FROM sighting " + 
				     "WHERE country = 'us' GROUP BY YEAR(DATETIME)";
		
		List<Anno> list = new ArrayList<Anno>();
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				list.add(new Anno(res.getInt("anno"), res.getInt("avvistamenti")));
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public List<String> elencoStati(int anno) {

		String sql = "SELECT DISTINCT state FROM sighting WHERE country = 'us' AND YEAR(DATETIME) = ?";
		
		List<String> list = new ArrayList<String>();
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				list.add(res.getString("state"));
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Adiacenza> ordineTemporale(int anno) {

		String sql = "SELECT s1.state AS primo, s2.state AS secondo " + 
				     "FROM sighting s1, sighting s2 " + 
				     "WHERE s1.country = 'us' AND YEAR(s1.datetime) = ? AND s2.datetime > s1.datetime " + 
				     "AND s1.country = s2.country AND YEAR(s1.datetime) = YEAR(s2.datetime) AND s1.state <> s2.state " + 
				     "GROUP BY s1.state, s2.state ";
		
		List<Adiacenza> list = new ArrayList<Adiacenza>();
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				list.add(new Adiacenza(res.getString("primo"), res.getString("secondo")));
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}