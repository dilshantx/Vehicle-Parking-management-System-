package model;

import database.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParkingModel {
    public boolean registerVehicle(String vehicleNumber, String vehicleType, int slotNumber, String entryTime) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String checkQuery = "SELECT is_available FROM Parking WHERE slot_number = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, slotNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getBoolean("is_available")) {
                // Update the slot with vehicle entry details
                String updateQuery = "UPDATE Parking SET vehicle_number = ?, vehicle_type = ?, entry_time = ?, is_available = false WHERE slot_number = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, vehicleNumber);
                updateStmt.setString(2, vehicleType);
                updateStmt.setString(3, entryTime);
                updateStmt.setInt(4, slotNumber);
                updateStmt.executeUpdate();

                return true; // Entry registered successfully
            } else {
                JOptionPane.showMessageDialog(null, "The selected slot is not available.");
                return false; // Slot not available
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] getVehicleDetails(int slotNumber) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT vehicle_number, vehicle_type, entry_time FROM Parking WHERE slot_number = ? AND is_available = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, slotNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new String[]{
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_type"),
                        rs.getString("entry_time")
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> getAvailableSlots() {
        List<Integer> availableSlots = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT slot_number FROM Parking WHERE is_available = true";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                availableSlots.add(rs.getInt("slot_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableSlots;
    }


    public List<Integer> getAvailableSlotsFill() {
        List<Integer> availableSlots = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT slot_number FROM Parking WHERE is_available = true";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                availableSlots.add(rs.getInt("slot_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableSlots;
    }



    public List<String[]> getCurrentParkingVehicles() {
        List<String[]> vehicles = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT vehicle_number, vehicle_type, slot_number, entry_time FROM Parking WHERE is_available = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String vehicleNumber = rs.getString("vehicle_number");
                String vehicleType = rs.getString("vehicle_type");
                int slotNumber = rs.getInt("slot_number");
                String entryTime = rs.getString("entry_time");
                vehicles.add(new String[]{vehicleNumber, vehicleType, String.valueOf(slotNumber), entryTime});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }
    public List<String[]> getParkingRecords() {
        List<String[]> records = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT vehicle_number, vehicle_type, slot_number, entry_time, exit_time, fee " +
                    "FROM ParkingHistory ORDER BY entry_time DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String vehicleNumber = rs.getString("vehicle_number");
                String vehicleType = rs.getString("vehicle_type");
                int slotNumber = rs.getInt("slot_number");
                String entryTime = rs.getString("entry_time");
                String exitTime = rs.getString("exit_time");
                double fee = rs.getDouble("fee");

                records.add(new String[]{vehicleNumber, vehicleType, String.valueOf(slotNumber), entryTime, exitTime, String.valueOf(fee)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }
    public List<Integer> getOccupiedSlots() {
        List<Integer> occupiedSlots = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT slot_number FROM Parking WHERE is_available = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                occupiedSlots.add(rs.getInt("slot_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return occupiedSlots;
    }
    public double calculateFee(int slotNumber) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Fetch entry time and vehicle type for the given slot
            String query = "SELECT entry_time, vehicle_type FROM Parking WHERE slot_number = ? AND is_available = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, slotNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String entryTimeStr = rs.getString("entry_time");
                String vehicleType = rs.getString("vehicle_type");

                // Handle fractional seconds in the entry_time string
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.S]");
                LocalDateTime entryTime;
                try {
                    entryTime = LocalDateTime.parse(entryTimeStr, formatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(null, "Invalid date format in the database.");
                    return -1;
                }

                // Calculate duration
                LocalDateTime currentTime = LocalDateTime.now();
                Duration duration = Duration.between(entryTime, currentTime);
                long hours = Math.max(duration.toHours(), 1); // Minimum 1 hour charge

                // Fetch rate for the vehicle type
                String rateQuery = "SELECT rate_per_hour FROM Rates WHERE vehicle_type = ?";
                PreparedStatement rateStmt = conn.prepareStatement(rateQuery);
                rateStmt.setString(1, vehicleType);
                ResultSet rateRs = rateStmt.executeQuery();

                if (rateRs.next()) {
                    double ratePerHour = rateRs.getDouble("rate_per_hour");
                    return hours * ratePerHour;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Indicates an error or invalid slot
    }

    public boolean releaseSlot(int slotNumber, double fee) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Fetch the vehicle number for the given slot
            String fetchQuery = "SELECT vehicle_number, vehicle_type, entry_time FROM Parking WHERE slot_number = ? AND is_available = false";
            PreparedStatement fetchStmt = conn.prepareStatement(fetchQuery);
            fetchStmt.setInt(1, slotNumber);
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                String vehicleNumber = rs.getString("vehicle_number");
                String vehicleType = rs.getString("vehicle_type");
                String entryTime = rs.getString("entry_time");

                // Update the parking record history
                String historyQuery = "INSERT INTO ParkingHistory (vehicle_number, vehicle_type, slot_number, entry_time, fee, exit_time) VALUES (?, ?, ?,?,?, NOW())";
                PreparedStatement historyStmt = conn.prepareStatement(historyQuery);
                historyStmt.setString(1, vehicleNumber);
                historyStmt.setString(2, vehicleType);
                historyStmt.setInt(3, slotNumber);
                historyStmt.setString(4, entryTime);
                historyStmt.setDouble(5, fee);


                // Mark the slot as available
                String updateQuery = "UPDATE Parking SET is_available = true, entry_time = NULL, vehicle_number = NULL, vehicle_type = NULL WHERE slot_number = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, slotNumber);

                // Execute both updates
                historyStmt.executeUpdate();
                updateStmt.executeUpdate();

                return true; // Operation successful
            } else {
                JOptionPane.showMessageDialog(null, "No vehicle found for the given slot number.");
                return false; // No vehicle found for the given slot
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
