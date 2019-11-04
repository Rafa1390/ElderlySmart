import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaseFile } from 'app/shared/model/case-file.model';

@Component({
  selector: 'jhi-case-file-detail',
  templateUrl: './case-file-detail.component.html'
})
export class CaseFileDetailComponent implements OnInit {
  caseFile: ICaseFile;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ caseFile }) => {
      this.caseFile = caseFile;
    });
  }

  previousState() {
    window.history.back();
  }
}
