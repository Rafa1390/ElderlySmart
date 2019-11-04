import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllergies } from 'app/shared/model/allergies.model';

@Component({
  selector: 'jhi-allergies-detail',
  templateUrl: './allergies-detail.component.html'
})
export class AllergiesDetailComponent implements OnInit {
  allergies: IAllergies;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allergies }) => {
      this.allergies = allergies;
    });
  }

  previousState() {
    window.history.back();
  }
}
