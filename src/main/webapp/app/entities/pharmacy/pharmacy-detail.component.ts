import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPharmacy } from 'app/shared/model/pharmacy.model';

@Component({
  selector: 'jhi-pharmacy-detail',
  templateUrl: './pharmacy-detail.component.html'
})
export class PharmacyDetailComponent implements OnInit {
  pharmacy: IPharmacy;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pharmacy }) => {
      this.pharmacy = pharmacy;
    });
  }

  previousState() {
    window.history.back();
  }
}
