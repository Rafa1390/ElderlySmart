import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProvider } from 'app/shared/model/provider.model';

@Component({
  selector: 'jhi-provider-detail',
  templateUrl: './provider-detail.component.html'
})
export class ProviderDetailComponent implements OnInit {
  provider: IProvider;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ provider }) => {
      this.provider = provider;
    });
  }

  previousState() {
    window.history.back();
  }
}
