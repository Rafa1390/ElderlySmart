import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuneralHistory } from 'app/shared/model/funeral-history.model';

@Component({
  selector: 'jhi-funeral-history-detail',
  templateUrl: './funeral-history-detail.component.html'
})
export class FuneralHistoryDetailComponent implements OnInit {
  funeralHistory: IFuneralHistory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ funeralHistory }) => {
      this.funeralHistory = funeralHistory;
    });
  }

  previousState() {
    window.history.back();
  }
}
