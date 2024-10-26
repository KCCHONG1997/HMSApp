package controller;

import model.HMSRecords;

public class RecordsController {
	public static boolean addRecord(HMSRecords record) {
		if (record == null || record.getRecordID() == null) {
			System.out.println("Error: Invalid record data.");
			return false;
		}
		return false;
	}
}
