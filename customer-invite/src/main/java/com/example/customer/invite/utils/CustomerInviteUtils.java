package com.example.customer.invite.utils;

public class CustomerInviteUtils {

	/**
	 * Calculate distance between two GPS coordinates
	 * 
	 * @param latitude1
	 * @param longitude1
	 * @param latitude2
	 * @param longitude2
	 * @param unit
	 * @return The distance between two GPS coordinates
	 */
	public static Double getDistance(double latitude1, double longitude1, double latitude2, double longitude2, String unit) {
		if ((latitude1 == latitude2) && (longitude1 == longitude2)) {
			return 0D;
		} else {
			double theta = longitude1 - longitude2;
			double dist = Math.sin(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2))
					+ Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
							* Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}
}
