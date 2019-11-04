import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuneralPackages } from 'app/shared/model/funeral-packages.model';

@Component({
  selector: 'jhi-funeral-packages-detail',
  templateUrl: './funeral-packages-detail.component.html'
})
export class FuneralPackagesDetailComponent implements OnInit {
  funeralPackages: IFuneralPackages;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ funeralPackages }) => {
      this.funeralPackages = funeralPackages;
    });
  }

  previousState() {
    window.history.back();
  }
}
