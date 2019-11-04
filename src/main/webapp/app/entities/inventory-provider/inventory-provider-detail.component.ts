import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryProvider } from 'app/shared/model/inventory-provider.model';

@Component({
  selector: 'jhi-inventory-provider-detail',
  templateUrl: './inventory-provider-detail.component.html'
})
export class InventoryProviderDetailComponent implements OnInit {
  inventoryProvider: IInventoryProvider;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventoryProvider }) => {
      this.inventoryProvider = inventoryProvider;
    });
  }

  previousState() {
    window.history.back();
  }
}
