import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrescriptionNotification } from 'app/shared/model/prescription-notification.model';

@Component({
  selector: 'jhi-prescription-notification-detail',
  templateUrl: './prescription-notification-detail.component.html'
})
export class PrescriptionNotificationDetailComponent implements OnInit {
  prescriptionNotification: IPrescriptionNotification;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prescriptionNotification }) => {
      this.prescriptionNotification = prescriptionNotification;
    });
  }

  previousState() {
    window.history.back();
  }
}
