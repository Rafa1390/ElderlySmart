import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProviderHistory } from 'app/shared/model/provider-history.model';

@Component({
  selector: 'jhi-provider-history-detail',
  templateUrl: './provider-history-detail.component.html'
})
export class ProviderHistoryDetailComponent implements OnInit {
  providerHistory: IProviderHistory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ providerHistory }) => {
      this.providerHistory = providerHistory;
    });
  }

  previousState() {
    window.history.back();
  }
}
