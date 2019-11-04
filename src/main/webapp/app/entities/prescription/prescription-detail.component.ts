import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrescription } from 'app/shared/model/prescription.model';

@Component({
  selector: 'jhi-prescription-detail',
  templateUrl: './prescription-detail.component.html'
})
export class PrescriptionDetailComponent implements OnInit {
  prescription: IPrescription;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prescription }) => {
      this.prescription = prescription;
    });
  }

  previousState() {
    window.history.back();
  }
}
