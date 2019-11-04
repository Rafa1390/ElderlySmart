import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPharmacyHistory } from 'app/shared/model/pharmacy-history.model';

@Component({
  selector: 'jhi-pharmacy-history-detail',
  templateUrl: './pharmacy-history-detail.component.html'
})
export class PharmacyHistoryDetailComponent implements OnInit {
  pharmacyHistory: IPharmacyHistory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pharmacyHistory }) => {
      this.pharmacyHistory = pharmacyHistory;
    });
  }

  previousState() {
    window.history.back();
  }
}
