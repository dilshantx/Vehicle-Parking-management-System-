package model;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class RateModel {
    public Map<String, Double> getRates() {
        Map<String, Double> rates = new HashMap<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT vehicle_type, rate_per_hour FROM Rates";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rates.put(rs.getString("vehicle_type"), rs.getDouble("rate_per_hour"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rates;
    }

    public boolean updateRate(String vehicleType, double newRate) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE Rates SET rate_per_hour = ? WHERE vehicle_type = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, newRate);
            stmt.setString(2, vehicleType);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
