import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMortuary } from 'app/shared/model/mortuary.model';

@Component({
  selector: 'jhi-mortuary-detail',
  templateUrl: './mortuary-detail.component.html'
})
export class MortuaryDetailComponent implements OnInit {
  mortuary: IMortuary;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mortuary }) => {
      this.mortuary = mortuary;
    });
  }

  previousState() {
    window.history.back();
  }
}
