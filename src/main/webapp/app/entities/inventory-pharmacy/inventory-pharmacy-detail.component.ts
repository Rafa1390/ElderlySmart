import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';

@Component({
  selector: 'jhi-inventory-pharmacy-detail',
  templateUrl: './inventory-pharmacy-detail.component.html'
})
export class InventoryPharmacyDetailComponent implements OnInit {
  inventoryPharmacy: IInventoryPharmacy;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventoryPharmacy }) => {
      this.inventoryPharmacy = inventoryPharmacy;
    });
  }

  previousState() {
    window.history.back();
  }
}
