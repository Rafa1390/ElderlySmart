import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicalAppointment } from 'app/shared/model/medical-appointment.model';

@Component({
  selector: 'jhi-medical-appointment-detail',
  templateUrl: './medical-appointment-detail.component.html'
})
export class MedicalAppointmentDetailComponent implements OnInit {
  medicalAppointment: IMedicalAppointment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicalAppointment }) => {
      this.medicalAppointment = medicalAppointment;
    });
  }

  previousState() {
    window.history.back();
  }
}
