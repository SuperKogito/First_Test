package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.MessageType;

import java.util.Date;

/**
 * Message used to send geographical location information
 */
public class LocationMessage implements Message {
    private static final long serialVersionUID = 1L;

    public String request_id;
    public double latitude;
    public double longitude;
    public double horizontal_accuracy;
    public double altitude;
    public double vertical_accuracy;
    public double bearing;
    public double speed;
    public Date measured_on;
    public String description;

    public LocationMessage() {
    }

    @Override
    public MessageType getType() {
        return MessageType.LOCATION;
    }

    @Override
    public String asText() {
        return String.format("%f;%f", latitude, longitude);
    }

    @Override
    public String toString() {
        return "LocationMessage{" +
                "request_id='" + request_id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", horizontal_accuracy=" + horizontal_accuracy +
                ", altitude=" + altitude +
                ", vertical_accuracy=" + vertical_accuracy +
                ", bearing=" + bearing +
                ", speed=" + speed +
                ", measured_on=" + measured_on +
                ", description=" + description +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationMessage that = (LocationMessage) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.horizontal_accuracy, horizontal_accuracy) != 0) return false;
        if (Double.compare(that.altitude, altitude) != 0) return false;
        if (Double.compare(that.vertical_accuracy, vertical_accuracy) != 0) return false;
        if (Double.compare(that.bearing, bearing) != 0) return false;
        if (Double.compare(that.speed, speed) != 0) return false;
        if (request_id != null ? !request_id.equals(that.request_id) : that.request_id != null) return false;
        if (description!= null ? !description.equals(that.description) : that.description != null) return false;
        return !(measured_on != null ? !measured_on.equals(that.measured_on) : that.measured_on != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = request_id != null ? request_id.hashCode() : 0;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(horizontal_accuracy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(altitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vertical_accuracy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(bearing);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(speed);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (measured_on != null ? measured_on.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}