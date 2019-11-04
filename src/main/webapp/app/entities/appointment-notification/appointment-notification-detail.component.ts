import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppointmentNotification } from 'app/shared/model/appointment-notification.model';

@Component({
  selector: 'jhi-appointment-notification-detail',
  templateUrl: './appointment-notification-detail.component.html'
})
export class AppointmentNotificationDetailComponent implements OnInit {
  appointmentNotification: IAppointmentNotification;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appointmentNotification }) => {
      this.appointmentNotification = appointmentNotification;
    });
  }

  previousState() {
    window.history.back();
  }
}
