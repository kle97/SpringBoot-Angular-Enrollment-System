import { Component, OnInit } from "@angular/core";
import { EnrollmentStatus } from "../../../../core/constants/enrollment-status";
import { Enrollment } from "../state/enrollment/enrollment.model";

@Component({
	selector: "app-shopping-cart",
	templateUrl: "./shopping-cart.component.html",
	styleUrls: ["./shopping-cart.component.scss"],
})
export class ShoppingCartComponent implements OnInit {

	title = "Shopping Cart";
	subtitle = "Cart for Enrollment";
	enrollmentStatuses = [EnrollmentStatus.IN_CART];

	constructor() {
	}

	ngOnInit(): void {
	}

	asEnrollment(value: any): Enrollment {
		return value as Enrollment;
	}

}
