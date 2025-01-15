package controller;

import model.RateModel;

import java.util.Map;

public class RateController {
    private final RateModel rateModel;

    public RateController() {
        rateModel = new RateModel();
    }

    public Map<String, Double> getRates() {
        return rateModel.getRates();
    }

    public boolean updateRate(String vehicleType, double newRate) {
        return rateModel.updateRate(vehicleType, newRate);
    }
}
