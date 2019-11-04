import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicationSchedule } from 'app/shared/model/medication-schedule.model';

@Component({
  selector: 'jhi-medication-schedule-detail',
  templateUrl: './medication-schedule-detail.component.html'
})
export class MedicationScheduleDetailComponent implements OnInit {
  medicationSchedule: IMedicationSchedule;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicationSchedule }) => {
      this.medicationSchedule = medicationSchedule;
    });
  }

  previousState() {
    window.history.back();
  }
}
