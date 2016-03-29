package com.ge.predix.solsvc.training.simulator.model;

public enum Locomotive {
	
	LOCOMOTIVE_1(40.712784, -74.005941, 4400),
	LOCOMOTIVE_2(37.774929, -122.419416, 4200);
	
	private final double latitude;
	private final double longitude;
	private final int horsePower;
	
	Locomotive(double latitude, double longitude, int horsePower) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.horsePower = horsePower;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public int getHorsePower() {
		return horsePower;
	}
	
//	LOCOMOTIVE_1 { //New York
//		@Override
//		public double getLatitude() {
//			return 40.712784;
//		}
//		
//		@Override
//		public double getLongitude() {
//			return -74.005941;
//		}
//	},
//	
//	LOCOMOTIVE_2 { //New York
//		@Override
//		public double getLatitude() {
//			return 37.774929;
//		}
//		
//		@Override
//		public double getLongitude() {
//			return -122.419416;
//		}
//	};
//
//	public abstract double getLatitude();
//	
//	public abstract double getLongitude();
}
