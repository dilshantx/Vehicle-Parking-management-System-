package controller;

import model.ParkingModel;
import java.util.List;

public class ParkingController {
    private final ParkingModel parkingModel;

    public ParkingController() {
        parkingModel = new ParkingModel();
    }

    public boolean registerVehicle(String vehicleNumber, String vehicleType, int slotNumber, String entryTime) {
        return parkingModel.registerVehicle(vehicleNumber, vehicleType, slotNumber, entryTime);
    }

    public List<String[]> getCurrentParkingVehicles() {
        return parkingModel.getCurrentParkingVehicles();
    }

    public List<String[]> getParkingRecords() {
        return parkingModel.getParkingRecords();
    }

    public List<Integer> getOccupiedSlots() {
        return parkingModel.getOccupiedSlots();
    }

    public double calculateFee(int slotNumber) {
        return parkingModel.calculateFee(slotNumber);
    }

    public String[] getVehicleDetails(int slotNumber) {
        return parkingModel.getVehicleDetails(slotNumber);
    }

    public boolean releaseSlot(int slotNumber, double fee) {
        return parkingModel.releaseSlot(slotNumber, fee);
    }

    public List<Integer> getAvailableSlotsFill() {
        return parkingModel.getAvailableSlots();
    }

}
