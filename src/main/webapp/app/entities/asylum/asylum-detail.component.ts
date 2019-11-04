import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAsylum } from 'app/shared/model/asylum.model';

@Component({
  selector: 'jhi-asylum-detail',
  templateUrl: './asylum-detail.component.html'
})
export class AsylumDetailComponent implements OnInit {
  asylum: IAsylum;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ asylum }) => {
      this.asylum = asylum;
    });
  }

  previousState() {
    window.history.back();
  }
}
