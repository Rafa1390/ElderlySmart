import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPathologies } from 'app/shared/model/pathologies.model';

@Component({
  selector: 'jhi-pathologies-detail',
  templateUrl: './pathologies-detail.component.html'
})
export class PathologiesDetailComponent implements OnInit {
  pathologies: IPathologies;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pathologies }) => {
      this.pathologies = pathologies;
    });
  }

  previousState() {
    window.history.back();
  }
}
