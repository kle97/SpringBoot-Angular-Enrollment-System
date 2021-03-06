export interface AdminSection {
	id: string,
	sectionNumber: number,
	meetingDays: string,
	meetingTimeStart: string,
	meetingTimeEnd: string,
	classCapacity: number,
	waitlistCapacity: number,
	enrolledNumber: number,
	waitingNumber: number,
	dateStart: string,
	dateEnd: string,
	sectionStatus: string,
	buildingNumber: number,
	roomNumber: number,
	instructorLastName: string,
	instructorFirstName: string,
	courseCode: string,
	courseName: string,
	termName: string,
	termId: string,
	courseId: string,
	roomId: string,
	instructorId: string,
}
